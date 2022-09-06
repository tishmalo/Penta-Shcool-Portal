package com.example.pentaschoolportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pentaschoolportal.Adapter.SubjectAdapter;
import com.example.pentaschoolportal.Model.SubjectModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClassSubject extends AppCompatActivity {

    TextView Email;
    FloatingActionButton proceed;
    Spinner spinner,subject;
    String USERNAME;
    ImageButton btn;



    List<SubjectModel> userList;
    SubjectAdapter adapter;

    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_subject);

        spinner=findViewById(R.id.courses);
        proceed=findViewById(R.id.proceed);
        Email=findViewById(R.id.LecName);
        subject=findViewById(R.id.subject);
        btn=findViewById(R.id.add);
        rv=findViewById(R.id.selectedrecycler);
        userList=new ArrayList<>();
        adapter=new SubjectAdapter(this, userList);


        Email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


        addClass();
        viewSubject();
        swipeToDelete();

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClassSubject.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void swipeToDelete() {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                String deletedItems=userList.get(viewHolder.getAdapterPosition()).getcode();

                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Subjects");


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            for (DataSnapshot ds:snapshot.getChildren()){

                                String CODE= ds.child("code").getValue().toString();
                                String USER=ds.child("userid").getValue().toString();

                                if(CODE.equals(deletedItems)&&USER.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                                    userList.remove(viewHolder.getAdapterPosition());
                                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                    ds.getRef().removeValue();

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        }).attachToRecyclerView(rv);

    }

    private void viewSubject() {

        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("Subjects");
        Query f=ref.orderByChild("userid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        f.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){

                    SubjectModel m=ds.getValue(SubjectModel.class);
                    userList.add(m);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addClass() {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String SPINNER=spinner.getSelectedItem().toString();
                final String SPINNER2=subject.getSelectedItem().toString();


             DatabaseReference ref;
             ref=FirebaseDatabase.getInstance().getReference("Students").child(FirebaseAuth.getInstance()
                     .getCurrentUser().getUid());
             ref.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                     USERNAME=snapshot.child("Username").getValue().toString();
                     Log.d("Reserve",USERNAME);


                     DatabaseReference ref1;
                     ref1= FirebaseDatabase.getInstance().getReference("Subjects");

                     if (SPINNER.equals("CLASS YEAR")){
                         Toast.makeText(ClassSubject.this, "Enter Class", Toast.LENGTH_SHORT).show();
                     }if (SPINNER2.equals("SUBJECT")){
                         Toast.makeText(ClassSubject.this, "Enter SUBJECT", Toast.LENGTH_SHORT).show();
                     }else{





                         final String CODE=SPINNER + " " + SPINNER2;
                         final String YEAR=SPINNER;
                         final String USERID= FirebaseAuth.getInstance().getCurrentUser().getUid();

                         SubjectModel sm=new SubjectModel(CODE,USERNAME,USERID,YEAR);

                         ref1.push().setValue(sm);

                        HashMap map=new HashMap();
                        map.put("year",SPINNER);
                        map.put("username",USERNAME);
                        DatabaseReference v=FirebaseDatabase.getInstance().getReference("Classes");
                        v.child(SPINNER).child(USERNAME).setValue(map);

                         Toast.makeText(ClassSubject.this, "Upload Successful", Toast.LENGTH_SHORT).show();





                     }




                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });

            }
        });

    }
}