package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        EditText editText = findViewById(R.id.editText);

        // Getting data from main activity
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);// default value is set as a value that will never be passed from main activity

        if(noteId != -1){
           // Log.i("info", "onCreate: iam here");
            editText.setText(MainActivity.notes.get(noteId));
        }
        // we haven't given note id to intent called by add note. so it will go to this else case

        else{
            MainActivity.notes.add("");//adding a new empty note
            noteId = MainActivity.notes.size() - 1; // adding a note id to it
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }

        // checking if edit text text is changed if it is changed then it is added to notes

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                // saving the data permanently when a change is made to note
                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
