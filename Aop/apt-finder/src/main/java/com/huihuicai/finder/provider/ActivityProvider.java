package com.huihuicai.finder.provider;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by ybm on 2017/9/7.
 */

public class ActivityProvider implements Provider {
    @Override
    public Context getContext(Object source) {
        return (Activity) source;
    }

    @Override
    public View findView(Object source, int id) {
        return ((Activity) source).findViewById(id);
    }
}
