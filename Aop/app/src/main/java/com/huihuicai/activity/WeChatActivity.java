package com.huihuicai.activity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huihuicai.util.DensityUtil;
import com.huihuicai.util.R;

public class WeChatActivity extends AppCompatActivity {

    private ListView lvWeChat;
    private WeChatDialog weChatDialog;
    private int mStatusBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        lvWeChat = (ListView) findViewById(R.id.lv_wechart);
        lvWeChat.setAdapter(new WeChatAdapter(this));
        weChatDialog = new WeChatDialog(this, R.style.dialog);
        lvWeChat.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                lvWeChat.getWindowVisibleDisplayFrame(rect);
                mStatusBarHeight = rect.top;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    lvWeChat.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public void showDialog(final int x, final int y) {
        if (weChatDialog == null) {
            weChatDialog = new WeChatDialog(this, R.style.dialog);
        }
        show(x, y);
    }

    private void show(int x, int y) {
        if (weChatDialog == null) {
            return;
        }
        Window win = weChatDialog.getWindow();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = DensityUtil.dip2px(this, 120);
        lp.height = DensityUtil.dip2px(this, 40);
        lp.x = x - lp.width;
        lp.y = (int) (y - lp.height * 0.5f);
        win.setAttributes(lp);
        win.setWindowAnimations(R.style.dialogWindowAnim);
        weChatDialog.setCanceledOnTouchOutside(true);
        weChatDialog.show();
    }

    private void showPop(int x, int y) {
        int width = DensityUtil.dip2px(this, 120);
        int height = DensityUtil.dip2px(this, 40);
        PopupWindow pop = new PopupWindow(width, height);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null);
        pop.setContentView(view);
        pop.setBackgroundDrawable(new ColorDrawable(0x88222222));
        pop.setOutsideTouchable(true);
        pop.showAtLocation(lvWeChat, Gravity.NO_GRAVITY, x - width, y);
    }

    private class WeChatAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public WeChatAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                view = mInflater.inflate(R.layout.item_wechat, parent, false);
                holder = new ViewHolder();
                holder.tvContent = (TextView) view.findViewById(R.id.tv_content);
                holder.btnDialog = (Button) view.findViewById(R.id.btn_dialog);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.btnDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int height = v.getMeasuredHeight();
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    int y = (int) (location[1] - mStatusBarHeight + height * 0.5f);
//                    showDialog(location[0], y);
                    showPop(location[0], y);
                }
            });

            return view;
        }

        class ViewHolder {
            TextView tvContent;
            Button btnDialog;
        }
    }

}
