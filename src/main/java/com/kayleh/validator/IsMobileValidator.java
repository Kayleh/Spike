package com.kayleh.validator;

import com.kayleh.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 有两个泛型，第一个是自定义的注解类，第二个是要验证的参数类型，另外实现该接口的逻辑类，被spring管理成bean，可以在需要的地方进行装配
 *
 * @Author: Kayleh
 * @Date: 2020/12/5 19:45
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String>
{

    private boolean required = false;

    /**
     * 初始化方法，它调用的是我们自定义注解中写的required()方法，默认需要有值
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(IsMobile constraintAnnotation)
    {
        required = constraintAnnotation.required();
    }

    /**
     * 对逻辑进行验证，true验证通过，false验证失败
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        if (required)
        {
            //在必须有值的情况下
            return ValidatorUtil.isMobile(value);
        } else
        {
            //在不要求有值的情况下
            if (StringUtils.isEmpty(value))
            {
                //空值是允许的
                return true;
            } else
            {
                //有值就给它判断判断
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
