package com.bogomolov.alexander.contentprovidertest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by admin on 26.10.2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {
    ArrayList<Note> dataset;

    Context context;

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.note_holder, parent, false);
        return new NoteHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        Note note = dataset.get(position);
        holder.title.setText(note.title);

        holder.star1.setVisibility(View.VISIBLE);
        holder.star2.setVisibility(View.VISIBLE);
        holder.star3.setVisibility(View.VISIBLE);

        switch (note.priority) {
            case 1:
                holder.star2.setVisibility(View.INVISIBLE);
                holder.star3.setVisibility(View.INVISIBLE);
            case 2:
                holder.star3.setVisibility(View.INVISIBLE);
        }

        holder.content.setText(note.content);
        holder.layout.setTag(new int[]{position, note.id});

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(note.createdAt);
        holder.date.setText(currentDateTimeString);

        if (note.image != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(note.image);
            Bitmap selectedImage = BitmapFactory.decodeStream(bis);
            holder.imageView.setImageBitmap(selectedImage);
        }
    }

    public NotesAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.dataset = notes;
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
