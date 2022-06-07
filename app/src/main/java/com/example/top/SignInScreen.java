package com.example.top;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInScreen extends AppCompatActivity{

    private EditText editTextEmail, editTextPass;
    private Button signIn;
    private FirebaseAuth mAuth;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        configureBackToHome();
        configureCompleteSignIn();


        editTextEmail = (EditText) findViewById(R.id.InputEmailSignIn);
        editTextPass = (EditText) findViewById(R.id.InputPassSignIn);

        mAuth = FirebaseAuth.getInstance();
    }

    private void configureBackToHome()
    {
        Button backBtnSignIn = (Button) findViewById(R.id.SignInBackBtn);
        backBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void configureCompleteSignIn()
    {

        signIn = (Button) findViewById(R.id.completeSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();

                if (email.isEmpty()) {
                    editTextEmail.setError("Enter A email!");
                    editTextEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Please enter a valid email");
                    editTextEmail.requestFocus();
                    return;
                }

                if (pass.isEmpty()) {
                    editTextPass.setError("Enter A Password!");
                    editTextPass.requestFocus();
                    return;
                }

               else if (pass.length() < 6) {
                    editTextPass.setError("Password should be a minimum of 6 characters");
                    editTextPass.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(SignInScreen.this, MessagesScreen.class));
                            pb.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(SignInScreen.this, "Failed to sign in", Toast.LENGTH_LONG).show();
                            pb.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

    }



}