package com.halo.bmsce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.halo.loginui2.R;

public class Class_View extends AppCompatActivity {
    TextView c_name;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private LinkAdapter adapter;
    private CollectionReference notebookRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);

        c_name = findViewById(R.id.c_name);

        fStore = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra("class_name");
        c_name.setText(userId);

    }
}