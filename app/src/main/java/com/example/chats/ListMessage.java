package com.example.chats;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static com.example.chats.MainActivity.LOG_TAG;

public class ListMessage {
    List<Message> message = new ArrayList<Message>();
    public final String newMessageSring = "http://www.zaural-vodokanal.ru/php/massage/get_one_dialog.php"; // Файл расписания
    private NewMessageTask messageNewTask;

    public int getSize(){return message.size();}
    public Message getMessage(int number){ return message.get(number);}

    public int addNewMessage(Message newMessage){

        messageNewTask = new NewMessageTask();
        messageNewTask.execute(""+newMessage.getIdDialogs(),""+newMessage.getIdLogin(),
                newMessage.getDate(), newMessage.getText());

        Gson gson = new GsonBuilder().create();

        class AnswerNewMessage{
            public int idMessage;
            public String surName;
            public String firstName;
        }

        try {
            String jsonString = messageNewTask.get();
            AnswerNewMessage data = gson.fromJson(jsonString,AnswerNewMessage.class);
            newMessage.setIdMessage(data.idMessage);
            newMessage.setSurName(data.surName);
            newMessage.setFirsName(data.surName);
            message.add(newMessage);
            return 1;

        }
        catch (Exception e) {
            Log.d(LOG_TAG,"Exp=" + e);
            return 0;
        }
    }

    private class NewMessageTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                //создаем запрос на сервер
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                //он у нас будет посылать post запрос
                HttpPost postMethod = new HttpPost(newMessageSring);
                //будем передавать два параметра
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("id_dialogs",params[0].toString()));
                nameValuePairs.add(new BasicNameValuePair("id_login",params[1].toString()));
                nameValuePairs.add(new BasicNameValuePair("data",params[2].toString()));
                nameValuePairs.add(new BasicNameValuePair("text",params[3].toString()));

                postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //получаем ответ от сервера
                String response = hc.execute(postMethod, res);
                return response;

            } catch (Exception e) {
                System.out.println("Exp=" + e);
                return "-1";

            }
           // return null;
        }
    }
}
