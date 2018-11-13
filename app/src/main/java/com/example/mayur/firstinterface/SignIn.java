package com.example.mayur.firstinterface;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.Arrays;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    private Button button,button2,button3;
    private ImageView button1;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        button = (Button) findViewById(R.id.ibsignin);
        button2 = (Button) findViewById(R.id.btemail);
        button1 = (ImageView)findViewById(R.id.ivbutton);
        button3 = (Button) findViewById(R.id.login_button);




        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        if (user != null) {
//            finish();
//            startActivity(new Intent(SignIn.this,Second_Screen.class));
//        }


     button1.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(View view) {
             AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignIn.this);
             alertDialogBuilder.setTitle("Exit Application?");
             alertDialogBuilder
                     .setMessage("Click yes to exit!")
                     .setCancelable(false)
                     .setPositiveButton("Yes",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     mAuth.signOut();
                                     FirebaseAuth.getInstance().signOut();
                                     mGoogleSignInClient.signOut();
                                     LoginManager.getInstance().logOut();
                                     //final Intent intent = new Intent(Intent.ACTION_MAIN);
                                     //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                     //intent.addCategory(Intent.CATEGORY_HOME);
                                     //startActivity(intent);
                                     finish();
                                     System.exit(0);
                                 }
                             })

                     .setNegativeButton("No", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {

                             dialog.cancel();
                         }
                     });

             AlertDialog alertDialog = alertDialogBuilder.create();
             alertDialog.show();

            }
       });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SignIn.this,MainActivity.class));
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logInWithReadPermissions(SignIn.this, Arrays.asList(EMAIL));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SignIn.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(SignIn.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {

            startActivity(new Intent(SignIn.this,Second_Screen.class));
            Toast.makeText(this,"You Are Logged In",Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignIn.this,"Login Success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignIn.this,Second_Screen.class));
                            finish();

                        } else {
                            Toast.makeText(SignIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(SignIn.this,Second_Screen.class));
                            finish();
                            Toast.makeText(SignIn.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignIn.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }




    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ibsignin) {
            signIn();
    }
    }
}



