package com.example.chats;

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

public class Chat extends AppCompatActivity {
    private SharedPreferences sPref;//файл настроек инфа о том кто сейчас залогинен
    public static String  LOG_TAG = "mylogs";

    private int idDialogs;
    private int idLogin=1;
    public int login_id = 1;//


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
                adapter.addNewMessage(idLogin,idDialogs,text.getText().toString());
                rv.smoothScrollToPosition(rv.getAdapter().getItemCount()-1);

            }
        });

        Log.d(LOG_TAG,"Exp=");

    }
}
