package com.example.chats;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class JsonParse {

    private String LOG_TAG = "mylogs";


    Context ctn;
    public final String listGroup = "http://www.zaural-vodokanal.ru/php/massage/get_all_dialog.php"; // Файл расписания
    public final String listData = "Data.json"; // Файл расписания


    //выгрузка из файла пакетов
    public Dialogs importGroupJsonInFile(Context ctn,int id){


        String jsonString = convertStreamToString(ctn,listGroup);
        Gson gson = new GsonBuilder().create();
        Dialogs data = null;
        try {
             data = gson.fromJson(jsonString, Dialogs.class);
        } catch (ArithmeticException e) {
             Log.d(LOG_TAG,e.toString());
        }
        return data;
    }

    //выгрузка из файла пакетов
    public DataBase importDataJsonInFile(Context ctn){
        String jsonString = convertStreamToString(ctn,listData);
        Gson gson = new GsonBuilder().create();
        DataBase data = null;
        try {
            data = gson.fromJson(jsonString, DataBase.class);
        } catch (ArithmeticException e) {
            Log.d(LOG_TAG,e.toString());
        }
        return data;
    }

    private String convertStreamToString(Context ctn, String fileName) {


        String line = null;
        BufferedReader reader;
        String out = null;
        StringBuilder sb=null;
        try {
            AssetManager assetManager = ctn.getAssets();
            reader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
             sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(sb!=null) out = sb.toString();
        return out;
    }


    class DiaogsTask extends AsyncTask<Integer, String, String> {

        @Override
        protected String doInBackground(Integer... params) {

            try {
                //создаем запрос на сервер
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                //он у нас будет посылать post запрос
                HttpPost postMethod = new HttpPost(listGroup);
                //будем передавать два параметра
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("login_name",params[0].toString()));

                postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //получаем ответ от сервера
                String response = hc.execute(postMethod, res);
                return response;

            } catch (Exception e) {
                System.out.println("Exp=" + e);
            }
            return null;
        }
    }
}

