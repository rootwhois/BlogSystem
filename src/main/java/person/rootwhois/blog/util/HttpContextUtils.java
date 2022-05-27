package person.rootwhois.blog.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: 陈广生
 * @Date: 2022/03/06/11:14 AM
 * @Description:
 */
public class HttpContextUtils {
    public static HttpServletRequest getRequest() {
        return  ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
    }

    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getCityInfo(String ip) {
        String url = "http://ip-api.com/json/" + ip + "?lang=zh-CN";
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(url));
        if ("success".equals(jsonObject.get("status"))) {
            return jsonObject.getString("country")
                    + " | "
                    + jsonObject.getString("regionName")
                    + " | "
                    + jsonObject.getString("city");
        } else {
            return jsonObject.getString("message");
        }
    }
}
