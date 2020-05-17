package com.halo.bmsce;

import android.content.Intent;
import androidx.annotation.NonNull;
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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.halo.loginui2.R;

import java.util.HashMap;
import java.util.Map;


public class Create_Class extends AppCompatActivity  {

    private EditText class_name,class_code;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String uid;
    Button create;
    private String faculty_name;
    private static final String TAG = Activity_Register.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);


        class_name=(EditText) findViewById(R.id.class_name);
        class_code=(EditText)findViewById(R.id.class_code);
        create=(Button)findViewById(R.id.create_class_btn);
        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               StartNewClass();
            }
        });
    }

    public void StartNewClass(){
        final String newclassname=class_name.getText().toString();
        final String newclasscode=class_code.getText().toString();



        if(TextUtils.isEmpty(newclassname)||TextUtils.isEmpty(newclasscode)){
            Toast.makeText(Create_Class.this,"Fill all fields",Toast.LENGTH_LONG).show();
            return;
        }
        if(newclasscode.length()<6 ){
            Toast.makeText(Create_Class.this,"class code must be atleast 6 characters",Toast.LENGTH_LONG).show();
            return;

        }

        else{
            uid = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference=db.collection("faculty").document(uid);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    faculty_name=documentSnapshot.getString("name");

            final String fname=faculty_name;
                DocumentReference documentReference2=db.collection("faculty").document(uid).collection("classes").document();
                Map<String, Object> newclass = new HashMap<>();
                newclass.put("classname",newclassname);
                newclass.put("classcode",newclasscode);
                newclass.put("facultyname",fname);

// Add a new document with a generated ID
                        documentReference2.set(newclass).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "successfully signed up" + uid);
                                DocumentReference documentReference1 = db.collection(newclassname).document(newclasscode);
                                Map<String, Object> newclass1 = new HashMap<>();
                                newclass1.put("facultyname", fname);

// Add a new document with a generated ID
                                documentReference1.set(newclass1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(Create_Class.this, Activity_Classes.class));
                                        Toast.makeText(Create_Class.this, "New Class Created!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Create_Class.this, "Recheck all fields", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });

                    }

                });

        }

    }

}
