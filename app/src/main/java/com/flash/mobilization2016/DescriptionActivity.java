package com.flash.mobilization2016;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DescriptionActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        init();
    }


    private void init() {
        // получение информации об исполнителе
        artist = (Artist) getIntent().getSerializableExtra("artist");

        initToolbar();
        loadInfo();
    }

    private void loadInfo() {

        ImageView imageView = (ImageView) findViewById(R.id.iv_artist_photo);
        //загрузка изображения
        Picasso.with(this)
                .load(artist.getImage_big())
                .placeholder(R.drawable.image_placeholder)
                .into(imageView);

        TextView textViewLink = (TextView) findViewById(R.id.tv_link);

        //проверка на наличие ссылки
        if (artist.getLink().equals("")) {
            textViewLink.setVisibility(View.GONE);
        } else textViewLink.setText(artist.getLink());

        TextView textViewGenres = (TextView) findViewById(R.id.tv_genres);

        //преобразование списка жанров в одну строку
        StringBuilder sb = new StringBuilder();
        for (String genre : artist.getGenres()) {
            sb.append(genre);
            sb.append(", ");
        }
        // удаление лишней запятой
        try {
            sb.deleteCharAt(sb.lastIndexOf(", "));
        } catch (Exception e) {
            e.printStackTrace();
        }

        textViewGenres.setText(sb.toString());
        TextView textViewAlbumsAndTracks = (TextView) findViewById(R.id.tv_albums_and_tracks);
        textViewAlbumsAndTracks.setText(this.getResources().getQuantityString(R.plurals.albums, artist.getAlbums(), artist.getAlbums()) + ", " +
                this.getResources().getQuantityString(R.plurals.tracks, artist.getTracks(), artist.getTracks()));

        TextView textViewBiography = (TextView) findViewById(R.id.tv_biography);
        textViewBiography.setText(artist.getDescription());

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addBackArrow();
        setTitle(artist.getName());
    }


    // добавление анимации при возвращении

    private void backAnimation() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backAnimation();
    }

    //добавление стрелки в toolbar

    private void addBackArrow() {
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                backAnimation();
            }
        });
    }
}
