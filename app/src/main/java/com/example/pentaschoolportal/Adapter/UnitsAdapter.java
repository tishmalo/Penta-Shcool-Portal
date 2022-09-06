package com.example.pentaschoolportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentaschoolportal.Message;
import com.example.pentaschoolportal.Model.UnitsModel;
import com.example.pentaschoolportal.R;

import java.util.List;

public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.ViewHolder> {

    Context context;
    List<UnitsModel> userList;

    public UnitsAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UnitsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_classes,parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitsAdapter.ViewHolder holder, int position) {

        UnitsModel um=userList.get(position);
        holder.code.setText(um.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh= context.getSharedPreferences("code",Context.MODE_PRIVATE);

                SharedPreferences.Editor edit=sh.edit();

                edit.putString("code",um.getcode());

                edit.apply();

                Intent intent=new Intent(context, Message.class);
                context.startActivity(intent);



            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView code;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            code=itemView.findViewById(R.id.code);
        }
    }
}
