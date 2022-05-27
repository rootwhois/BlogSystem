package person.rootwhois.blog.annotation;


import java.lang.annotation.*;

/**
 * @Description: 日志注解
 * @Author: 陈广生
 * @Date: 2022/03/06/11:02 AM
 * @Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnno {
    // 记录日志的操作类型
    String operateType() default "";

    // 是否是token
    boolean isToken() default false;

    // 记录操作人
    String operator() default "";

    // 电子邮件通知
    boolean sendEmail() default false;

    // 电邮地址
    String emailAddress() default "";

}