package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapp.hotfix.FixDexUtils;
import com.example.myapp.hotfix.HotFixTest;
import com.lody.virtual.client.core.InstallStrategy;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 是否自动安装并启动插件应用
        boolean isAuto = true;
        if (isAuto) {
            new Thread() {
                @Override
                public void run() {
                    // 安装插件
                    Business.anzhuang(MainActivity.this);

                    SystemClock.sleep(3000);

                    //启动插件
                    Business.qidong(MainActivity.this);
                }
            }.start();
        }
    }

    /*private void installPackage() {
        // int COMPARE_VERSION = 0X01 << 3;
        // int SKIP_DEX_OPT = 0x01 << 6;
        // 或运算结果 72
        int flags = InstallStrategy.COMPARE_VERSION | InstallStrategy.SKIP_DEX_OPT;

        // 安装 SD 卡根目录中的 app.apk 文件
        // /storage/emulated/0/app.apk
        VirtualCore.get().installPackage(getFilesDir() + "/app.apk", flags);
    }*/

    /*private void startApp() {
        // 打开应用
        Intent intent =  VirtualCore.get().getLaunchIntent("kim.hsl.svg", 0);
        VActivityManager.get().startActivity(intent, 0);

        finish();
    }*/

    /**
     * 热修复 升级包 生成命令
     * @param view
     */
    public void onClick2(View view) {
        hotFix();
    }

    private void hotFix() {
        // 拷贝的目的文件目录
        // /data/user/0/kim.hsl.hotfix/app_odex/update.dex
        File targetDir = this.getDir("odex", Context.MODE_PRIVATE);
        // 拷贝的目的文件名称
        String targetName = "update.dex";

        // 准备目的文件, 将 Dex 文件从 SDK 卡拷贝到此文件中
        String filePath = new File(targetDir, targetName).getAbsolutePath();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        // 准备输入流, 读取 SD 卡文件
        InputStream is = null;
        // 准备输出流, 输出到目的文件
        FileOutputStream os = null;

        try {
            // 读取 SD 卡跟目录的 /storage/emulated/0/update.dex 文件
            //is = new FileInputStream(new File(Environment.getExternalStorageDirectory(), targetName));

            // 读取 /data/user/0/com.example.myapp/files/update.dex 字节码文件
            is = new FileInputStream(new File(getFilesDir(), "update.dex"));
            // 输出到目标文件
            os = new FileOutputStream(filePath);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }

            // 进行后续操作
            FixDexUtils.loadDex(this);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭 IO 流
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick3(View view) {
        // 创建测试类
        HotFixTest hotFixTest = new HotFixTest();
        // 调用产生异常的方法
        hotFixTest.test();
    }
}
