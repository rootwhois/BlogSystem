package person.rootwhois.blog.common.result;


/**
 * @Author: 陈广生
 * @Date: 2022/01/06/10:25 AM
 * @Description:
 */
public enum ApiErrorCode {

    /**
     * 失败
     */
    FAILED(400, "数据获取失败"),

    /**
     * 成功
     */
    SAVE_SUCCESS(200, "保存成功"),

    /**
     * 失败
     */
    SAVE_FAILED(400, "数据已存在，保存失败"),


    /**
     * 成功
     */
    UPDATE_SUCCESS(200, "修改成功"),

    /**
     * 失败
     */
    UPDATE_FAILED(400, "数据存在重复，修改失败"),

    /**
     * 成功
     */
    DELETE_SUCCESS(200, "删除成功"),

    /**
     * 失败
     */
    DELETE_FAILED(400, "数据不存在或者正在被使用，删除失败"),

    /**
     * 服务异常
     */
    SERVICE(500, "服务器异常"),

    /**
     * 成功
     */
    SUCCESS(200, "数据获取成功"),

    /**
     * 权限不足
     */
    Unauthorized(403, "权限不足，禁止访问"),

    /**
     * 未满足前提条件
     */
    PRECONDITION_FAILED(412, "未满足前提条件")
    ;

    private final long code;
    private final String msg;

    ApiErrorCode(final long code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiErrorCode fromCode(long code) {
        ApiErrorCode[] ecs = ApiErrorCode.values();
        for (ApiErrorCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format(" ErrorCode:{code=%s, msg=%s} ", code, msg);
    }
}

