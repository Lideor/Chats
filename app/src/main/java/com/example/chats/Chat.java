package com.example.chats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.example.chats.MainActivity.BROADCAST_ACTION;
import static com.example.chats.MainActivity.PARAM_RESULT;

public class Chat extends AppCompatActivity {
    private SharedPreferences sPref;//файл настроек инфа о том кто сейчас залогинен
    public static String  LOG_TAG = "mylogs";

    private int idDialogs;
    private int idLogin=1;
    public int login_id = 1;//

    BroadcastReceiver br;

    Toolbar mActionBarToolbar;// тул бар

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListMessage message = new ListMessage();
        setContentView(R.layout.activity_chat);
        Bundle arguments = getIntent().getExtras();

        final EditText text = (EditText) findViewById(R.id.newMessageText);

        if(arguments!=null){
            idDialogs = arguments.getInt("idDialogs");
            message = new JsonParse().importMessage(this,idDialogs);
            text.setText(""+idDialogs);
        }
        loadLogin_id();
        final RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);// создаем менджер отвечающий за расположение объектов
        rv.setLayoutManager(llm); // устанавливаем созданный менждер

        final RVAdapterListMessage adapter = new RVAdapterListMessage(message,this);//создаем аддаптер, отвечающий за создание
        rv.setAdapter(adapter);
        rv.smoothScrollToPosition(rv.getAdapter().getItemCount()-1);
        Button btn = (Button) findViewById(R.id.newMessageButton);
        final Message asd = message.getMessage(0);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Message newMessage = new Message();
                adapter.addNewMessage(login_id,idDialogs,text.getText().toString());
                rv.smoothScrollToPosition(rv.getAdapter().getItemCount()-1);

            }
        });
        br = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                String json = intent.getStringExtra(PARAM_RESULT);
                Log.d(LOG_TAG, "onReceive: task = "+ json );
                // Ловим сообщения об окончании задач
                Gson gson = new GsonBuilder().create();
                Message data = null;

                try {
                    data = gson.fromJson(json,Message.class);
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
        Log.d(LOG_TAG,"Exp=");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(br);
    }
    int loadLogin_id() {
        sPref = getSharedPreferences("prefs",MODE_PRIVATE);
        login_id = sPref.getInt("login_id", -1);
        if (login_id == -1) return 0;
        else return 1;

    }
}
