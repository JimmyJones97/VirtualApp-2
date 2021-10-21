package com.example.myapp;

import android.content.Context;
import android.content.Intent;

import com.lody.virtual.client.core.InstallStrategy;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;

import android.app.Activity;
import android.util.Log;

/**
 * Created by octop on 2021/10/19.
 */

public class Business {

    /**
     * 启动插件化引擎
     */
    public static void startEngine(Context base) {
        Log.i("HSL", "startEngine 方法入口");
        try {
            VirtualCore.get().startup(base);
            Log.i("HSL", "startEngine 启动 VA 引擎");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Log.i("HSL", "startEngine 方法结束");
    }

    /**
     * 安装插件 APK
     */
    public static void anzhuang(Context context) {
        Log.i("HSL", "anzhuang 安装插件 方法入口");
        // int COMPARE_VERSION = 0X01 << 3;
        // int SKIP_DEX_OPT = 0x01 << 6;
        // 或运算结果 72
        int flags = InstallStrategy.COMPARE_VERSION | InstallStrategy.SKIP_DEX_OPT;

        // 安装 SD 卡根目录中的 app.apk 文件
        // /storage/emulated/0/app.apk
        VirtualCore.get().installPackage(context.getFilesDir() + "/app.apk", flags);
        Log.i("HSL", "anzhuang 安装插件");

        Log.i("HSL", "anzhuang 安装插件 方法结束");
    }

    /**
     * 启动插件 APK
     */
    public static void qidong(Activity activity) {
        Log.i("HSL", "qidong 启动插件 方法入口");

        // 打开应用
        Intent intent =  VirtualCore.get().getLaunchIntent("kim.hsl.svg", 0);
        VActivityManager.get().startActivity(intent, 0);
        Log.i("HSL", "qidong 启动插件");

        activity.finish();

        Log.i("HSL", "qidong 启动插件 方法结束");
    }
}
