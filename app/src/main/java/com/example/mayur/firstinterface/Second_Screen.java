package com.example.mayur.firstinterface;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Second_Screen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__screen);
        firebaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);


        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displaySelectedScreen(R.id.nav_home);

    }




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser==null)
        {
            startActivity(new Intent(this,SignIn.class));
            Toast.makeText(this,"You Are LogOut",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void SendTOFinal(View view) {

        startActivity(new Intent(this, Final_Activity.class));

    }

    public void Logout(){
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(Second_Screen.this,SignIn.class));
        Toast.makeText(getBaseContext(), "Logged Out", Toast.LENGTH_LONG).show(); //if u want to show some text
        finish();

}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.Logout:{
                Logout();
                break;
            }
        }
        if (mToggle.onOptionsItemSelected(item)) {
            return true;

        }
        return true;
    }


    private void displaySelectedScreen(int id)
    {
        Fragment fragment = null;
        switch(id)
        {
            case R.id.nav_facebook:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mightyghosthack/")));
                break;

            case R.id.nav_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/mayurkadampro")));
                break;

            case R.id.nav_insta:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mighty_ghost_hack/")));
                break;

            case R.id.nav_reddit:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com/user/mighty_ghost_hack")));
                break;

            case R.id.nav_twitter:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/HackMighty")));
                break;

            case R.id.nav_yt:
                fragment = new YtPlaylist();
                break;

            case R.id.nav_home:
                fragment = new HomeFragment();
                break;

            case R.id.nav_nonyt:
                fragment = new NonYtPlaylist();
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }
}

