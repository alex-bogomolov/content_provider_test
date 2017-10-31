package com.bogomolov.alexander.contentprovidertest;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String providerUri = "content://com.bogomolov.alexander.androidlab.notes";

        Uri uri = Uri.parse(providerUri);

        String[] projection = {
                NotesContract.NotesEntry._ID, NotesContract.NotesEntry.COLUMN_NAME_TITLE,
                NotesContract.NotesEntry.COLUMN_NAME_CONTENT, NotesContract.NotesEntry.COLUMN_NAME_PRIORITY,
                NotesContract.NotesEntry.COLUMN_NAME_CREATED_AT, NotesContract.NotesEntry.COLUMN_NAME_IMAGE_PATH
        };

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        ArrayList<Note> notes = new ArrayList<>();

        while(cursor.moveToNext()) {
            try {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_TITLE));
                String c = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_CONTENT));
                int p = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_PRIORITY));
                Date createdAt = DateFormat.getDateTimeInstance().parse(cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_CREATED_AT)));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_IMAGE_PATH));

                notes.add(new Note(id, title, c, p, imagePath, createdAt));
            } catch (ParseException e) {
            }
        }

        cursor.close();

        this.recyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        this.recyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(this.layoutManager);

        adapter = new NotesAdapter(this, notes);
        recyclerView.setAdapter(adapter);
    }
}
