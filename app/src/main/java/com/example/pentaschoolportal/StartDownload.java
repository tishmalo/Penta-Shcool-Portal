package com.example.pentaschoolportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartDownload extends AppCompatActivity {

    Toolbar tb;
    TextView title;
    Button download;

    String TITLE,DOCUMENT,CODE;

    DownloadManager mgr=null;
    long lastDownload=-1L;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 200;
    private boolean permissionToStorePdf = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                permissionToStorePdf = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToStorePdf ) finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_download);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        29);
                finish();

            }
        }


        mgr=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        registerReceiver(onNotificationClick, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));

        Intent intent= getIntent();
        TITLE=intent.getStringExtra("title");
        DOCUMENT=intent.getStringExtra("document");
        CODE=intent.getStringExtra("code");

        tb=findViewById(R.id.toolBar);
        title=findViewById(R.id.LecName);
        download=findViewById(R.id.download);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title.setText(TITLE);

        startDownloading();





    }

    private void startDownloading() {


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri= Uri.parse(DOCUMENT);
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        .mkdirs();

                lastDownload=
                        mgr.enqueue(new DownloadManager.Request(uri)
                                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                        DownloadManager.Request.NETWORK_MOBILE)
                                .setAllowedOverRoaming(false)
                                .setTitle(TITLE)
                                .setDescription("UNIT NOTES")
                                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                        TITLE + ".pdf"));


                download.setEnabled(false);

            }
        });
            }
    BroadcastReceiver onComplete=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            findViewById(R.id.download).setEnabled(true);
            Toast.makeText(StartDownload.this, "pdf downloaded", Toast.LENGTH_SHORT).show();

        }
    };

    BroadcastReceiver onNotificationClick=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt, "Ummmm...hi!", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(onComplete);
        unregisterReceiver(onNotificationClick);
    }


    }

