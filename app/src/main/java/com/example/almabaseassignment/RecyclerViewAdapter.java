package com.example.almabaseassignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almabaseassignment.Models.Repo;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{

    private Context mContext;
    private ArrayList<Repo> mRepo=new ArrayList<>();

    public RecyclerViewAdapter(Context context,ArrayList<Repo> docs) {
        this.mContext = context;
        mRepo=docs;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.repo_name.setText(mRepo.get(position).getName());
        holder.fork.setText(mRepo.get(position).getForks_count());
        holder.sno.setText(String.valueOf(position+1));

        holder.repo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,Committee.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("link",mRepo.get(position).getContributors_url());
                intent.putExtra("name",mRepo.get(position).getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRepo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout repo_layout;
        TextView repo_name;
        TextView fork;
        TextView sno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            repo_layout=itemView.findViewById(R.id.repo_layout);
            repo_name=itemView.findViewById(R.id.repo_name);
            fork=itemView.findViewById(R.id.fork);
            sno=itemView.findViewById(R.id.sno);
        }
    }
}
