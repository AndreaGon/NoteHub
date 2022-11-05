package com.example.notehub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notehub.model.Notes;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Notes> mArrayList;

    public MyAdapter(Context context, ArrayList<Notes> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v1 = LayoutInflater.from(mContext).inflate(R.layout.explore_items, parent, false);

        return new MyViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Notes notes = mArrayList.get(position);

        holder.title.setText(notes.getTitle());
        holder.description.setText(notes.getDescription());
        holder.tags.setText(notes.getTags());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, description, tags;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDesc);
            tags = itemView.findViewById(R.id.textViewTag);
        }

    }
}
