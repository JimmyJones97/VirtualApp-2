package com.example.myapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp.hotfix.FixDexUtils;
import com.lody.virtual.client.core.VirtualCore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VApp extends Application {

    private static VApp gApp;

    public static VApp getApp() {
        return gApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);

        // 每次启动应用都先进行修复包加载操作
        FixDexUtils.loadDex(base);

        super.attachBaseContext(base);
        try {
            VirtualCore.get().startup(base);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        gApp = this;
        super.onCreate();

        /*new Thread(){
            @Override
            public void run() {
                File file = new File(getFilesDir(), "app.apk");
                // 如果文件不存在 , 则拷贝文件
                if (!file.exists()) {
                    // 拷贝文件到内置存储
                    copyFile();
                }
            }
        }.start();*/

        File file = new File(getFilesDir(), "app.apk");
        // 如果文件不存在 , 则拷贝文件
        if (!file.exists()) {
            // 拷贝文件到内置存储
            copyFile();
            Toast.makeText(this, file.getAbsolutePath() +  " 文件拷贝完毕 , 可以安装插件", Toast.LENGTH_LONG).show();
            Log.i("HSL", file.getAbsolutePath() +  " 文件拷贝完毕 , 可以安装插件");
        } else {
            Toast.makeText(this, file.getAbsolutePath() + " 文件已存在 , 可以安装插件", Toast.LENGTH_LONG).show();
            Log.i("HSL", file.getAbsolutePath() + " 文件已存在 , 可以安装插件");
        }

    }

    /**
     * 将 VirtualApp\myapp\src\main\assets\app.apk 文件 ,
     * 拷贝到 /data/user/0/com.example.myapp/files/app.apk 位置
     */
    public void copyFile() {
        try {
            InputStream inputStream = getAssets().open("app.apk");
            FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), "app.apk"));

            byte[] buffer = new byte[1024 * 4];
            int readLen = 0;
            while ( (readLen = inputStream.read(buffer)) != -1 ) {
                fileOutputStream.write(buffer, 0, readLen);
            }

            inputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.i("HSL", "文件拷贝完毕");
        }
    }
}
