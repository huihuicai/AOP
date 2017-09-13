package com.huihuicai.finder.provider;

import android.content.Context;
import android.view.View;

/**
 * Created by ybm on 2017/9/7.
 */
//主要是得到一个上下文和当前的view
public interface Provider {

    Context getContext(Object source);

    View findView(Object source, int id);

}
