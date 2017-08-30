package com.huihuicai.aspectj.log;

import com.huihuicai.aspectj.annotation.LogAnnotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by ybm on 2017/8/30.
 */
@Aspect
public class LogAspectj {
    /**
     * 1.连接点是什么？2.怎么确定切入点？3.advice确认处理
     */
    private final String EXECUTE_METHOD =
            "execution(@com.huihuicai.aspectj.annotation.LogAnnotation * *(..))";
    private final String EXECUTE_STRUCTURE =
            "execution(@com.huihuicai.aspectj.annotation.LogAnnotation *.new(..))";

    @Pointcut(EXECUTE_METHOD)
    public void executeMethod() {
    }

    @Pointcut(EXECUTE_STRUCTURE)
    public void executeStructure() {
    }

    @Around("executeMethod()||executeStructure()")
    public Object weaveJoinPoint(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        LogAnnotation ann = signature.getMethod().getAnnotation(LogAnnotation.class);
        if (ann != null) {
            String value = ann.value();
            LogUtil.print(className, value);
        }
        return point.proceed();
    }
}
