package cn.edu.sdua.toysrent.aspect;

import cn.edu.sdua.toysrent.utils.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect()
@Component
public class LoggingAspect {

    @Pointcut("execution(* cn.edu.sdua.toysrent.service.*.*(..))")
    public void serviceMethods() {}
    @Pointcut("execution(* cn.edu.sdua.toysrent.repository.*.*(..))")
    public void repositoryMethods() {}

    @Before("serviceMethods()")
    public void logStart(JoinPoint joinPoint) {
        LogUtils.logStart(joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        LogUtils.returnValue(joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "e")
    public void logException(JoinPoint joinPoint, Throwable e) {
        LogUtils.logException(joinPoint.getSignature().getName(), e);
    }

    @After("serviceMethods()")
    public void logEnd(JoinPoint joinPoint) {
        LogUtils.logEnd(joinPoint.getSignature().getName());
    }
}
