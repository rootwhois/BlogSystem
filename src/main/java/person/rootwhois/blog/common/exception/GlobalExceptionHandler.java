package person.rootwhois.blog.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import person.rootwhois.blog.common.result.ApiErrorCode;
import person.rootwhois.blog.common.result.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 陈广生
 * @Date: 2022/01/11/12:04 AM
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕捉shiro的授权异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result<Object> handler(HttpServletRequest req, Exception e) {
        log.error("权限不足:-------------->{}", e.getMessage());
        return Result.failed("权限不足", ApiErrorCode.Unauthorized.getCode(),e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result<Object> handler(ShiroException e) {
        log.error("Shiro异常：----------------{}", e.getMessage());
        return Result.failed(null, ApiErrorCode.Unauthorized.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> handler(MethodArgumentNotValidException e) {
        log.error("实体校验异常：----------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.failed(null, ApiErrorCode.SERVICE.getCode(), objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<Object> handler(IllegalArgumentException e) {
        log.error("Assert异常：----------------{}", e.getMessage());
        return Result.failed(null, ApiErrorCode.SERVICE.getCode(), e.getMessage());
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = RuntimeException.class)
//    public Result<Object> handler(RuntimeException e) {
//        log.error("运行时异常：----------------{}", e.getMessage());
//        return Result.failed(null, ApiErrorCode.SERVICE.getCode(), e.getMessage());
//    }

}
