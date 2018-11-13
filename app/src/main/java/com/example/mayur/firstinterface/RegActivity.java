package com.example.mayur.firstinterface;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.v4.content.ContextCompat.startActivity;

public class RegActivity extends AppCompatActivity {

    private EditText UserName,PassWord,Email;
    private Button Register;
    private TextView Login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg);
        getSupportActionBar().hide();
        variables();

        firebaseAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validtate() == true)
                {
                    //String Username = UserName.getText().toString().trim();
                    String UserEmail = Email.getText().toString().trim();
                    String Userpass = PassWord.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(UserEmail,Userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                senEmailVerification();

                            }else
                                {
                                    Toast.makeText(RegActivity.this,"Registration Unsuccessfull!! Check Your Internet Connection.",Toast.LENGTH_LONG).show();

                                }
                        }
                    });
                }

            }
        });

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegActivity.this,MainActivity.class));
            }
        });


    }

    private void variables()
    {
        UserName = (EditText)findViewById(R.id.etUser);
        PassWord = (EditText)findViewById(R.id.etPass);
        Email = (EditText)findViewById(R.id.etEmail);
        Register = (Button)findViewById(R.id.btReg);
        Login = (TextView)findViewById(R.id.tvlogin);
    }

    private boolean validtate()
    {
        boolean result = false;

        String name = UserName.getText().toString();
        String email = Email.getText().toString();
        String pass = PassWord.getText().toString();

        if(name.isEmpty() || email.isEmpty() || pass.isEmpty())
        {
            Toast.makeText(RegActivity.this,"Please Fill The Details",Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;
    }





    private void senEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(RegActivity.this,"Registration Successfull!! Verification email has been sent",Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(RegActivity.this,"Verification email has not been sent",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }
}
