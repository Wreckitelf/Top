package com.example.top;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    TextView personRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureToSignIn();
        configureToCreateAcc();
        personRating =  (TextView) findViewById(R.id.personRating);
        String combine = "";
        for (int i = 0; i < 2; i++)
        {
            if(i == 0)
                combine += "- Josh";
            else
                combine += "/Everyone Else";

        }
        personRating.setText(combine);
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
    private void configureToCreateAcc() {
        Button toCreateAcc = (Button) findViewById(R.id.toCreateAcc);
        toCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateAccountScreen.class));
            }
        });



    }


}