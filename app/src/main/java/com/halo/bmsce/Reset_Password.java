package com.halo.bmsce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.halo.loginui2.R;

public class Reset_Password extends AppCompatActivity {

    EditText mail;
    Button reset;
    String mailid;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        mail =(EditText)findViewById(R.id.emailid);
        progressDialog=new ProgressDialog(Reset_Password.this);
        progressDialog.setTitle("Sending Reset link");
        progressDialog.show();
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailid = mail.getText().toString();
                if (!TextUtils.isEmpty(mailid)) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(mailid)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Reset_Password.this, "Password Reset successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Reset_Password.this, Activity_Login.class));
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(Reset_Password.this, "Not Authentic Email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

        });
    }
}
