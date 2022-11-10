package com.example.notehub.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notehub.R;
import com.example.notehub.model.Notes;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouritesRecyclerAdapter extends RecyclerView.Adapter<FavouritesRecyclerAdapter.FavouritesHolder> {
    private ArrayList<QueryDocumentSnapshot> mNotesList;

    public FavouritesRecyclerAdapter(ArrayList notesList) {
        this.mNotesList = notesList;
    }

    @NonNull
    @Override
    public FavouritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_page_card, parent, false);

        return new FavouritesHolder(view);
    }


    @Override
    public void onBindViewHolder(FavouritesHolder holder, int position) {
        Notes notes = mNotesList.get(position).toObject(Notes.class);

        holder.mFileTitle.setText(notes.getTitle());
        holder.mFileDescription.setText(notes.getDescription());
    }

    @Override
    public int getItemCount() {
        if (mNotesList.size() < 3){
            return mNotesList.size();
        }
        else{
            return 3;
        }

    }

    public class FavouritesHolder extends RecyclerView.ViewHolder {
        public TextView mFileTitle;
        public TextView mFileDescription;

        public FavouritesHolder(View itemView){
            super(itemView);

            mFileTitle = (TextView) itemView.findViewById(R.id.file_title);
            mFileDescription = (TextView) itemView.findViewById(R.id.file_description);
        }
    }



}
