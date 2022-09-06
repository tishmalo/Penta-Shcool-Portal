package com.example.pentaschoolportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pentaschoolportal.Adapter.ResultsAdapter;
import com.example.pentaschoolportal.Model.ResultsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ResultsAdapter adapter;
    List<ResultsModel> userList;

    TextView SUmTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        toolbar=findViewById(R.id.toolBar);
        recyclerView=findViewById(R.id.recyclerview);
        SUmTotal=findViewById(R.id.total);

        userList=new ArrayList<>();
        adapter=new ResultsAdapter(this, userList);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        populateData();






    }

    private void populateData() {

        DatabaseReference ref;

        ref=FirebaseDatabase.getInstance().getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String USERNAME=snapshot.child("Username").getValue().toString();

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Results").child(USERNAME);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        userList.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            ResultsModel rm=ds.getValue(ResultsModel.class);

                            final String RESULTS=ds.child("opening").getValue().toString();

                            Integer Res=Integer.valueOf(RESULTS);
                            Integer SUm=0;

                            SUm+=Res;




                            userList.add(rm);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}