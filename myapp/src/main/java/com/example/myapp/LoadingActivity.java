package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;

/**
 * @author Lody
 */

public class LoadingActivity extends AppCompatActivity {

    private static final String PKG_NAME_ARGUMENT = "MODEL_ARGUMENT";
    private static final String KEY_INTENT = "KEY_INTENT";
    private static final String KEY_USER = "KEY_USER";
    //private PackageAppData appModel;
    //private EatBeansView loadingView;

    public static void launch(Context context, String packageName, int userId) {
        Intent intent = VirtualCore.get().getLaunchIntent(packageName, userId);
        Log.i("HSL", "packageName : " + packageName + " , userId : " + userId + " , intent : " + intent) ;
        if (intent != null) {
            Intent loadingPageIntent = new Intent(context, LoadingActivity.class);
            loadingPageIntent.putExtra(PKG_NAME_ARGUMENT, packageName);
            loadingPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            loadingPageIntent.putExtra(KEY_INTENT, intent);
            loadingPageIntent.putExtra(KEY_USER, userId);
            context.startActivity(loadingPageIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        //loadingView = (EatBeansView) findViewById(R.id.loading_anim);
        int userId = getIntent().getIntExtra(KEY_USER, -1);
        String pkg = getIntent().getStringExtra(PKG_NAME_ARGUMENT);
        //appModel = PackageAppDataStorage.get().acquire(pkg);
        ImageView iconView = (ImageView) findViewById(R.id.app_icon);
        //iconView.setImageDrawable(appModel.icon);
        TextView nameView = (TextView) findViewById(R.id.app_name);
        //nameView.setText(String.format(Locale.ENGLISH, "Opening %s...", appModel.name));
        Intent intent = getIntent().getParcelableExtra(KEY_INTENT);
        if (intent == null) {
            return;
        }
        VirtualCore.get().setUiCallback(intent, mUiCallback);

        /*VUiKit.defer().when(() -> {

        });*/

        try {
            VirtualCore.get().preOpt("kim.hsl.svg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        VActivityManager.get().startActivity(intent, userId);
    }

    private final VirtualCore.UiCallback mUiCallback = new VirtualCore.UiCallback() {

        @Override
        public void onAppOpened(String packageName, int userId) throws RemoteException {
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //loadingView.startAnim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //loadingView.stopAnim();
    }
}
