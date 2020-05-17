package com.halo.bmsce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.halo.loginui2.R;

import java.util.HashMap;
import java.util.Map;

public class Workshop_View extends AppCompatActivity {
    TextView title, description, lastdate,workshop_date;
    Button remainder;
    FirebaseAuth fAuth;
    FloatingActionButton button;
    String user_type;
    FirebaseFirestore fStore;
    String userId,title_rem,date_rem,month,year,month_name,user,workshop_date_rem,type;
    private LinkAdapter adapter;
    private CollectionReference notebookRef;
    private static final String TAG = Activity_Register.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_view);

        title = findViewById(R.id.title_wk);
        description = findViewById(R.id.detail_description);
        lastdate = findViewById(R.id.last);
        workshop_date=findViewById(R.id.workshop_date);
        remainder=(Button)findViewById(R.id.remainder_btn);
        remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRemainder();
            }
        });
        button=findViewById(R.id.add_contest_material);

        user_type=UserAccess.getPermission();
        if(user_type.compareTo("faculty")==0) {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(Workshop_View.this,Add_Workshop_Material.class);
                    i.putExtra("code",userId);
                    startActivity(i);
                }
            });
        }
        if(user_type.compareTo("students")==0){
            button.setVisibility(View.GONE);
        }

        fAuth=FirebaseAuth.getInstance();
        user=fAuth.getUid();
        type=UserAccess.getPermission();
        fStore = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra("uid");
        final DocumentReference documentReference = fStore.collection("workshops")
                .document(userId);
        notebookRef=fStore.collection("workshops").document(userId).collection("links");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                title.setText(documentSnapshot.getString("title"));
                description.setText(documentSnapshot.getString("description"));
                lastdate.setText(documentSnapshot.getString("lastdate"));
                workshop_date.setText(documentSnapshot.getString("workshop_date"));

                title_rem=documentSnapshot.getString("title");
                workshop_date_rem=documentSnapshot.getString("workshop_date");
                date_rem=documentSnapshot.getString("lastdate");
                month=date_rem.substring(date_rem.indexOf("-")+1,date_rem.indexOf("-")+3);
                switch(month){
                    case "01":month_name="JAN";break;
                    case "02":month_name="FEB";break;
                    case "03":month_name="MAR";break;
                    case "04":month_name="APR";break;
                    case "05":month_name="MAY";break;
                    case "06":month_name="JUN";break;
                    case "07":month_name="JUL";break;
                    case "08":month_name="AUG";break;
                    case "09":month_name="SEP";break;
                    case "10":month_name="OCT";break;
                    case "11":month_name="NOV";break;
                    case "12":month_name="DEC";break;

                }
                year=date_rem.substring(date_rem.lastIndexOf("-")+1);
                date_rem=date_rem.substring(0,2);

            }
        });
        setUpRecyclerView();
    }
    private void setUpRecyclerView(){
        Query query=notebookRef.orderBy("a1");
        FirestoreRecyclerOptions<Link> options=new FirestoreRecyclerOptions.Builder<Link>()
                .setQuery(query,Link.class).build();
        adapter=new LinkAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.all_links);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new LinkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String uri=documentSnapshot.getString("link");
                //Note note=documentSnapshot.toObject(Note.class);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));

            }
        });

    }
    private void setRemainder(){

        DocumentReference documentReference=fStore.collection(type).document(user).collection("remainders").document();
        Map<String, Object> user = new HashMap<>();
        user.put("month",month_name);
        user.put("timestamp",workshop_date_rem);
        user.put("year",year);
        user.put("date",date_rem);
        user.put("title",title_rem);
        user.put("type","workshop");

// Add a new document with a generated ID
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"successfully signed up");
                Toast.makeText(Workshop_View.this,"Remainder Set!",Toast.LENGTH_LONG).show();
            }
        });
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