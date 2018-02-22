package com.aayushsingh.secure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {
    private Button mRegister;
    private TextInputLayout mName,mEmail,mPassword;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRefrence;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Instantiation
        mToolbar=(Toolbar)findViewById(R.id.signup_app_bar);
        mName=(TextInputLayout)findViewById(R.id.input_register_name);
        mEmail=(TextInputLayout)findViewById(R.id.input_register_email);
        mPassword=(TextInputLayout)findViewById(R.id.input_register_password);
        mRegister=(Button)findViewById(R.id.bregister);
        mProgress =new ProgressDialog(RegisterActivity.this);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //firebse auth
        mAuth= FirebaseAuth.getInstance();
        //OnclickListener on Create Account
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mName.getEditText().getText().toString();
                String email=mEmail.getEditText().getText().toString();
                String password=mPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterActivity.this,"Please enter name",Toast.LENGTH_LONG)
                            .show();
                    return;
                } else if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this,"Please enter email address",Toast.LENGTH_LONG)
                            .show();
                    return;
                } else if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"Please enter password",Toast.LENGTH_LONG)
                            .show();
                    return;
                }  /*else if(password.length()<6){
                    Toast.makeText(RegisterActivity.this,"Too short password",Toast.LENGTH_LONG)
                            .show();
                }*/
                //progrssDialog
                mProgress.setTitle("Creating Account");
                mProgress.setMessage("Please wait while we create your account!");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();

                register_user(name,email,password);

            }
        });}
        //Method to Get user name,email and password
    /*Creating user database saving user's email id and password*/
        private void register_user(final String name,String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mProgress.dismiss();
                    Intent Reg_Intent =new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(Reg_Intent);
                    finish();
                    //Exceptions during registeration
                    /* 1.If user already exist
                    2. Improper email id
                    3. weak password*/
                }else if (task.getException() instanceof FirebaseAuthUserCollisionException)
                {
                    mProgress.hide();
                    Toast.makeText(RegisterActivity.this,"User with this id already exist",Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                    else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                    mProgress.hide();
                    Toast.makeText(RegisterActivity.this,"Password is not Strong enough",Toast.LENGTH_LONG)
                            .show();
                    return;

                }else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    mProgress.hide();
                    Toast.makeText(RegisterActivity.this,"Please enter proper email address",Toast.LENGTH_LONG)
                            .show();
                    return;

                }else {
                    mProgress.hide();
                    Toast.makeText(RegisterActivity.this,"Can't Sign Up",Toast.LENGTH_LONG)
                            .show();
                    return;
                }

            }
        });

    }
    }

