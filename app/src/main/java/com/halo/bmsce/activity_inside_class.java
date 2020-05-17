package com.halo.bmsce;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.halo.loginui2.R;

public class activity_inside_class extends AppCompatActivity {
    private Toolbar toolbar ;
    private TabLayout tabLayout ;
    private ViewPager viewPager ;
    private String classname,classcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_class);

        toolbar =(Toolbar)findViewById(R.id.myToolbar) ;
        tabLayout=(TabLayout) findViewById(R.id.tablayout) ;
        viewPager=(ViewPager)findViewById(R.id.myViewpager);

        classname=getIntent().getStringExtra("class_name");
        classcode=getIntent().getStringExtra("class_code");


        pass_classname.new_classname=classname;
        pass_classname.class_id=classcode;


        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setTitle(classname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager){

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new PostFragment(),"POSTS");
        viewPagerAdapter.addFragment(new MaterialFragment(),"MATERIALS");
        viewPager.setAdapter(viewPagerAdapter);
    }

}
