package person.rootwhois.blog.common.result;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/**
 * @Author: 陈广生
 * @Date: 2022/01/06/10:35 AM
 * @Description:
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 业务错误码
     */
    private long code;

    /**
     * 描述
     */
    private String msg;


    /**
     * 结果集
     */
    private T data;

    /**
     * 响应时间
     */
    private String responseTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public Result() {
    }

    public Result(ApiErrorCode errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ApiErrorCode.FAILED);
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public static <T> Result<T> ok(T data) {
        ApiErrorCode aec = ApiErrorCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCode.FAILED;
        }
        return restResult(data, aec);
    }

    public static <T> Result<T> save(T data) {
        ApiErrorCode aec = ApiErrorCode.SAVE_SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCode.SAVE_FAILED;
        }
        return restResult(data, aec);
    }

    public static <T> Result<T> update(T data) {
        ApiErrorCode aec = ApiErrorCode.UPDATE_SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCode.UPDATE_FAILED;
        }
        return restResult(data, aec);
    }

    public static <T> Result<T> delete(T data) {
        ApiErrorCode aec = ApiErrorCode.DELETE_SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCode.DELETE_FAILED;
        }
        return restResult(data, aec);
    }
    public static <T> Result<T> failed(String msg) {
        return restResult(null, ApiErrorCode.FAILED.getCode(), msg);
    }

    public static <T> Result<T> failed(ApiErrorCode errorCode, String msg) {
        return restResult(null, errorCode.getCode(), msg);
    }

    public static <T> Result<T> failed(ApiErrorCode errorCode) {
        return restResult(null, errorCode);
    }

    public static <T> Result<T> restResult(T data, ApiErrorCode errorCode) {
        return restResult(data, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> restResult(T data, long code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static <T> Result<T> failed(T data, long code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean ok() {
        return ApiErrorCode.SUCCESS.getCode() == code;
    }

    /**
     * 服务间调用非业务正常，异常直接释放
     */
    public T serviceData() throws Exception {
        if (!ok()) {
            throw new Exception(this.msg);
        }
        return data;
    }

}

