package com.flash.mobilization2016;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ArtistsListAdapter extends BaseAdapter {

    private ArrayList<Artist> list;
    private LayoutInflater lInflater;
    private View view;
    private Context context;

    public ArtistsListAdapter(ArrayList<Artist> list, Context context) {
        this.list = list;
        this.context = context;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Artist getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.artist_item, viewGroup, false);
        }

        loadInfo(position);

        return view;
    }

    // загрузка данных об артисте по позиции в ArrayList

    private void loadInfo(int position) {

        Artist artist = list.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_artist_photo);

        Picasso.with(context)
                .load(artist.getImage_small())
                .placeholder(R.drawable.image_placeholder)
                .into(imageView);

        TextView textViewName = (TextView) view.findViewById(R.id.tv_name);
        textViewName.setText(artist.getName());
        TextView textViewGenres = (TextView) view.findViewById(R.id.tv_genres);

        //преобразование списка жанров в одну строку
        StringBuilder sb = new StringBuilder();
        for (String genre : artist.getGenres()) {
            sb.append(genre);

            sb.append(", ");
        }
        try {
            sb.deleteCharAt(sb.lastIndexOf(", "));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textViewGenres.setText(sb.toString());
        TextView textViewAlbumsAndTracks = (TextView) view.findViewById(R.id.tv_albums_and_tracks);
        textViewAlbumsAndTracks.setText(context.getResources().getQuantityString(R.plurals.albums, artist.getAlbums(), artist.getAlbums()) + ", " +
                context.getResources().getQuantityString(R.plurals.tracks, artist.getTracks(), artist.getTracks()));


    }
}
