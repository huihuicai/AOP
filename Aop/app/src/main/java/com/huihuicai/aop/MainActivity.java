
package com.huihuicai.aop;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.huihuicai.aspectj.annotation.LogAnnotation;
import com.huihuicai.aspectj.annotation.PermissionAnnotation;
import com.huihuicai.aspectj.log.LogUtil;

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
    public void testPermission() {
        LogUtil.print("mainActivity", "testPermission");
    }

    public void testPermission(View view) {
        testPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("mainActivity", "onRequestPermissionsResult======");
        }
    }
}
