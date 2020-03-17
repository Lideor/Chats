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

public class DialogActivity  extends AppCompatActivity {
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
        ListDialogs message = new ListDialogs();
        setContentView(R.layout.activity_new_dialog);
        Bundle arguments = getIntent().getExtras();

        final EditText text = (EditText) findViewById(R.id.newMessageText);

        if(arguments!=null){
            idDialogs = arguments.getInt("idDialogs");
            text.setText(""+idDialogs);
        }
        message = new JsonParse().importNewDialogs(this);

        loadLogin_id();
        final RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);// создаем менджер отвечающий за расположение объектов
        rv.setLayoutManager(llm); // устанавливаем созданный менждер

        final RVAdapterListNewDialog adapter = new RVAdapterListNewDialog(message,this);//создаем аддаптер, отвечающий за создание
        rv.setAdapter(adapter);
        rv.scrollToPosition(rv.getAdapter().getItemCount()-1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// дерегистрируем (выключаем) BroadcastReceiver

    }
    int loadLogin_id() {
        sPref = getSharedPreferences("prefs",MODE_PRIVATE);
        login_id = sPref.getInt("login_id", -1);
        if (login_id == -1) return 0;
        else return 1;

    }
}
