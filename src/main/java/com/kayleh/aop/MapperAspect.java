package com.kayleh.aop;

import groovy.util.logging.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class MapperAspect {

    private static final Log LOG = LogFactory.getLog(LogAspect.class);

    @AfterReturning("execution(* com.kayleh.dao.*Dao.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        LOG.info("Completed: " + joinPoint);
    }


    /**
     * 监控cn.xbmchina.mybatissqltime.mapper..*Mapper包及其子包的所有public方法
     */
    @Pointcut("execution(* com.kayleh.dao.*Dao.*(..))")
    private void pointCutMethod() {
    }

    /**
     * 声明环绕通知
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.nanoTime();
        Object obj = pjp.proceed();
        long end = System.nanoTime();

//        LOG.info("调用Mapper方法：{}，参数：{}，执行耗时：{}纳秒，耗时：{}毫秒",
//                pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()),
//                (end - begin), (end - begin) / 1000000);
        return obj;
    }
}
