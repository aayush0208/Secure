package com.aayushsingh.secure;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity{
    private Toolbar mToolbar;
    private EditText message, primaryContact, secondaryContact;
    public static final String MyPreferences = "AbhayaPreference";
    public static final String Messages = "MessageKey";
    public static final String pContacts = "pContactKey";

    public static final String sContacts = "sContactKey";

    public static final String securityOnOffs = "securityKey";
    SharedPreferences.Editor editor;

    SharedPreferences sharedPreferences;

    public SettingsActivity(){

    }
    public SettingsActivity (Context context){
        sharedPreferences = context.getSharedPreferences(MyPreferences, 0);
        editor=sharedPreferences.edit();
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mToolbar=(Toolbar)findViewById(R.id.settings_appbar);
        message = (EditText) findViewById(R.id.emergencyMessageTextField);
        primaryContact = (EditText) findViewById(R.id.primaryContactTextField);
        secondaryContact = (EditText) findViewById(R.id.secondaryContactTextField);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MyPreferences, 0);
        if(sharedPreferences.contains(Messages)) {
            message.setText(sharedPreferences.getString(Messages, ""));
        }
        if(sharedPreferences.contains(pContacts)) {
            primaryContact.setText(sharedPreferences.getString(pContacts, ""));
        }
        if(sharedPreferences.contains(sContacts)) {
            secondaryContact.setText(sharedPreferences.getString(sContacts, ""));
        }

    }
    public void settingDetails(View view){
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(MyPreferences, 0);
        editor= sharedPreferences.edit();
        String emergencyMessage = message.getText().toString();
        String primaryCont = primaryContact.getText().toString();

        String sContact = secondaryContact.getText().toString();

        editor.putString(Messages, emergencyMessage);
        editor.putString(pContacts, primaryCont);

        editor.putString(sContacts, sContact);

        editor.commit();
        Toast.makeText(this,"Details have been saved", Toast.LENGTH_SHORT).show();
    }

}
