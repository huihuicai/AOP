package com.huihuicai.finder;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.huihuicai.finder.provider.ActivityProvider;
import com.huihuicai.finder.provider.Provider;
import com.huihuicai.finder.provider.ViewProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ybm on 2017/9/7.
 */

//主要是对外提供接口使用
public class ViewFinder {

    private static ActivityProvider mActivityProvider = new ActivityProvider();
    private static ViewProvider mViewProvider = new ViewProvider();
    private static Map<String, Finder> mFinderMap = new HashMap<>();

    public static void inject(Object host) {
        if (host == null) {
            throw new IllegalArgumentException("The argument can't be null");
        }
        if (host instanceof Activity) {
            inject(host, host, mActivityProvider);
        } else if (host instanceof View) {
            inject(host, host, mViewProvider);
        }
    }

    private static void inject(Object host, Object source, Provider provider) {
        String className = host.getClass().getName();
        Finder finder = mFinderMap.get(className);
        if (finder == null) {
            try {
                Class<?> clazz = Class.forName(className + "$$Finder");
                finder = (Finder) clazz.newInstance();
                mFinderMap.put(className, finder);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (finder != null) {
            finder.finder(host, source, provider);
        }
    }
}
