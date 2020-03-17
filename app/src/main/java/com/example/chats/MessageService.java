package com.example.chats;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.chats.MainActivity.LOG_TAG;

public class MessageService  extends FirebaseMessagingService {
    private static final String TAG = LOG_TAG;





    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(LOG_TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception: " + e.getMessage());
            }
        }
    }
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            Log.v(LOG_TAG, "JSON: "+ json.toString());
            Gson gson = new GsonBuilder().create();
            Message data = null;

            try {
                data = gson.fromJson(json.toString(),Message.class);
            }
            catch (Exception e) {
                Log.d(LOG_TAG,"Exp=" + e);
            }
            //parsing json data
            String title = data.getFirsName()+" "+data.getSurName();
            String message = data.getText();
           // String imageUrl = json.getString("image");
            Intent test = new Intent(MainActivity.BROADCAST_ACTION);

            test.putExtra(MainActivity.PARAM_RESULT, json.toString());

            Log.v(LOG_TAG, "test: "+test.toString());

            sendBroadcast(test);

            Log.v(LOG_TAG, "test: "+test.toString());
            //creating MyNotificationManager object
            MessageNotificationManager mNotificationManager = new MessageNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            mNotificationManager.showSmallNotification(title,message,intent);
            //if there is no image


        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}