package com.halo.bmsce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.halo.loginui2.R;


public class Activity_Profile extends AppCompatActivity {

     TextView  name,usn,ph,email,sem;
     FirebaseAuth fAuth;
     FirebaseFirestore fStore;
     String userId,semsec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.usernametitle);
        usn=findViewById(R.id.usn_profile);
        email=findViewById(R.id.email_profile);
        sem=findViewById(R.id.class_profile);
        ph=findViewById(R.id.phone_profile);


        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userId=fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference=fStore.collection("students")
                .document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString( "name"));
                usn.setText(documentSnapshot.getString("usn"));
                ph.setText(documentSnapshot.getString("phone"));
                email.setText(documentSnapshot.getString("email"));
                semsec=documentSnapshot.getString("sem")+" "+documentSnapshot.getString("sec");
                sem.setText(semsec);

            }
        });


    }
}
