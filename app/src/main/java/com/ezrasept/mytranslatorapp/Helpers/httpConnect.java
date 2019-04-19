/*
 * This Project (MyTranslatorApp) Created by Ezra Septian
 * Copyright(c) 2019. All rights reserved.
 * Last modified 19/04/19 17:19
 */

package com.ezrasept.mytranslatorapp.Helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EzraSept on 02/12/2017.
 */

public class httpConnect {
    public httpConnect() {

    }


    public String makeHttpCall(String call) {
        String response = null;
        try {
            URL url = new URL(call);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();


            InputStream inputStream = connection.getInputStream();

            response = converter(inputStream);

        }catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


    private String converter(InputStream inputStream) {
        String resultJson;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line=bufferedReader.readLine())!=null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        resultJson = sb.toString();
        return resultJson;
    }
}
