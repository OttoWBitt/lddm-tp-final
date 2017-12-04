package com.example.otto.trabalhopratico3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class NewUserActivity extends AppCompatActivity {

    EditText newUser,newPassword;
    Button createNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        newUser = (EditText)findViewById(R.id.newUser);
        newPassword = (EditText)findViewById(R.id.newPassword);
        createNewUser = (Button)findViewById(R.id.buttonNewUser);
    }
}
