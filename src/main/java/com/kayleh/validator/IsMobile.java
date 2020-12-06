package com.kayleh.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: Kayleh
 * @Date: 2020/12/5 19:34
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})//表示的是能够标注的范围
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})//这个注解帮助我们处理逻辑，其中有IsMobileValidator.class是真正处理逻辑的类，我们看看它的代码
public @interface IsMobile
{
    boolean required() default true;

    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
