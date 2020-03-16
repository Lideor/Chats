package com.example.chats;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity  extends AppCompatActivity {
    private SharedPreferences sPref;//файл настроек инфа о том кто сейчас залогинен
    public static String  LOG_TAG = "mylogs";

    public int login_id = 1;//


    Toolbar mActionBarToolbar;// тул бар
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);// создаем менджер отвечающий за расположение объектов
        rv.setLayoutManager(llm); // устанавливаем созданный менждер

        ListDialogs asd = new JsonParse().importDialogs(this,1);

        RVAdapterListDialogs adapter = new RVAdapterListDialogs(asd,this);//создаем аддаптер, отвечающий за создание
        rv.setAdapter(adapter); //устанавливаем только что созданный адаптер для основного списком контента

        Log.d(LOG_TAG,"Exp=");
        //getting token from shared preferences
        String token = SharedPrefManager.getInstance(this).getDeviceToken();
        Log.d(LOG_TAG,"token="+token);
        //if token is not null

    }
}
