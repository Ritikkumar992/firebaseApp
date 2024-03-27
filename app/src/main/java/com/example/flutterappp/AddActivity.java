package com.example.flutterappp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText name,email,courses,imgUrl;
    Button backBtn, saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name =  findViewById(R.id.addNewName);
        email =  findViewById(R.id.addNewEmail);
        courses = findViewById(R.id.addNewCourses);
        imgUrl  = findViewById(R.id.addNewImgUrl);
        backBtn =  findViewById(R.id.addNewBackBtn);
        saveBtn =  findViewById(R.id.addNewSaveBtn);

        //============================== Create Operation =================================//
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(); // inserting date to our firebase database.
                clearAll();
                startActivity(new Intent(AddActivity.this, MainActivity.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this, MainActivity.class));
            }
        });
    }
    private void insertData ()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("email",email.getText().toString());
        map.put("courses",courses.getText().toString());
        map.put("imgUrl",imgUrl.getText().toString()); // ""

        FirebaseDatabase.getInstance().getReference().child("students")
                .push().setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Data Inserted Successfully ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,"Data Insertion Failed ", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearAll()
    {
        name.setText("");
        email.setText("");
        courses.setText("");
        imgUrl.setText("");
    }
}