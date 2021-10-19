package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lody.virtual.client.core.InstallStrategy;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;

/**
 * 良性应用特点就是任何操作都需要用户点击同意
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 安装应用
     * @param view
     */
    public void onClick0(View view) {
        installPackage();
    }

    private void installPackage() {
        // int COMPARE_VERSION = 0X01 << 3;
        // int SKIP_DEX_OPT = 0x01 << 6;
        // 或运算结果 72
        int flags = InstallStrategy.COMPARE_VERSION | InstallStrategy.SKIP_DEX_OPT;

        // 安装 SD 卡根目录中的 app.apk 文件
        // /storage/emulated/0/app.apk
        VirtualCore.get().installPackage(getFilesDir() + "/app.apk", flags);
    }

    /**
     * 启动应用
     * @param view
     */
    public void onClick(View view) {
        startApp();
    }

    private void startApp() {
        // 打开应用
        Intent intent =  VirtualCore.get().getLaunchIntent("kim.hsl.svg", 0);
        /*VirtualCore.get().setUiCallback(intent, null);
        try {
            VirtualCore.get().preOpt("kim.hsl.svg");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        VActivityManager.get().startActivity(intent, 0);

        finish();
    }
}
