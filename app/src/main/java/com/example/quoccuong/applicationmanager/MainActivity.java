package com.example.quoccuong.applicationmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}

//package com.javatechig.listapps;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    private PackageManager packageManager = null;
    private ApplicationAdapter listadaptor = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager = getPackageManager();

        new LoadApplications().execute();
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        boolean result = true;
//
//        switch (item.getItemId()) {
//            case R.id.menu_about: {
//                displayAboutDialog();
//
//                break;
//            }
//            default: {
//                result = super.onOptionsItemSelected(item);
//
//                break;
//            }
//        }
//
//        return result;
//    }

    private void displayAboutDialog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.about_title));
//        builder.setMessage(getString(R.string.about_desc));
//
//
//        builder.setPositiveButton("Know More", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com"));
//                startActivity(browserIntent);
//                dialog.cancel();
//            }
//        });
//        builder.setNegativeButton("No Thanks!", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ApplicationInfo app = MyData.applist.get(position);
        try {
            //Intent intent = packageManager
            //        .getLaunchIntentForPackage(app.packageName);

            //Intent intent = new Intent(Intent.ACTION_DELETE);
            //intent.setData(Uri.parse("package:" + app.packageName));

            //startActivity(intent);

           // if (null != intent) {
           //     startActivity(intent);
           // }


            Intent intent = new Intent(MainActivity.this, AppInforActivity.class);
            intent.putExtra("index", position);
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            MyData.applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(MainActivity.this,
                    R.layout.snippet_list_row, MyData.applist);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MainActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
