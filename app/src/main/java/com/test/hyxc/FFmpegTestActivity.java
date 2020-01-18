package com.test.hyxc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.test.hyxc.JniNative.FFmpegNative;

import java.io.File;

public class FFmpegTestActivity extends AppCompatActivity {
  public TextView tv1;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.setContentView(R.layout.imap_ffmpegtest_activity);
        tv1=(TextView)findViewById(R.id.tv_1);

    }


    @Override
    protected  void onResume() {

        super.onResume();
        int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(FFmpegTestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    FFmpegTestActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        String input = new File(Environment.getExternalStorageDirectory(),"/DCIM/Camera/1af3d362209f10eb87025026823f2286.mp4").getAbsolutePath();
        int ret = FFmpegNative.FFmpegTest(input,"");
        tv1.setText("视频长度"+ret);
        System.out.println(ret);
        int s=FFmpegNative.JniCppAdd(1,2);
    }
}
