package com.example.pentaschoolportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.pentaschoolportal.Adapter.DownloadNotesAdapter;
import com.example.pentaschoolportal.Model.DowloadNotesModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DownloadNotes extends AppCompatActivity {
    
    Toolbar tb;
    RecyclerView rv;
    String CODE;
    
    List<DowloadNotesModel> userList;
    DownloadNotesAdapter adapter;

    DatabaseReference reference;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_notes);
        
        SharedPreferences ph=DownloadNotes.this.getSharedPreferences("code",MODE_PRIVATE);
        CODE=ph.getString("code","");
        

        tb=findViewById(R.id.selectedtoolbar);
        rv=findViewById(R.id.recyclerview);
        
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        userList=new ArrayList<>();
        adapter=new DownloadNotesAdapter(this,userList);
        
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        
        
        populateData();
        


    }

    private void populateData() {

        reference= FirebaseDatabase.getInstance().getReference("notes");
        Query v=reference.orderByChild("code").equalTo(CODE);
        v.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    DowloadNotesModel dm= ds.getValue(DowloadNotesModel.class);
                    userList.add(dm);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}