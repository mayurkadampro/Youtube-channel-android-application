package com.example.mayur.firstinterface;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private Button resetButton;
    private TextView loginText;
    private EditText emailText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        resetButton =(Button)findViewById(R.id.btReset);
        loginText = (TextView)findViewById(R.id.tvForgotLogin);
        emailText = (EditText)findViewById(R.id.etEmailReset);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = emailText.getText().toString().trim();
                if(useremail.isEmpty())
                {
                    Toast.makeText(ForgotPassword.this,"Please Enter your register email id",Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotPassword.this,"Password reset email sent!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                            }else{
                                Toast.makeText(ForgotPassword.this,"Error in password reset email not sent!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ForgotPassword.this,MainActivity.class));
            }
        });

    }
}
