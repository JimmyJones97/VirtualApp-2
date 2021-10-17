package com.example.myapp;

import android.content.Intent;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lody.virtual.client.core.InstallStrategy;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.remote.InstallResult;
import com.lody.virtual.remote.InstalledAppInfo;

public class MainActivity extends AppCompatActivity {

    private static final String PKG_NAME_ARGUMENT = "MODEL_ARGUMENT";
    private static final String KEY_INTENT = "KEY_INTENT";
    private static final String KEY_USER = "KEY_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // int COMPARE_VERSION = 0X01 << 3;
        // int SKIP_DEX_OPT = 0x01 << 6;
        int flags = InstallStrategy.COMPARE_VERSION | InstallStrategy.SKIP_DEX_OPT;

        // 安装 SD 卡根目录中的 app.apk 文件
        // /storage/emulated/0/./app.apk
        VirtualCore.get().installPackage(/*Environment.getExternalStorageDirectory() + "/app.apk"*/ "/storage/emulated/0/./app.apk", 72);
    }

    public void onClick(View view) {
        // 打开应用
        Intent intent =  VirtualCore.get().getLaunchIntent("kim.hsl.svg", 0);
        VirtualCore.get().setUiCallback(intent, null);
        try {
            VirtualCore.get().preOpt("kim.hsl.svg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        VActivityManager.get().startActivity(intent, 0);

        finish();
    }
}
