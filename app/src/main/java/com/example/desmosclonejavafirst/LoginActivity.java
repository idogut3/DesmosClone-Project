package com.example.desmosclonejavafirst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameET, passwordET;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initView();
    }

//    private void initView(){
//        usernameET.findViewById(R.id.);
//        passwordET.findViewById(R.id.)
//    }

}