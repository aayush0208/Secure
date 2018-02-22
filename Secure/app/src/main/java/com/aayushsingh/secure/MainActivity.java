package com.aayushsingh.secure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigation;
    private Button mSMS;
    private  String main_message;
    private String main_primary;
    private String main_secondary;
    private String pBoth;
    SharedPreferences sharedPreferences;
    SmsManager sendsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialization
        mSMS = (Button) findViewById(R.id.b_sendsms);
        sendsms = SmsManager.getDefault();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        mNavigation = (NavigationView) findViewById(R.id.main_nav_view);
        mToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetupNavigationView();


        //OnClickListener on Butoon
        mSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GetDetails();
                sharedPreferences = getApplicationContext().getSharedPreferences(SettingsActivity.MyPreferences, 0);
                String message = sharedPreferences.getString(SettingsActivity.Messages, null);
                String primary_Contact = sharedPreferences.getString(SettingsActivity.pContacts, null);
                String secondary_Contact = sharedPreferences.getString(SettingsActivity.sContacts, null);
                if (sharedPreferences.contains(SettingsActivity.pContacts) || sharedPreferences.contains(SettingsActivity.sContacts)) {
                    if (sharedPreferences.contains(SettingsActivity.Messages)) {
                        if (sharedPreferences.contains(SettingsActivity.pContacts)) {
                            String urlWithPrefix = "";
                            GPSTracker gpsTracker = null;
                            if (gpsTracker.isGPSEnabled) {
                                String stringLatitude = String.valueOf(gpsTracker.latitude);
                                String stringLongitude = String.valueOf(gpsTracker.longitude);
                                urlWithPrefix = " and I am at https://www.google.co.in/maps/@" + stringLatitude + "," + stringLongitude + ",19z";
                            } else {
                                Toast.makeText(MainActivity.this, "Your GPS is OFF", Toast.LENGTH_LONG).show();
                            }


                            if (!message.isEmpty() && !primary_Contact.isEmpty()) {

                                message = message + urlWithPrefix;
                                sendsms.sendTextMessage(primary_Contact, null, message, null, null);
                                Toast.makeText(MainActivity.this, "Message sent:" + primary_Contact, Toast.LENGTH_LONG)
                                        .show();


                                if (!message.isEmpty() && !secondary_Contact.isEmpty()) {
                                    String url = (primary_Contact != null && !primary_Contact.isEmpty()) ? "" : urlWithPrefix;
                                    message = message + url;
                                    sendsms.sendTextMessage(secondary_Contact, null, message, null, null);
                                    Toast.makeText(MainActivity.this, "Message sent:" + secondary_Contact, Toast.LENGTH_LONG)
                                            .show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Enter Primary Contact", Toast.LENGTH_LONG)
                                        .show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Please Enter Secondary Contact", Toast.LENGTH_LONG)
                                    .show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter Emergency Message", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
       /* GPSTracker onconnected = new GPSTracker();
        onconnected.onConnected(@Nullable Bundle bundle);*/
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
           GotoStart();

        }
    }

    private void GotoStart(){
        Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
           return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void SetupNavigationView(){
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               /* switch (item.getItemId()){
                    case R.id.b_log_out:
                       mAuth=FirebaseAuth.getInstance();
                       mAuth.signOut();
                       GotoStart();
                       Toast.makeText(MainActivity.this,"Into the Start Activity",Toast.LENGTH_LONG)
                               .show();

                    case R.id.b_settings:
                        Intent settingsIntent = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(settingsIntent);
                }*/if(item.getItemId()==R.id.bmain_log_out){
                    mAuth=FirebaseAuth.getInstance();
                   mAuth.signOut();
                    GotoStart();
                    Toast.makeText(MainActivity.this,"Into the Start Activity",Toast.LENGTH_LONG)
                            .show();
                }
                if (item.getItemId()==R.id.b_settings){
                    GotoStart();
                }
                    return true;
            }
        });

    }

}
