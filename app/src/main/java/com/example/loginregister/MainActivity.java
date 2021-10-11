package com.example.loginregister;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView textView_name, textView_pass;
    Button btnLogout;
    SharedPreferences sharedPreferences;


    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_name = (TextView) findViewById(R.id.text_fullname);
        textView_pass = (TextView) findViewById(R.id.text_password);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_NAME, null);
        String pass = sharedPreferences.getString(KEY_PASS, null);

        if (name != null || pass != null) {
            textView_name.setText("Full name : "+name);
            textView_pass.setText("Password : "+pass);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(MainActivity.this, "Log out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}