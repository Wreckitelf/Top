package com.example.top;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountScreen extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private TextView banner, registerAccount;
    private EditText editTextUser, editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);
        configureBackToHome();

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.CreateAccountTextView);

        registerAccount = (Button) findViewById(R.id.CompleteCreateAcc);
        registerAccount.setOnClickListener(this);

        editTextUser = (EditText) findViewById(R.id.InputUserCreateAcc);
        editTextPass = (EditText) findViewById(R.id.InputPassCreateAcc);

    }

    private void configureBackToHome()
    {
        Button backBtnCreateAcc = (Button) findViewById(R.id.CreateAccBackBtn);
        backBtnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
            @Override
            public void onClick(View v) {
                String user = editTextUser.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();

                if(user.isEmpty())
                {
                    editTextUser.setError("Enter A Username!");
                    editTextUser.requestFocus();
                }

                if(pass.isEmpty())
                {
                    editTextPass.setError("Enter A Password!");
                    editTextPass.requestFocus();
                    return;
                }

                if(pass.length() < 6)
                {
                    editTextPass.setError("Password should be a minimum of 6 characters");
                    editTextPass.requestFocus();
                }
    }
}