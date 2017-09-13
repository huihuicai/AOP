
package com.huihuicai.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huihuicai.util.R;
import com.huihuicai.aspectj.annotation.LogAnnotation;
import com.huihuicai.aspectj.annotation.PermissionAnnotation;
import com.huihuicai.aspectj.annotation.SingleClick;
import com.huihuicai.finder.ViewFinder;
import com.wynne.annotation.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_apt)
    Button btnApt;
    @BindView(R.id.btn_chat)
    Button btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewFinder.inject(this);
        text();
        aptTest();
    }

    @LogAnnotation(value = "text")
    public void text() {

    }

    @PermissionAnnotation(permission = Manifest.permission.CAMERA)
    public void testPermission(View view) {
        Toast.makeText(this, "检查权限", Toast.LENGTH_SHORT).show();
    }

    @SingleClick(millisecond = 1500)
    public void preventClick(View view) {
        Toast.makeText(this, "点我了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("mainActivity", "onRequestPermissionsResult======");
        }
    }

    private void aptTest() {
        if (btnApt != null) {
            Toast.makeText(this, "我日日，apt成功了", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "我靠，apt竟然没成了", Toast.LENGTH_SHORT).show();
        }

        btnApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "APT自动生成了我这个button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //类似微信的dialog
    public void wechat(View view) {
        startActivity(new Intent(this, WeChatActivity.class));
    }
}
