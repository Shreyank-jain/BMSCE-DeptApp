package com.halo.bmsce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.halo.loginui2.R;

public class Activity_Events extends AppCompatActivity {

    private FirebaseAuth fAuth=FirebaseAuth.getInstance();
    String user=fAuth.getCurrentUser().getUid();
    String type=UserAccess.getPermission();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference notebookRef=db.collection(type).document(user).collection("remainders");
    private EventAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        setUpRecyclerView();
    }
    private void setUpRecyclerView(){
        Query query=notebookRef.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Event>options=new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query,Event.class).build();
        adapter=new EventAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.all_liste);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }
}