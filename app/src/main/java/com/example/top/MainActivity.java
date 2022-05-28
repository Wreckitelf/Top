package com.example.top;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureToSignIn();
        configureToCreateAcc();
    }

    private void configureToSignIn()
    {
        Button toSignIn = (Button) findViewById(R.id.toSignIn);
        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInScreen.class));
            }
        });

    }
    private void configureToCreateAcc()
    {
        Button toCreateAcc = (Button) findViewById(R.id.toCreateAcc);
        toCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateAccountScreen.class));
            }
        });

    }
}