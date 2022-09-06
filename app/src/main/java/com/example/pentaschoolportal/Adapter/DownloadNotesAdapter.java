package com.example.pentaschoolportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentaschoolportal.Model.DowloadNotesModel;
import com.example.pentaschoolportal.R;
import com.example.pentaschoolportal.StartDownload;

import java.util.List;

public class DownloadNotesAdapter extends RecyclerView.Adapter<DownloadNotesAdapter.ViewHolder> {

    Context context;
    List<DowloadNotesModel> userList;

    public DownloadNotesAdapter(Context context, List<DowloadNotesModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public DownloadNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_pdf,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadNotesAdapter.ViewHolder holder, int position) {

        DowloadNotesModel dm=userList.get(position);
        holder.title.setText(dm.getname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, StartDownload.class);
                intent.putExtra("title",dm.getname());
                intent.putExtra("document",dm.getdocument());
                intent.putExtra("code",dm.getcode());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.name);
        }
    }
}
