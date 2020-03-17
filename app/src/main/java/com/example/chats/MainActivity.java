package com.example.chats;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sPref;//файл настроек инфа о том кто сейчас залогинен
    public static String LOG_TAG = "mylogs";

    public int login_id = 1;//
    public final static String PARAM_TIME = "time";
    public final static String PARAM_TASK = "task";
    public final static String PARAM_RESULT = "result";
    public final static String PARAM_STATUS = "status";

    public final static String BROADCAST_ACTION = "com.example.chats";
    ListDialogs dataDialogs;
    BroadcastReceiver br;
    Toolbar mActionBarToolbar;// тул бар

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mActionBarToolbar.setTitle("Ваши диалоги");
        setSupportActionBar(mActionBarToolbar);

        if (loadLogin_id() == 0) {
            Intent stopIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(stopIntent);
        }



     }
    @Override
    protected void onResume() {
        super.onResume();

         RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);// создаем менджер отвечающий за расположение объектов
        rv.setLayoutManager(llm); // устанавливаем созданный менждер

        dataDialogs = new JsonParse().importDialogs(this, login_id);

        final RVAdapterListDialogs adapter = new RVAdapterListDialogs(dataDialogs, this);//создаем аддаптер, отвечающий за создание
        rv.setAdapter(adapter); //устанавливаем только что созданный адаптер для основного списком контента
        //getting token from shared preferences
        // создаем BroadcastReceiver

        br = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                String json = intent.getStringExtra(PARAM_RESULT);
                Log.d(LOG_TAG, "onReceive: task = "+ json );
                // Ловим сообщения об окончании задач
                Gson gson = new GsonBuilder().create();
                Dialogs data = null;

                try {
                    data = gson.fromJson(json,Dialogs.class);
                }
                catch (Exception e) {
                    Log.d(LOG_TAG,"Exp=" + e);
                }
                adapter.changes(data);
            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
// дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(br);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    int loadLogin_id() {
        sPref = getSharedPreferences("prefs",MODE_PRIVATE);
        login_id = sPref.getInt("login_id", -1);
        if (login_id == -1) return 0;
        else return 1;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sPref = getSharedPreferences("prefs",MODE_PRIVATE);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            SharedPreferences.Editor ed = sPref.edit();
            ed.clear().commit();
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    class MessageTask extends AsyncTask<Integer, String, String> {

        @Override
        protected String doInBackground(Integer... params) {

            try {
                //создаем запрос на сервер
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                //он у нас будет посылать post запрос
                HttpPost postMethod = new HttpPost("http://zaural-vodokanal.ru/php/massage/send_message.php");
                //будем передавать два параметра
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                ;

                postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //получаем ответ от сервера
                String response = hc.execute(postMethod, res);
                Log.d(LOG_TAG, "response=" + response);
                return response;

            } catch (Exception e) {
                Log.d(LOG_TAG, "Exp=" + e);
            }
            return null;
        }
    }
}
