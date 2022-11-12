package com.example.notehub.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notehub.FileViewerActivity;
import com.example.notehub.R;
import com.example.notehub.model.Notes;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExploreRecyclerAdapter extends RecyclerView.Adapter<ExploreRecyclerAdapter.ExploreHolder>  {

    private ArrayList<QueryDocumentSnapshot> mArrayList;
    private Context mContext;

    public ExploreRecyclerAdapter(Context context, ArrayList arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public ExploreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.explore_items, parent, false);

        return new ExploreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreRecyclerAdapter.ExploreHolder holder, int position) {
        Notes notes = mArrayList.get(position).toObject(Notes.class);
        holder.title.setText(notes.getTitle());
        holder.description.setText(notes.getDescription());
        holder.tags.setText(notes.getTags());

        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(mContext, FileViewerActivity.class);
            i.putExtra("key",notes.getTitle());
            mContext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public static class ExploreHolder extends RecyclerView.ViewHolder{

        TextView title, description, tags;

        public ExploreHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDesc);
            tags = itemView.findViewById(R.id.textViewTag);
        }

    }
}
