package com.example.chats;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.api.Releasable;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.chats.MainActivity.LOG_TAG;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sPref;//Настройка
    String urlCreate = "http://www.zaural-vodokanal.ru/php/massage/create_login.php";
    String urlAuth = "http://www.zaural-vodokanal.ru/php/massage/auth_login.php";

    int sWitchSet = 0;
    int buttonSwitchSet = 1;
    Toolbar mActionBarToolbar;// тул бар

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadLogin_id();

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mActionBarToolbar.setTitle("Авторизация");
        setSupportActionBar(mActionBarToolbar);

        RelativeLayout rv = (RelativeLayout) findViewById(R.id.type);
        final EditText login = (EditText) rv.findViewById(R.id.login);
        final EditText password = (EditText) rv.findViewById(R.id.password);
        final EditText surName = (EditText) rv.findViewById(R.id.surName);
        final EditText fistName = (EditText) rv.findViewById(R.id.fistName);
        final Button btn = (Button) rv.findViewById(R.id.button);
        final Button btnSwitch = (Button) rv.findViewById(R.id.buttonSwitch);

        final Switch sWitch = (Switch) rv.findViewById(R.id.switch1);
        final String token = SharedPrefManager.getInstance(this).getDeviceToken();

        sWitch.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (sWitchSet == 1) sWitchSet = 0;
                else sWitchSet = 1;
            }
        });
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (buttonSwitchSet == 1) {
                    buttonSwitchSet = 0;
                    surName.setVisibility(View.INVISIBLE);
                    fistName.setVisibility(View.INVISIBLE);
                    sWitch.setVisibility(View.INVISIBLE);
                    btn.setText("Авторизоваться");
                    btnSwitch.setText("Регестрация");
                    mActionBarToolbar.setTitle("Авторизация");


                } else {
                    buttonSwitchSet = 1;
                    surName.setVisibility(View.VISIBLE);
                    fistName.setVisibility(View.VISIBLE);
                    sWitch.setVisibility(View.VISIBLE);
                    btn.setText("Зарегестрироваться");
                    btnSwitch.setText("Авторизация");
                    mActionBarToolbar.setTitle("Регестрация");

                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (buttonSwitchSet == 1) {
                    RegestrationTask log = new RegestrationTask();
                    try {
                        log.execute(new String(login.getText().toString().getBytes("UTF-8")),
                                new String(password.getText().toString().getBytes("UTF-8")),
                                new String(surName.getText().toString().getBytes("UTF-8")),
                                new String(fistName.getText().toString().getBytes("UTF-8")),
                                token, "" + sWitchSet);

                    } catch (Exception e) {
                        Log.d(LOG_TAG, "Exp=" + e);
                    }
                }

            else{
                    AuthTask log = new AuthTask();
                try {
                    log.execute(new String(login.getText().toString().getBytes("UTF-8")),
                            new String(password.getText().toString().getBytes("UTF-8")),
                            token);

                } catch (Exception e) {
                    Log.d(LOG_TAG, "Exp=" + e);
                }
            }
        }
    });
}

    int loadLogin_id() {
        sPref = getSharedPreferences("prefs", MODE_PRIVATE);
        int login_id = sPref.getInt("login_id", -1);
        if (login_id == -1) return 0;
        else return 1;

    }

class RegestrationTask extends AsyncTask<String, Integer, Integer> {

    @Override
    protected Integer doInBackground(String... params) {

        try {
            //создаем запрос на сервер
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            //он у нас будет посылать post запрос
            HttpPost postMethod = new HttpPost(urlCreate);
            //будем передавать два параметра
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            //передаем параметры из наших текстбоксов
            //лоигн
            nameValuePairs.add(new BasicNameValuePair("loginName", params[0]));
            nameValuePairs.add(new BasicNameValuePair("password", params[1]));
            nameValuePairs.add(new BasicNameValuePair("surName", params[2]));
            nameValuePairs.add(new BasicNameValuePair("fistName", params[3]));
            nameValuePairs.add(new BasicNameValuePair("token", params[4]));
            nameValuePairs.add(new BasicNameValuePair("typeUsers", params[5]));


            //пароль
            //собераем их вместе и посылаем на сервер
            postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            //получаем ответ от сервера
            String response = hc.execute(postMethod, res);
            if (response.equals("-2")) return 0;
            if (!response.equals("-1")) {
                int num = Integer.parseInt(response);
                sPref = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt("login_id", num);
                ed.putInt("typeUsers", sWitchSet);

                ed.putString("loginName", params[0]);
                ed.apply();
                finish();
            } else return 1;
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exp=" + e);
        }
        return null;
    }
}

class AuthTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            try {
                //создаем запрос на сервер
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                //он у нас будет посылать post запрос
                HttpPost postMethod = new HttpPost(urlAuth);
                //будем передавать два параметра
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                //передаем параметры из наших текстбоксов
                //лоигн
                nameValuePairs.add(new BasicNameValuePair("loginName", params[0]));
                nameValuePairs.add(new BasicNameValuePair("password", params[1]));
                nameValuePairs.add(new BasicNameValuePair("token", params[2]));



                //пароль
                //собераем их вместе и посылаем на сервер
                postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                //получаем ответ от сервера
                String response = hc.execute(postMethod, res);
                if (response.equals("-1")) return 0;
                else {

                    JSONObject data = new JSONObject(response);
                    sPref = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putInt("login_id", data.getInt("id_login"));
                    ed.putInt("typeUsers", data.getInt("status"));

                    ed.apply();
                    finish();
                }
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exp=" + e);
            }
            return null;
        }
    }

}
