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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {
    private Button breset;
    private TextInputLayout mEmail;
    private TextView mMessage;
    private Toolbar mToolbar;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        breset=(Button)findViewById(R.id.b_reset);
        mMessage=(TextView)findViewById(R.id.txt_message);
        mEmail=(TextInputLayout)findViewById(R.id.input_reset_email);
        mToolbar=(Toolbar)findViewById(R.id.reset_password_app_bar);
        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Password Reset");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Firebase
        mAuth=FirebaseAuth.getInstance();
        //progress Dialog
        mProgress=new ProgressDialog(PasswordResetActivity.this);

        breset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getEditText().getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(PasswordResetActivity.this,"Enter email address",Toast.LENGTH_LONG)
                            .show();
                }
                mProgress.setTitle("Sending Reset Password");
                mProgress.setMessage("We are sending the reset password link");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();
                reset_password(email);
            }
        });
    }
    private void reset_password(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mProgress.dismiss();
                    Toast.makeText(PasswordResetActivity.this,"We have sent the password reset link",Toast.LENGTH_LONG)
                            .show();
                    startActivity(new Intent(PasswordResetActivity.this,LoginActivity.class));

                }else {
                   mProgress.hide();
                    Toast.makeText(PasswordResetActivity.this,"Failed to send the password reset link",Toast.LENGTH_LONG)
                            .show();
                            return;
                }
            }
        });
    }
}
