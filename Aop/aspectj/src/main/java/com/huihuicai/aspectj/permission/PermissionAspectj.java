package com.huihuicai.aspectj.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.huihuicai.aspectj.annotation.PermissionAnnotation;
import com.huihuicai.aspectj.log.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * Created by ybm on 2017/8/30.
 */
@Aspect
public class PermissionAspectj {
    private final String PERMISSION_METHOD =
            "execution(@com.huihuicai.aspectj.annotation.PermissionAnnotation * *(..))";

    @Pointcut(PERMISSION_METHOD)
    public void executePermission() {
    }

    @Around("executePermission()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PermissionAnnotation ann = signature.getMethod().getAnnotation(PermissionAnnotation.class);
        String permission = "";
        Activity context = null;
        Object o = joinPoint.getThis();
        if (o instanceof Activity) {
            context = (Activity) o;
        } else if (o instanceof Fragment) {
            context = ((Fragment) o).getActivity();
        } else if (o instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) o).getActivity();
        }
        if (ann != null) {
            permission = ann.permission();
        }
        if (context != null && !TextUtils.isEmpty(permission)) {
            if (ContextCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                LogUtil.print("permission", "权限还没申请呢");
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    new AlertDialog.Builder(context)
                            .setTitle("权限管理")
                            .setMessage("缺少程序所需要的权限，应用无法使用，请前往“权限管理”页面打开相应权限")
                            .setPositiveButton("好的", null)
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                } else {
                    ActivityCompat.requestPermissions(context, new String[]{permission}, 0);
                }
            }
        }
        return joinPoint.proceed();
    }
}
