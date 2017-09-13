package com.huihuicai.finder.provider;

import android.content.Context;
import android.view.View;

/**
 * Created by ybm on 2017/9/7.
 */

public class ViewProvider implements Provider {

    @Override
    public Context getContext(Object source) {
        return ((View) source).getContext();
    }

    @Override
    public View findView(Object source, int id) {
        return ((View) source).findViewById(id);
    }
}
