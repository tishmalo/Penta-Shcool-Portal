package com.example.pentaschoolportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentaschoolportal.Model.ResultsModel;
import com.example.pentaschoolportal.R;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    Context context;
    List<ResultsModel> userList;

    public ResultsAdapter(Context context, List<ResultsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_results,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder holder, int position) {

        ResultsModel rm=userList.get(position);
        holder.code.setText(rm.getcode());
        holder.grade.setText(rm.getopening());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView code, grade;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            code=itemView.findViewById(R.id.code);
            grade=itemView.findViewById(R.id.grade);


        }
    }
}
