package com.example.pentaschoolportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pentaschoolportal.Adapter.AttendanceAdapter;
import com.example.pentaschoolportal.Model.SubjectModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewAttendance extends AppCompatActivity {

TextView date;
Toolbar tb;
RecyclerView v;
List<SubjectModel> userList;
AttendanceAdapter adapter;
DatePickerDialog.OnDateSetListener setListener;
ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        tb=findViewById(R.id.toolBar);
        v=findViewById(R.id.recyclerview);
        date=findViewById(R.id.date);
        btn=findViewById(R.id.add);
        
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("ATTENDANCE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        userList=new ArrayList<>();
        adapter=new AttendanceAdapter(this, userList);

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        v.setHasFixedSize(true);
        v.setLayoutManager(lm);
        v.setAdapter(adapter);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(ViewAttendance.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();
            }
        });
        
        populateData();
        

    }

    private void populateData() {



                setListener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month= month+1;
                        String fullDate= year+"/"+month+"/"+day;

                        date.setText(fullDate);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                        DatabaseReference ref;
                        ref= FirebaseDatabase.getInstance().getReference("Attendance");
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                final String USERNAME=snapshot.child("Username").getValue().toString();

                                Query v= reference.orderByChild("Username").equalTo(USERNAME);
                                v.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        Query t=ref.orderByChild("date").equalTo(fullDate);

                                        t.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                if (snapshot.exists()){

                                                    userList.clear();
                                                    for (DataSnapshot sn:snapshot.getChildren()){



                                                        SubjectModel sm=sn.getValue(SubjectModel.class);

                                                        userList.add(sm);

                                                    }
                                                    adapter.notifyDataSetChanged();
                                                }else{
                                                    Toast.makeText(ViewAttendance.this, "No Data", Toast.LENGTH_SHORT).show();
                                                }




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

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }
                });


            }
        };



    }
}