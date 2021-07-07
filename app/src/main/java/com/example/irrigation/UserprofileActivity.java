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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserprofileActivity extends AppCompatActivity {

    ImageView profileimg;
    TextView profilename,profileusername;
    TextInputLayout fullname,username,email,phone;
    CardView water,temp;
    Button update,back;

    FirebaseDatabase root;
    DatabaseReference reference;

    String uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        profileimg = findViewById(R.id.profileimg);
        profilename = findViewById(R.id.ufullname);
        profileusername = findViewById(R.id.uname);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        water = findViewById(R.id.water);
        temp = findViewById(R.id.temp);
        update = findViewById(R.id.updatebtn);
        back = findViewById(R.id.backbtn);

        String id = getIntent().getStringExtra("uid");

        root = FirebaseDatabase.getInstance();
        reference = root.getReference("Users").child(id);

        Helper help = new Helper();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Helper helper = dataSnapshot.getValue(Helper.class);
                profilename.setText(helper.fullname);
                profileusername.setText(helper.username);
                fullname.getEditText().setText(helper.fullname);
                username.getEditText().setText(helper.username);
                email.getEditText().setText(helper.email);
                phone.getEditText().setText(helper.phoneNo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child("fullname").setValue(fullname.getEditText().getText().toString());
                reference.child("username").setValue(username.getEditText().getText().toString());
                reference.child("email").setValue(email.getEditText().getText().toString());
                reference.child("phoneNo").setValue(phone.getEditText().getText().toString());
                Toast.makeText(getApplicationContext(),"Data has been updated",Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserprofileActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });


        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),WaterActivity.class);
                startActivity(intent);

            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(getApplicationContext(),Data.class);
                startActivity(intent);
                finish();
                */
            }
        });

    }
}
