package com.example.pentaschoolportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentaschoolportal.DownloadNotes;
import com.example.pentaschoolportal.Model.UnitsModel;
import com.example.pentaschoolportal.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    Context context;
    List<UnitsModel> userList;

    public NotesAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_classes,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {

        UnitsModel um=userList.get(position);
        holder.CODE.setText(um.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh=context.getSharedPreferences("code",Context.MODE_PRIVATE);

                SharedPreferences.Editor edit=sh.edit();

                edit.putString("code",um.getcode());

                edit.apply();

                Intent intent=new Intent(context, DownloadNotes.class);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView CODE;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CODE=itemView.findViewById(R.id.code);
        }
    }
}
