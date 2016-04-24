package com.flash.mobilization2016;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONutils {

    public ArrayList<Artist> Parse(String JSONstring) {
        ArrayList<Artist> list = new ArrayList<>();

        try {
            JSONArray artistsJsonArray = new JSONArray(JSONstring);
            for (int i = 0; i < artistsJsonArray.length(); i++) {
                JSONObject artistJsonObject = artistsJsonArray.getJSONObject(i);

                // получение списка жанров в формате json
                JSONArray genresJsonArray = artistJsonObject.getJSONArray("genres");
                ArrayList<String> genresList = new ArrayList<>();

                // заполнение списка жанорв
                for (int genrePosition = 0; genrePosition < genresJsonArray.length(); genrePosition++) {
                    genresList.add(genresJsonArray.getString(genrePosition));
                }

                Artist artist = new Artist(artistJsonObject.getInt("id"),
                        getJSONObjectString(artistJsonObject, "name"),
                        genresList,
                        artistJsonObject.getInt("tracks"),
                        artistJsonObject.getInt("albums"),
                        getJSONObjectString(artistJsonObject, "link"),
                        getJSONObjectString(artistJsonObject, "description"),
                        getJSONObjectString(artistJsonObject.getJSONObject("cover"), "small"),
                        getJSONObjectString(artistJsonObject.getJSONObject("cover"), "big"));


                list.add(artist);


            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w(Constants.LOG_TAG, "invalid json format");
        }

        return list;
    }

    // проверка на наличие строки в объекте json

    private String getJSONObjectString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException ex) {
            Log.w(Constants.LOG_TAG, "can't parse \"" + key + "\" value");
            return "";
        }
    }
}
