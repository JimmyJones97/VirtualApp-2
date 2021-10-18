package com.example.myapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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

        new Thread(){
            @Override
            public void run() {
                File file = new File(getFilesDir(), "app.apk");
                // 如果文件不存在 , 则拷贝文件
                if (!file.exists()) {
                    // 拷贝文件到内置存储
                    copyFile();
                }
            }
        }.start();
    }

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
