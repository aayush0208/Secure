package com.aayushsingh.secure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button mSignup,mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mSignup =(Button)findViewById(R.id.bneed_account);
        mLogin =(Button)findViewById(R.id.balready_have);

        //OnclickListener on Signup
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registeractiviy=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(registeractiviy);
            }
        });
        //OnclickListener on Login
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginactivity = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(loginactivity);
            }
        });
    }
}
