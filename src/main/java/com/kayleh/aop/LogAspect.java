package com.kayleh.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect
{
    private static final Log LOG = LogFactory.getLog(LogAspect.class);

    /**
     * 定义一个切入点.
     * 解释下：
     * <p>
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 定义在web包或者子包
     * ~ 第三个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(* com.kayleh.Service..*.*(..))")
    public void logPointcut()
    {
    }

    @org.aspectj.lang.annotation.Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable
    {
        //       LOG.debug("logPointcut " + joinPoint + "\t");
        long start = System.currentTimeMillis();
        try
        {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            LOG.error("+++++around " + joinPoint + "\tUse time : " + (end - start) + "ms!");
            return result;
        } catch (Throwable e)
        {
            long end = System.currentTimeMillis();
            LOG.error("+++++around " + joinPoint + "\tUse time : " + (end - start) + "ms with exception :" + e.getMessage());
            throw e;
        }
    }
}