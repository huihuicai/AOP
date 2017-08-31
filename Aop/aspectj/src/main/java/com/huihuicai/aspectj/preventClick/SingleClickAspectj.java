package com.huihuicai.aspectj.preventClick;

import android.view.View;

import com.huihuicai.aspectj.R;
import com.huihuicai.aspectj.annotation.SingleClick;
import com.huihuicai.aspectj.log.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by ybm on 2017/8/31.
 */

@Aspect
public class SingleClickAspectj {

    private final String EXECUTE_METHOD =
            "execution(@com.huihuicai.aspectj.annotation.SingleClick * *(..))";

    @Pointcut(EXECUTE_METHOD)
    public void executeSingleClick() {
    }

    @Around("executeSingleClick()")
    public void DoubleClick(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SingleClick ann = signature.getMethod().getAnnotation(SingleClick.class);
        int second = ann.millisecond();
        Object[] args = joinPoint.getArgs();
        View view = null;
        if (args != null && args[0] instanceof View) {
            view = (View) args[0];
        }
        if (view != null) {
            long curTime = System.currentTimeMillis();
            Object tag = view.getTag(R.integer.id_time);
            view.setTag(R.integer.id_time, curTime);
            if (tag == null) {
                joinPoint.proceed();
            } else {
                long time = (long) tag;
                if (curTime - time > second) {
                    joinPoint.proceed();
                }
            }
        } else {
            joinPoint.proceed();
        }
    }
}
