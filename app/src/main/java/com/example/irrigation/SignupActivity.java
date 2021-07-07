package com.example.irrigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    ImageView logo;
    TextView slogan1,slogan2;
    TextInputLayout fullname,username,email,phone,password;
    Button signin,signup;

   /* FirebaseDatabase rootNode;
    DatabaseReference reference;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        logo=findViewById(R.id.logo);
        slogan1=findViewById(R.id.slogan1);
        slogan2=findViewById(R.id.slogan2);
        fullname=findViewById(R.id.fullname);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        signin=findViewById(R.id.signin);

       /* rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();*/


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validationFullname() | !validationUsername() | !validationEmail() | !validationPhoneNo() | !validationPassword()){
                    return;
                }

              //  reference = rootNode.getReference("Users");

                String fn=fullname.getEditText().getText().toString();
                String un=username.getEditText().getText().toString();
                String ue=email.getEditText().getText().toString();
                String up=phone.getEditText().getText().toString();
                String ups=password.getEditText().getText().toString();

              //  Helper help = new Helper(fn,un,ue,up,ups);

               // reference.child(up).setValue(help);

                Intent intent = new Intent(SignupActivity.this,VerifyActivity.class);
                intent.putExtra("phone",up);
                intent.putExtra("fullname",fn);
                intent.putExtra("username",un);
                intent.putExtra("email",ue);
                intent.putExtra("password",ups);
                startActivity(intent);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);

            }
        });

    }

    private Boolean validationFullname(){

        String val = fullname.getEditText().getText().toString();

        if(val.isEmpty()){
            fullname.setError("Field can't be empty");
            return false;
        }
        else{
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validationUsername(){

        String val = username.getEditText().getText().toString();
        String whitespace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            username.setError("Field can't be empty");
            return false;
        }
        else if(val.length()>=15){
            username.setError("Username too long");
            return false;
        }
        else if(!val.matches(whitespace)){
            username.setError("Whitespaces are not allowed");
            return false;
        }
        else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validationEmail(){

        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            email.setError("Field can't be empty");
            return false;
        }
        else if(!val.matches(emailPattern)){
            email.setError("Invalid email address");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }

    private Boolean validationPhoneNo(){

        String val = phone.getEditText().getText().toString();

        if(val.isEmpty()){
            phone.setError("Field can't be empty");
            return false;
        }
        else{
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validationPassword(){

        String val = password.getEditText().getText().toString();
        String passwordVal = "^"+
                //"(?=.*[0-9])"+            //atleast 1 digit
                //"(?=.*[a-z])"+            //atleast 1 lowercase letter
                //"(?=.*[A-Z])"+            //atleast 1 uppercase letter
                "(?=.*[a-zA-Z])"+            //any letter
                "(?=.*[@#$%^&+=])"+            //atleast 1 special character
                "(?=\\S+$)"+            //no whitespaces
                ".{4,}"+            //atleast 4 character
                "$";

        if(val.isEmpty()){
            password.setError("Field can't be empty");
            return false;
        }
        else if(!val.matches(passwordVal)){
            password.setError("Password is too weak (use atleast 4 character, 1 special character)");
            return false;
        }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}
