package com.halo.bmsce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.halo.loginui2.R;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    private EditText emailfield;
    private EditText password;
    private Button loginbtn;
    ProgressDialog progressDialog;
    Button forgot;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    RelativeLayout rellay1, rellay2;
    public Button signup;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 3000); //3000 is the timeout for the splash
        signup=(Button) findViewById(R.id.signupbtn);
        signup.setOnClickListener(this);
        progressDialog=new ProgressDialog(Activity_Login.this);
        progressDialog.setTitle("Signing In...");


        mAuth=FirebaseAuth.getInstance();
        emailfield=(EditText) findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        loginbtn=(Button)findViewById(R.id.login);
        forgot=findViewById(R.id.forgotbtn);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this,Reset_Password.class));
            }
        });

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    progressDialog.dismiss();
                    startActivity(new Intent(Activity_Login.this,Loading.class));
                }
            }
        };
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener((mAuthListener));
    }
    private void startSignIn(){
        progressDialog.show();
        String email=emailfield.getText().toString();
        String pswd=password.getText().toString();
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pswd)){
            Toast.makeText(Activity_Login.this,"Fill all fields",Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(Activity_Login.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Activity_Login.this, Activity_Register.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){


    }

}
