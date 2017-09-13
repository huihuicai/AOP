package com.huihuicai.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.huihuicai.util.R;

/**
 * Created by ybm on 2017/9/11.
 */

public class WeChatDialog extends AlertDialog {


    protected WeChatDialog(Context context) {
        super(context);
    }

    protected WeChatDialog(Context context, int style) {
        super(context, style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);
    }
}
