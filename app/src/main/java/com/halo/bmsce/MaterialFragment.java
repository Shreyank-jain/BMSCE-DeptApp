package com.halo.bmsce;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.halo.loginui2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialFragment extends Fragment {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    String tablename=pass_classname.getClassname();
    String doc=pass_classname.getClass_id();
    private CollectionReference notebookRef;
    private MaterialAdapter adapter;
    private RecyclerView recyclerView;
    String permission;
    View view;

    public MaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        notebookRef=db.collection(tablename).document(doc).collection("materials");
        view= inflater.inflate(R.layout.fragment_material, container, false);
        recyclerView = view.findViewById(R.id.all_materials);
        setUpRecyclerView();

        permission=UserAccess.getPermission();
        FloatingActionButton layers = view.findViewById(R.id.add_material);
        if(permission.compareTo("faculty")==0){
            layers.show();
            layers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent i=new Intent(getActivity(),Activity_FileUpload.class);
                   i.putExtra("classname",tablename);
                   i.putExtra("classcode",doc);
                   startActivity(i);
                }
            });
        }
        if(permission.compareTo("students")==0){
            layers.hide();
        }
        return view;

    }

    private void setUpRecyclerView(){
        Query query=notebookRef.orderBy("material_date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Material>options=new FirestoreRecyclerOptions.Builder<Material>()
                .setQuery(query, Material.class).build();
        adapter=new MaterialAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MaterialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //Note note=documentSnapshot.toObject(Note.class);
                String uri=documentSnapshot.getString("material_link");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }
}