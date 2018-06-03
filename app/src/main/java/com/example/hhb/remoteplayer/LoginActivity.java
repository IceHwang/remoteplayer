package com.example.hhb.remoteplayer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    public static List<String> urlList = new ArrayList<>();

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText=findViewById(R.id.input_username);
        passwordText=findViewById(R.id.input_password);
        loginButton=findViewById(R.id.btn_login);


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
        progressDialog.setMessage("Authenticating...");
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



//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 10000);




    }

//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        // disable going back to the MainActivity
//        moveTaskToBack(true);
//    }
//
    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
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
