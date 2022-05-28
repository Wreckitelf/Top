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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountScreen extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private TextView banner, registerAccount;
    private EditText editTextEmail, editTextPass, editTextUser;

    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);
        configureBackToHome();

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.CreateAccountTextView);

        registerAccount = (Button) findViewById(R.id.CompleteCreateAcc);
        registerAccount.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.InputEmailCreateAcc);
        editTextPass = (EditText) findViewById(R.id.InputPassCreateAcc);
        editTextUser = (EditText) findViewById(R.id.InputUserCreateAcc);

        pb = (ProgressBar) findViewById(R.id.createAccPB);

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
                String email = editTextEmail.getText().toString().trim();
                String username = editTextUser.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();

                if(email.isEmpty())
                {
                    editTextEmail.setError("Enter A email!");
                    editTextEmail.requestFocus();
                    return;
                }

                if(username.isEmpty())
                {
                    editTextUser.setError("Enter a Username!");
                    editTextUser.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    editTextEmail.setError("Please enter a valid email");
                    editTextEmail.requestFocus();
                    return;
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
                    return;
                }

                pb.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {
                                    User user = new User(email, username);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(email).addOnCompleteListener(new OnCompleteListener<Void>()
                                            {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(CreateAccountScreen.this, "Your account has been created", Toast.LENGTH_LONG).show();
                                                        pb.setVisibility(View.GONE);
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(CreateAccountScreen.this, "Failed to Register!", Toast.LENGTH_LONG).show();
                                                        pb.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                }
                                else
                                {
                                    Toast.makeText(CreateAccountScreen.this, "Failed to Register!", Toast.LENGTH_LONG).show();
                                    pb.setVisibility(View.GONE);

                                }
                            }
                        });
    }
}