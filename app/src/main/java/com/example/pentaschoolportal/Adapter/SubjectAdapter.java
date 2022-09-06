package com.example.pentaschoolportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentaschoolportal.Model.SubjectModel;
import com.example.pentaschoolportal.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    Context context;
    List<SubjectModel> userList;

    public SubjectAdapter(Context context, List<SubjectModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.show_pdf,parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, int position) {

        SubjectModel sm=userList.get(position);
        holder.CODE.setText(sm.getcode());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView CODE;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CODE=itemView.findViewById(R.id.name);

        }
    }
}
