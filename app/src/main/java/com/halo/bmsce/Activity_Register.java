package com.halo.bmsce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.halo.loginui2.R;

import java.util.HashMap;
import java.util.Map;


public class Activity_Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText email;
    private EditText pswd;
    private EditText confirmpswd;
    private EditText name;
    private EditText usn;
    private EditText phone;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db,db1;
    private String uid;
    ProgressDialog progressDialog;
    private static final String TAG = Activity_Register.class.getSimpleName();

    Spinner sem,sec;
    String semester;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sem= (Spinner) findViewById(R.id.semester);
        sec= (Spinner) findViewById(R.id.sec);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sem,R.layout.dropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sec,R.layout.dropdown);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        sem.setAdapter(adapter);
        sec.setAdapter(adapter1);

        email=(EditText) findViewById(R.id.email);
        pswd=(EditText)findViewById(R.id.pswd);
        confirmpswd=(EditText)findViewById(R.id.confirmpswd);
        name=(EditText)findViewById(R.id.name);
        usn=(EditText)findViewById(R.id.usn);
        phone=(EditText)findViewById(R.id.phone);
        register=(Button)findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        db1=FirebaseFirestore.getInstance();
       progressDialog=new ProgressDialog(Activity_Register.this);
       progressDialog.setTitle("Signing up");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSignUp();
            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    public void StartSignUp(){
        progressDialog.show();
        final String newemail=email.getText().toString();
        final String newpswd=pswd.getText().toString();
        final String newconpswd=confirmpswd.getText().toString();
        final String fname=name.getText().toString();
        final String usnno=usn.getText().toString();
        final String ph=phone.getText().toString();
        final String s=sem.getSelectedItem().toString();
        final String sc=sec.getSelectedItem().toString();

        if(TextUtils.isEmpty(newemail)||TextUtils.isEmpty(newpswd)|| TextUtils.isEmpty(newconpswd) || TextUtils.isEmpty(fname) || TextUtils.isEmpty(usnno) || TextUtils.isEmpty(ph)){
            Toast.makeText(Activity_Register.this,"Fill all fields",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }
        if(newpswd.length()<6 && newpswd.length()<6){
            Toast.makeText(Activity_Register.this,"Password must be atleast 6 characters",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;

        }

        if(ph.length()!=10 || usnno.length()!=10){
            Toast.makeText(Activity_Register.this,"Invalid phone number",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;

        }
        if( usnno.length()!=10 && usnno.contains("1")){
            Toast.makeText(Activity_Register.this,"Invalid USN",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;

        }
        if(newpswd.equals(newconpswd) ){
            mAuth.createUserWithEmailAndPassword(newemail, newpswd).addOnCompleteListener(Activity_Register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        uid = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference1 = db1.collection("User_Access").document(uid);
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put("permission", "students");
                        documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) { DocumentReference documentReference = db.collection("students").document(uid);
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", fname);
                            user.put("usn", usnno);
                            user.put("phone", ph);
                            user.put("email", newemail);
                            user.put("password", newpswd);
                            user.put("sem", s);
                            user.put("sec", sc);
                            user.put("permission", "students");

// Add a new document with a generated ID
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>(){
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "successfully signed up" + uid);
                                        progressDialog.dismiss();
                                        Toast.makeText(Activity_Register.this, "Successfully Signed up! ", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Activity_Register.this, "Failed to set permission ", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }


                        });
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(Activity_Register.this, "Recheck Credentials", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

            });
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(Activity_Register.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}