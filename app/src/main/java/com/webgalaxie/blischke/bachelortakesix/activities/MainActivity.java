package com.webgalaxie.blischke.bachelortakesix.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.activities.LoginScreen;
import com.webgalaxie.blischke.bachelortakesix.fragments.AddNewExposeFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.MyAccountFragment;
import com.webgalaxie.blischke.bachelortakesix.fragments.ShowAllExposeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private TextView emailnav, usernameNav;
    private ImageView profilPhotoNav;
    private String userid, imageUrl, username, email;


    //Dashboard Components
    private CardView all_expose_card, add_expose_card, my_account_card,logout_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" ");


        all_expose_card = findViewById(R.id.all_expose_card);
        add_expose_card = findViewById(R.id.add_expose_card);
        my_account_card = findViewById(R.id.my_account_card);
        logout_dashboard= findViewById(R.id.logout_card);


        emailnav = findViewById(R.id.emailnav);
        usernameNav = findViewById(R.id.usernameNav);
        profilPhotoNav = findViewById(R.id.profilPhotoNav);


        //Set up the Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        //get id for current user
        userid = user.getUid();

        // setup the navigation header with user information

        //emailnav.setText(user.getEmail());
        //usernameNav.setText(databaseReference.child(userid).child("email").toString());




        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                    finish();
                }
            }
        };

        //Setup the Redirects for the Cardviews
        all_expose_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllExposeFragment showAllExposeFragment = new ShowAllExposeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, showAllExposeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        add_expose_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewExposeFragment addNewExposeFragment = new AddNewExposeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, addNewExposeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        my_account_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAccountFragment myAccountFragmentFragment = new MyAccountFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, myAccountFragmentFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        logout_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log user out
                auth.signOut();
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // get the id of the Menu Item
        int id = item.getItemId();

        if (id == R.id.nav_all_expose) {
            //Replace content_frame with the ShowAllExposeFragment

            ShowAllExposeFragment showAllExposeFragment = new ShowAllExposeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, showAllExposeFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_add_new_expose) {
            //Replace content_frame with the AddNewExposeFragment

            AddNewExposeFragment addNewExposeFragment = new AddNewExposeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, addNewExposeFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_my_account) {
            //Replace content_frame with the MyAccountFragment
            MyAccountFragment myAccountFragmentFragment = new MyAccountFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, myAccountFragmentFragment)
                    .addToBackStack(null)
                    .commit();
        } else if(id == R.id.nav_logout){
            //Log user out
            auth.signOut();
        }

        // Close the NavigationDrawer after clicking MenuItem
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
