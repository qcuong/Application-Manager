package com.example.quoccuong.applicationmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class AppInforActivity extends Activity {

    private int index;
    private PackageManager pm;
    private ApplicationInfo app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_infor);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        TextView app_infor = (TextView) findViewById(R.id.app_infor);
        Button unistall = (Button) findViewById(R.id.unistall);
        Button lunch = (Button) findViewById(R.id.lunch);

        app = MyData.applist.get(index);

        String infor = "infor";
        infor += "Package: " + app.packageName;
        infor += "---Directory: " + app.sourceDir;
        infor += "---Icon: " + app.icon;

        try {

            pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(app.packageName,
                    PackageManager.GET_PERMISSIONS);
            Date installTime = new Date(packageInfo.firstInstallTime);
            Date updateTime = new Date(packageInfo.lastUpdateTime);

            infor += "---Installed: " + installTime.toString();
            infor += "---Updated: " + updateTime.toString();
            infor += "---Version: " + packageInfo.versionCode;
            infor += "---Name: " + packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            String TAG = "xxxxx";
            Log.d(TAG, "Error");
        }


//        String TAG = "xxxxx";
//        Log.d(TAG, "Package: " + app.packageName);
//        Log.d(TAG, "Directory: " + app.sourceDir);
//        Log.d(TAG, "Icon: " + app.icon);

        app_infor.setText(infor);
        ButtonListener buttonListener = new ButtonListener();
        unistall.setOnClickListener(buttonListener);
        lunch.setOnClickListener(buttonListener);
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.lunch) {
                Intent intent = pm.getLaunchIntentForPackage(app.packageName);
                startActivity(intent);
                finish();
            } else if (view.getId() == R.id.unistall) {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + app.packageName));
                startActivity(intent);
                finish();
            }
        }
    }
}
