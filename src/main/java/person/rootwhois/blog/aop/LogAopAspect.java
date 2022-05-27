package person.rootwhois.blog.aop;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import person.rootwhois.blog.annotation.LogAnno;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.dao.AdminDao;
import person.rootwhois.blog.entity.History;
import person.rootwhois.blog.service.HistoryService;
import person.rootwhois.blog.service.MailService;
import person.rootwhois.blog.util.HttpContextUtils;
import person.rootwhois.blog.util.JwtUtils;
import person.rootwhois.blog.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: AOP实现日志
 * @Author: 陈广生
 * @Date: 2022/03/06/11:04 AM
 * @Description:
 */
@Order(3)  // 标记支持AspectJ的切面排序
@Component
@Aspect
public class LogAopAspect {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    HistoryService historyService;

    @Autowired
    MailService mailService;

    @Autowired
    AdminDao adminDao;

    @Pointcut("@annotation(person.rootwhois.blog.annotation.LogAnno)")
    public void operLogPoinCut() {

    }

    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public Object aroundAdvice(JoinPoint joinPoint, Object keys) {
        // 1.方法执行前的处理，相当于前置通知
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        LogAnno logAnno = method.getAnnotation(LogAnno.class);
        // 获取操作描述的属性值
        String operateType = logAnno.operateType();
        History history = new History();
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer localVariableTable = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = localVariableTable.getParameterNames(method);
        Object[] args = joinPoint.getArgs();
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for(int i = 0; i< Objects.requireNonNull(paraNameArr).length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        String operator = null;


        String operatorSpel = logAnno.operator();
        // 使用变量方式传入业务动态数据
        if(operatorSpel.matches("^#.*.$")) {
            operator = parser.parseExpression(operatorSpel).getValue(context, String.class);
        }

        if (!logAnno.isToken()) {
            history.setOperator(operator);
        } else {
            String userId = jwtUtils.getClaimByToken(operator).getSubject();
            operator = adminDao.selectById(userId).getUserName();
            history.setOperator(operator);
        }

        String className = joinPoint.getTarget().getClass().getName();
        String signatureName = methodSignature.getName();
        history.setOperateMethod(className + "." + signatureName + "()");
        String requestMethod = ServletUtils.getRequest().getMethod();
        String params = "";
        if ("PUT".equals(requestMethod) || "POST".equals(requestMethod)) {
            params = argsArrayToString(joinPoint.getArgs());
            history.setParams(StringUtils.substring(params, 0, 2000));
        } else {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            assert requestAttributes != null;
            HttpServletRequest request = (HttpServletRequest) requestAttributes
                    .resolveReference(RequestAttributes.REFERENCE_REQUEST);
            // 请求的参数
            assert request != null;
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            history.setParams(StringUtils.substring(JSON.toJSONString(rtnMap), 0, 2000));
        }
        // 接口返回值参数
        Result result = (Result) keys;
        history.setOperateResultStatus(result.getCode() == 200 ? "成功" : "失败");
        String operateResult = JSON.toJSONString(keys);
        history.setOperateResult(operateResult);
        history.setOperateType(operateType);
        String ip = HttpContextUtils.getIpAddress();
        history.setOperateIp(ip);
        historyService.save(history);


        // 对管理员发送邮件
        if (result.getCode() == 200 && logAnno.sendEmail()) {
            mailService.sendSystemNotifyMail(ip, operator, operateType, params,operateResult);
        }
        return result;
    }

    /**
     * 转换request 请求参数
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!isFilterObject(o)) {
                    Object jsonObj = JSON.toJSON(o);
                    if (jsonObj != null) {
                        params.append(jsonObj).append(" ");
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}

