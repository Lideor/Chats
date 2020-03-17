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
import static com.example.chats.MainActivity.LOG_TAG;

public class JsonParse {

    Context ctn;
    public final String listDialogs = "http://www.zaural-vodokanal.ru/php/massage/get_all_dialog.php"; // Файл расписания
    public final String listMessage = "http://www.zaural-vodokanal.ru/php/massage/get_one_dialog.php"; // Файл расписания
    public final String listNewDialogs = "http://www.zaural-vodokanal.ru/php/massage/get_null_dialog.php"; // Файл расписания


    public final String listData = "Data.json"; // Файл расписания
    private DiaogsTask dialog;
    private MessageTask message;
    private DiaogsNewTask dialogNew;


    public ListDialogs importDialogs(Context ctn,int id){

        Gson gson = new GsonBuilder().create();
        ListDialogs data = null;
        dialog = new DiaogsTask();
        dialog.execute(id);
        try {
            String jsonString = dialog.get();
            data = gson.fromJson(jsonString,ListDialogs.class);
        }
        catch (Exception e) {
            Log.d(LOG_TAG,"Exp=" + e);
        }
        return data;
    }

    public ListDialogs importNewDialogs(Context ctn){

        Gson gson = new GsonBuilder().create();
        ListDialogs data = null;
        dialogNew = new DiaogsNewTask();
        dialogNew.execute();
        try {
            String jsonString = dialogNew.get();
            data = gson.fromJson(jsonString,ListDialogs.class);
        }
        catch (Exception e) {
            Log.d(LOG_TAG,"Exp=" + e);
        }
        return data;
    }

    public ListMessage importMessage(Context ctn,int id){

        Gson gson = new GsonBuilder().create();
        ListMessage data = null;
        message = new MessageTask();
        message.execute(id);
        try {
            String jsonString = message.get();
            data = gson.fromJson(jsonString,ListMessage.class);
        }
        catch (Exception e) {
            Log.d(LOG_TAG,"Exp=" + e);
        }
        return data;
    }

    class DiaogsTask extends AsyncTask<Integer, String, String> {

        @Override
        protected String doInBackground(Integer... params) {

            try {
                //создаем запрос на сервер
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                //он у нас будет посылать post запрос
                HttpPost postMethod = new HttpPost(listDialogs);
                //будем передавать два параметра
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("login_id",params[0].toString()));

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

    class MessageTask extends AsyncTask<Integer, String, String> {

        @Override
        protected String doInBackground(Integer... params) {

            try {
                //создаем запрос на сервер
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                //он у нас будет посылать post запрос
                HttpPost postMethod = new HttpPost(listMessage);
                //будем передавать два параметра
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("id_dialogs",params[0].toString()));

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

    class DiaogsNewTask extends AsyncTask<Integer, String, String> {

        @Override
        protected String doInBackground(Integer... params) {

            try {
                //создаем запрос на сервер
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                //он у нас будет посылать post запрос
                HttpPost postMethod = new HttpPost(listDialogs);
                //будем передавать два параметра
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("login_id",params[0].toString()));

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

