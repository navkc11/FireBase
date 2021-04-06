package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText fullName,password;
    private Button saveToFireBase;
    private Button getFromFireBase;
    private TextView fullNameText,passwordText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressBar.setVisibility(View.GONE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        }


    private void init() {
        fullName=findViewById(R.id.full_name);
        password=findViewById(R.id.password);
        saveToFireBase=findViewById(R.id.save_to_firbase);
        getFromFireBase=findViewById(R.id.get_from_firebase);
        saveToFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(fullName.getText().toString(),password.getText().toString());
                db.collection("users").add(user);
                db.collection("users").document("test").set(user);
            }
        });
        getFromFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").document("test").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        passwordText=findViewById(R.id.password_text);
                        fullNameText=findViewById(R.id.full_name_text);
                        User user1=task.getResult().toObject(User.class);
                        passwordText.setText(user1.getPassword());
                        fullNameText.setText(user1.getFullName());
                    }
                });
            }
        });
    }
}