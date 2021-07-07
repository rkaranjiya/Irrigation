package com.example.irrigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    EditText otp;
    Button verify;
    ProgressBar progressBar;

    FirebaseDatabase root;
    DatabaseReference reference;

    FirebaseUser user;

    String verificationcodebysys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        otp = findViewById(R.id.OTP);
        verify = findViewById(R.id.verifybtn);
        progressBar = findViewById(R.id.prograssbar);

        root = FirebaseDatabase.getInstance();
        reference = root.getReference();

        progressBar.setVisibility(View.GONE);

        String phoneNo = getIntent().getStringExtra("phone");

        sendOTP(phoneNo);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otp.getText().toString();

                if(code.isEmpty() || code.length()<6){

                    otp.setError("Wrong OTP...");
                    otp.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                verifycode(code);
            }
        });

    }

    private void sendOTP(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationcodebysys = s;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if(code!=null){
                progressBar.setVisibility(View.VISIBLE);
                verifycode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };

    private void verifycode(String codebyuser){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationcodebysys,codebyuser);
        signinbycredential(credential);

    }

    private void signinbycredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            reference = root.getReference("Users");

                            user = FirebaseAuth.getInstance().getCurrentUser();

                            String uid = user.getUid();

                            String phoneNo = getIntent().getStringExtra("phone");
                            String fullname = getIntent().getStringExtra("fullname");
                            String username = getIntent().getStringExtra("username");
                            String email = getIntent().getStringExtra("email");
                            String password = getIntent().getStringExtra("password");

                            Helper helper = new Helper(fullname,username,email,phoneNo,password);

                            reference.child(uid).setValue(helper);

                            Intent intent = new Intent(getApplicationContext(),UserprofileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("uid",uid);
                            intent.putExtra("name",fullname);
                            intent.putExtra("username",username);
                            intent.putExtra("phone",phoneNo);
                            intent.putExtra("email",email);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
