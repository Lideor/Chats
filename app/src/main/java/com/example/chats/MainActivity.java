package com.example.chats;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity  extends AppCompatActivity {
    private SharedPreferences sPref;//файл настроек инфа о том кто сейчас залогинен

    private int login_id = 1;//


    Toolbar mActionBarToolbar;// тул бар
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
