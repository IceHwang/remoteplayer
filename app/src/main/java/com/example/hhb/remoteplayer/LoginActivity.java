package com.example.hhb.remoteplayer;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private long exitTime=0;

    public static List<String> urlList = new ArrayList<>();

    public static String username;


    public static final int FAILED=0;
    public static final int SUCCESS=1;


    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;
    private ProgressDialog progressDialog;

    private Handler handler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case FAILED:
                    onLoginFailed();
                    break;
                case SUCCESS:
                    onLoginSuccess();
                    break;
            }
        }
    };

    private CheckBox remember;

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {

            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);


        remember =findViewById(R.id.remember);
        usernameText=findViewById(R.id.input_username);
        passwordText=findViewById(R.id.input_password);
        loginButton=findViewById(R.id.btn_login);

        boolean isRemember =preferences.getBoolean("isRemember",false);

        if(isRemember)
        {
            usernameText.setText(preferences.getString("username",""));
            passwordText.setText(preferences.getString("password",""));
            remember.setChecked(true);
        }



        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {

        loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("验证中...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String username = usernameText.getText().toString();
        final String password = passwordText.getText().toString();

        new Thread(new Runnable() {
            Mysql mysql=null;
            @Override
            public void run() {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    mysql=new Mysql("159.65.111.107:3306",username,password);


                } catch (Exception e) {
                    sendFailed();
                    e.printStackTrace();
                }

                try {
                    mysql.execute("use remoteplayer");
                    String[][] t=mysql.getResultSet("select url from url");
                    for (int i=1;i<t.length;i++)
                    {
                        urlList.add(t[i][0]);
                    }

                    sendSuccess();



                } catch (Exception e) {
                    sendFailed();
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void onLoginSuccess() {
        editor=preferences.edit();
        if (remember.isChecked())
        {
            editor.putBoolean("isRemember",true);
            editor.putString("username",usernameText.getText().toString());
            editor.putString("password",passwordText.getText().toString());
        }
        else
        {
            editor.clear();
        }
        editor.apply();

        username=usernameText.getText().toString();
        loginButton.setEnabled(true);
        finish();
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        loginButton.setEnabled(true);
    }

    public void sendFailed()
    {
        Message message=new Message();
        message.what=FAILED;
        handler.sendMessage(message);
    }

    public void sendSuccess()
    {
        Message message=new Message();
        message.what=SUCCESS;
        handler.sendMessage(message);
    }
}
