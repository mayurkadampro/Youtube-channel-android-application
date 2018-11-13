package com.example.mayur.firstinterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SizeF;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText userText;
    private EditText passtext;
    private Button button;
    private TextView Signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        userText = (EditText)findViewById(R.id.etUser);
        passtext = (EditText)findViewById(R.id.etPass);
        button = (Button)findViewById(R.id.btSub);
        Signup = (TextView)findViewById(R.id.tvSignup);
        forgotPassword = (TextView) findViewById(R.id.tvForgorPassword);
        back = (ImageView)findViewById(R.id.ivback);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            finish();
            startActivity(new Intent(MainActivity.this,Second_Screen.class));
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = userText.getText().toString();
                String Pass = passtext.getText().toString();

                if(Email.isEmpty() || Pass.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please Fill The Details",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    Validate(userText.getText().toString(), passtext.getText().toString());
                }
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(MainActivity.this,RegActivity.class));
                startActivity(new Intent(MainActivity.this,RegActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainActivity.this, SignIn.class));
            }
        });


    }

    public void Validate(String Username,String Password)
    {
        firebaseAuth.signInWithEmailAndPassword(Username,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    chekEmailVerification();
                }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    private void chekEmailVerification()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser.isEmailVerified()){
            finish();
            Toast.makeText(MainActivity.this,"Login Sucess",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,Second_Screen.class));
        }else{
            Toast.makeText(MainActivity.this,"Please Verify Email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }


}
