package com.flash.mobilization2016;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Request {

    private final String USER_AGENT = "Mozilla/5.0";
    private final int RESPONSE_CODE_OK = 200;

    public String sendGet(String url, String urlParameters) throws Exception {

        URL obj = new URL(url + urlParameters);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        if (responseCode!=RESPONSE_CODE_OK) {
            return "Ошибка:" + Integer.toString(responseCode);
        } else {
            return response.toString();
        }

    }

}
