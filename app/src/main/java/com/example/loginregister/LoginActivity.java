package com.example.loginregister;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText TxUsername, TxPassword;
    Button BtnLogin;
    DBHelper dbHelper;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TxUsername = (EditText)findViewById(R.id.txUsername);
        TxPassword = (EditText)findViewById(R.id.txPassword);
        BtnLogin = (Button)findViewById(R.id.btnLogin);
        dbHelper = new DBHelper(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        if (name != null){
            Intent intent = new Intent (LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }


        TextView tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccount);

        tvCreateAccount.setText(fromHtml("I don't have account yet. " +
                "</font><font color='#3b5998'>create one</font>"));
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = TxUsername.getText().toString().trim();
                String password = TxPassword.getText().toString().trim();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME, TxUsername.getText().toString());
                editor.putString(KEY_PASS,TxPassword.getText().toString());
                editor.apply();


                Boolean res = dbHelper.checkUser(username,password);
                if(res == true){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}