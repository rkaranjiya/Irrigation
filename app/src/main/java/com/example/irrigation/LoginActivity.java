package com.example.irrigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ImageView logo;
    TextView slogan1,slogan2;
    TextInputLayout username,password;
    Button signup,forget,signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        logo = findViewById(R.id.logo);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        forget = findViewById(R.id.forgetPass);
        slogan1 = findViewById(R.id.slogan1);
        slogan2 = findViewById(R.id.slogan2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logo,"logo_img");
                pairs[1] = new Pair<View,String>(slogan1,"logo_text");
                pairs[2] = new Pair<View,String>(slogan2,"slogan2");
                pairs[3] = new Pair<View,String>(username,"username");
                pairs[4] = new Pair<View,String>(password,"password");
                pairs[5] = new Pair<View,String>(signup,"btn1");
                pairs[6] = new Pair<View,String>(signin,"btn2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);

                startActivity(intent,options.toBundle());
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validationUsername() | !validationPassword()){
                    return;
                }
                else{
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    final String entername = username.getEditText().getText().toString().trim();
                    final String enterpass = password.getEditText().getText().toString().trim();

                    Query query = reference.orderByChild("username").equalTo(entername);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                username.setError(null);
                                username.setErrorEnabled(false);

                                for(DataSnapshot child:dataSnapshot.getChildren())
                                {
                                    String passFromDB = child.child("password").getValue().toString();

                                    if(passFromDB.equals(enterpass)){

                                        password.setError(null);
                                        password.setErrorEnabled(false);

                                        String uid = child.getKey().toString();

                                        Intent intent = new Intent(LoginActivity.this,UserprofileActivity.class);
                                        intent.putExtra("uid",uid);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        password.setError("Wrong Password");
                                        password.requestFocus();
                                    }
                                }

                            }
                            else {
                                username.setError("No such user exist");
                                username.requestFocus();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
    }

    private Boolean validationUsername(){

        String val = username.getEditText().getText().toString();

        if(val.isEmpty()){
            username.setError("Field can't be empty");
            return false;
        }
        else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validationPassword(){

        String val = password.getEditText().getText().toString();

        if(val.isEmpty()){
            password.setError("Field can't be empty");
            return false;
        }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

}
