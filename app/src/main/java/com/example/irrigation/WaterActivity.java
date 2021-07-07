package com.example.irrigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaterActivity extends AppCompatActivity {

    ImageView profileimg;
    TextView profilename,profileusername,humidity,moisture,temp;
    Button back;

    FirebaseDatabase root;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        profileimg = findViewById(R.id.profileimg);
        profilename = findViewById(R.id.ufullname);
        profileusername = findViewById(R.id.uname);
        humidity = findViewById(R.id.humidity);
        moisture = findViewById(R.id.moisture);
        temp = findViewById(R.id.temp);
        back = findViewById(R.id.back);

        root = FirebaseDatabase.getInstance();
        reference = root.getReference("Sensor");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               Value value = dataSnapshot.getValue(Value.class);

               humidity.setText(value.Humidity);
               temp.setText(value.Temperture);
               moisture.setText(value.Moisture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WaterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WaterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
