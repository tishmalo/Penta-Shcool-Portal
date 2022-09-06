package com.example.pentaschoolportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Fees extends AppCompatActivity {
    TextView fees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);


        fees=findViewById(R.id.balance);

        viewFees();


    }

    private void viewFees() {

        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("Students");
        Query v=reference.orderByChild("UserId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

          v.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn: snapshot.getChildren()){
                    final String USERNAME=sn.child("Username").getValue().toString();

                    fees.setText(USERNAME);
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Fees");
                   Query z= ref.orderByChild("username").equalTo(USERNAME);



                   z.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {

                           for (DataSnapshot s: snapshot.getChildren()){

                               final String FEES=s.child("balance").getValue().toString();
                               fees.setText(FEES);
                           }


                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }
}
