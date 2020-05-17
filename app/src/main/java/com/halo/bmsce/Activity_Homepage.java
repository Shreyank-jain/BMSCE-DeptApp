package com.halo.bmsce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.internal.zzj;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.halo.loginui2.R;

import java.util.Objects;

public class Activity_Homepage extends AppCompatActivity implements View.OnClickListener{
    private CardView contest,workshop,logout,profile,classes,events;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    String userid;


    private String permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        logout=(CardView)findViewById(R.id.Logout);
        workshop=(CardView) findViewById(R.id.workshop);
        contest=(CardView)findViewById(R.id.contests);
        profile=(CardView)findViewById(R.id.profile);
        classes=(CardView)findViewById(R.id.classes);
        events=(CardView)findViewById(R.id.events);

        logout.setOnClickListener(this);
        workshop.setOnClickListener(this);
        contest.setOnClickListener(this);
        profile.setOnClickListener(this);
        classes.setOnClickListener(this);
        events.setOnClickListener(this);


        permission=UserAccess.getPermission();
        if(permission.compareTo("faculty")==0){
            profile.setVisibility(View.GONE);
        }
        if(permission.compareTo("students")==0){
            profile.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Logout:{
                mAuth.getInstance().signOut();
                Toast.makeText(Activity_Homepage.this, "Signed out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Activity_Homepage.this,Activity_Login.class));
                break;
            }
            case R.id.workshop:
            {
                startActivity(new Intent(Activity_Homepage.this,Activity_Workshop.class));
                break;
            }
            case R.id.contests:
            {
                startActivity(new Intent(Activity_Homepage.this,Activity_Contest.class));
                break;
            }
            case R.id.profile:
            {
                startActivity(new Intent(Activity_Homepage.this,Activity_Profile.class));
                break;
            }
            case R.id.classes:
            {
                startActivity(new Intent(Activity_Homepage.this,Activity_Classes.class));
                break;
            }
            case R.id.events:
            {
                startActivity(new Intent(Activity_Homepage.this,Activity_Events.class));
                break;
            }
        }


    }
}
