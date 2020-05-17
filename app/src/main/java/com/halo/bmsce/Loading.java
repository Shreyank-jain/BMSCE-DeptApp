package com.halo.bmsce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.halo.loginui2.R;

public class Loading extends AppCompatActivity {
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressDialog=new ProgressDialog(Loading.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        mAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        userid=mAuth.getCurrentUser().getUid();
        final DocumentReference documentReference=fStore.collection("User_Access")
                .document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                UserAccess.permission=documentSnapshot.getString( "permission");
                progressDialog.dismiss();
                startActivity(new Intent(Loading.this,Activity_Homepage.class));
            }
        });
    }
}
