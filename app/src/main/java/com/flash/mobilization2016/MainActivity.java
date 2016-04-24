package com.flash.mobilization2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Request request = new Request();

    private ListView listView ;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private ArrayList<Artist> list = new ArrayList<>();
    private ArtistsListAdapter listAdapter;
    private JSONutils jsonUtils = new JSONutils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv_artists);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_updating);
        initToolbar();
        enableHttpResponseCache();
        loadInfo();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DescriptionActivity.class );
                intent.putExtra("artist", list.get(i));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.toolbar_title));
    }

    private void loadInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // получение json с сервера
                    String json = request.sendGet(Constants.URL, "");

                    // заполнение ArrayList данными из json
                    list = jsonUtils.Parse(json);
                    listAdapter = new ArtistsListAdapter(list, MainActivity.this);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(listAdapter);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Не удалось обновить данные",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    // кэширование
    private void enableHttpResponseCache() {
        try {
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            File httpCacheDir = new File(getCacheDir(), "http");
            Class.forName("android.net.http.HttpResponseCache")
                    .getMethod("install", File.class, long.class)
                    .invoke(null, httpCacheDir, httpCacheSize);
        } catch (Exception httpResponseCacheNotAvailable) {
            Log.d(Constants.LOG_TAG, "HTTP response cache is unavailable.");
        }
    }
}
