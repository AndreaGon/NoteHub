package com.example.notehub.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notehub.FileViewerActivity;
import com.example.notehub.R;
import com.example.notehub.controllers.ExploreController;
import com.example.notehub.databinding.FragmentExploreBinding;
import com.example.notehub.fragment.HomeFragment;
import com.example.notehub.model.Notes;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExploreRecyclerAdapter extends RecyclerView.Adapter<ExploreRecyclerAdapter.ExploreHolder>  {

    private ArrayList<QueryDocumentSnapshot> mArrayList;
    private ArrayList<Notes> mNotesArrayList = new ArrayList<Notes>();
    private Context mContext;

    public ExploreRecyclerAdapter(Context context, ArrayList<Notes> arrayList) {
        mContext = context;
        //mArrayList = arrayList;
        mNotesArrayList = arrayList;
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

        //Notes notes = mArrayList.get(position).toObject(Notes.class);
        Notes notes = mNotesArrayList.get(position);

        holder.title.setText(notes.getTitle());
        holder.description.setText(notes.getDescription());
        holder.tags.setText(notes.getTags());
        holder.url.setText(notes.getUrl());


        //WHEN CLICKED RECYCLERVIEW
        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(mContext, FileViewerActivity.class);
            i.putExtra("url_key",notes.getUrl());
            i.putExtra("title_key",notes.getTitle());
            i.putExtra("description",notes.getDescription());
            i.putExtra("username",notes.getUserName());
            i.putExtra("file_id", notes.getFile_id());
            i.putExtra("is_fav", false);
            mContext.startActivity(i);
        });
        //WHEN CLICKED RECYCLERVIEW
    }

    @Override
    public int getItemCount() {
        //return mArrayList.size();
        return mNotesArrayList.size();
    }

    public void exploreSearchFilter(ArrayList<Notes> filtered_list){
        mNotesArrayList = filtered_list;
        notifyDataSetChanged();
    }

    public static class ExploreHolder extends RecyclerView.ViewHolder{

        TextView title, description, tags, url;

        public ExploreHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDesc);
            tags = itemView.findViewById(R.id.textViewTag);
            url = itemView.findViewById(R.id.url_id);
        }

    }
}
