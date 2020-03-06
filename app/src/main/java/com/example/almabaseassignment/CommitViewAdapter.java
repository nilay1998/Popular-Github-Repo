package com.example.almabaseassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almabaseassignment.Models.Commit;
import com.example.almabaseassignment.Models.Repo;

import java.util.ArrayList;

public class CommitViewAdapter extends RecyclerView.Adapter<CommitViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Commit> mCommit=new ArrayList<>();
    public CommitViewAdapter(Context context, ArrayList<Commit> commit) {
        this.mContext = context;
        mCommit=commit;
    }

    @NonNull
    @Override
    public CommitViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        CommitViewAdapter.ViewHolder holder=new CommitViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewAdapter.ViewHolder holder, int position) {
        holder.repo_name.setText(mCommit.get(position).getLogin());
        holder.fork.setText(mCommit.get(position).getContributions());
        holder.sno.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return mCommit.size();
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
            sno=itemView.findViewById(R.id.sno);
            repo_name=itemView.findViewById(R.id.repo_name);
            fork=itemView.findViewById(R.id.fork);
        }
    }
}
