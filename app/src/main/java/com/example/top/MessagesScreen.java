package com.example.top;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MessagesScreen extends AppCompatActivity {
    EditText etToken;
    Button send;
    TextView chat;
    EditText enterMessage;
    String chatText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_screen);
        enterMessage = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        chat = (TextView) findViewById(R.id.chat);

       /* etToken = findViewById(R.id.editTextToken);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {

                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println(token);
                        Toast.makeText(MessagesScreen.this, "Your device token is" + token, Toast.LENGTH_SHORT).show();
                        etToken.setText(token);
                    }
                });
           //This code allowed me to access my user token, which allows me to send notifications.
        */

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mref = db.getReference("Chat").child("Josh");

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren())
                {
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            chatText += user.username + ": " + child.getValue().toString() + "\n";
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    System.out.println(child.getValue().toString());
                }
                chat.setText(chatText);
                chatText = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.print(error.getDetails());
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mref.push().setValue(enterMessage.getText().toString());
            }
        });
    }




}