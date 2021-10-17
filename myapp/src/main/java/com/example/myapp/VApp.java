package com.example.myapp;

import android.app.Application;
import android.content.Context;

import com.lody.virtual.client.core.VirtualCore;

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
    }
}
