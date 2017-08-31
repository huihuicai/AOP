
package com.huihuicai.aop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huihuicai.aspectj.annotation.LogAnnotation;
import com.huihuicai.aspectj.annotation.PermissionAnnotation;
import com.huihuicai.aspectj.annotation.SingleClick;

public class MainActivity extends AppCompatActivity {

    @LogAnnotation(value = "onCreate")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text();
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
}
