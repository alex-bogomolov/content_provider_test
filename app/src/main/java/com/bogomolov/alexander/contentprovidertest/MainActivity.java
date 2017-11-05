package com.bogomolov.alexander.contentprovidertest;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NotesAdapter adapter;

    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final static String PROVIDER_URI = "content://com.bogomolov.alexander.androidlab.notes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        this.recyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        this.recyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(this.layoutManager);

        adapter = new NotesAdapter(this, getNotes());
        recyclerView.setAdapter(adapter);
    }

    public void updateNotes(View view) {
        adapter.dataset = getNotes();
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Note> getNotes() {
        Uri uri = Uri.parse(PROVIDER_URI);

        String[] projection = {
                NotesContract.NotesEntry._ID, NotesContract.NotesEntry.COLUMN_NAME_TITLE,
                NotesContract.NotesEntry.COLUMN_NAME_CONTENT, NotesContract.NotesEntry.COLUMN_NAME_PRIORITY,
                NotesContract.NotesEntry.COLUMN_NAME_CREATED_AT, NotesContract.NotesEntry.COLUMN_NAME_IMAGE
        };

        String selection = "created_at LIKE ?";
        String[] selectionArgs = { "%" + dateFormat.format(new Date()) + "%" };

        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);

        ArrayList<Note> notes = new ArrayList<>();

        while(cursor.moveToNext()) {
            try {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_TITLE));
                String c = cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_CONTENT));
                int p = cursor.getInt(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_PRIORITY));
                Date createdAt = dateTimeFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_CREATED_AT)));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_NAME_IMAGE));

                notes.add(new Note(id, title, c, p, image, createdAt));
            } catch (ParseException e) {
            }
        }

        cursor.close();

        return notes;
    }
}
