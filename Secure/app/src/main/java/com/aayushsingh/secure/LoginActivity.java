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

public class LoginActivity extends AppCompatActivity {
    private Button blogin,bpassword;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initialisation
        blogin=(Button)findViewById(R.id.btn_login);
        bpassword=(Button)findViewById(R.id.bforget_password);
        mEmail=(TextInputLayout)findViewById(R.id.input_login_email);
        mPassword=(TextInputLayout)findViewById(R.id.input_login_password);
        mToolbar=(Toolbar)findViewById(R.id.login_app_bar);
        //progressDialog
        mProgress=new ProgressDialog(LoginActivity.this);
        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //OnclickListener on forgot password
        bpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent password_intent =new Intent(LoginActivity.this,PasswordResetActivity.class);
                startActivity(password_intent);//startActivity(new Intent(LoginActivity.this,PasswordResetActivity.class));
            }
        });

        //firebase
        mAuth=FirebaseAuth.getInstance();

        //OnClick Listener on Login
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getEditText().getText().toString();
                String password=mPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Please enter email address",Toast.LENGTH_LONG)
                            .show();
                }else  if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Pease enter password",Toast.LENGTH_LONG)
                            .show();
                }
                mProgress.setTitle("Logging In");
                mProgress.setMessage("Please wait while we check your credentials!");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();
                sign_in(email,password);
            }
        });
    }
        private void sign_in(final String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mProgress.dismiss();
                    Intent log_intent =new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(log_intent);
                    finish();
                }else{
                    mProgress.hide();
                    Toast.makeText(LoginActivity.this,"Authentication Failed!",Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        }
}
