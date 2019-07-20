package com.example.notes2;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ANUJ KD on 6/22/2017.
 */

public class EditNoteActivity extends Activity implements View.OnClickListener {

    EditText titleEditTextView, descriptionEditTextView;
    String title, description;
    int noteId;
    Boolean isUpdateNode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        isUpdateNode = false;

        titleEditTextView = (EditText) this.findViewById(R.id.titleEditText);
        descriptionEditTextView = (EditText) this.findViewById(R.id.descriptionEditText);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("source")) {
            if (bundle.getString("source").equalsIgnoreCase("editPress")) {
                isUpdateNode = true;
                noteId = bundle.getInt("noteId");
                titleEditTextView.setText(bundle.getString("noteTitle"));
                descriptionEditTextView.setText(bundle.getString("noteDescription"));
                deleteButton.setVisibility(View.VISIBLE);
            } else if (bundle.getString("source").equalsIgnoreCase("addPress")) {
                isUpdateNode = false;
                deleteButton.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "Invalid Arguments", Toast.LENGTH_LONG).show();
                super.onBackPressed();
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                saveNote();
                break;

            case R.id.deleteButton:
                deleteNote();
                break;
        }
    }

    private void saveNote() {
        title = titleEditTextView.getText().toString();
        description = descriptionEditTextView.getText().toString();

        if (!isValidNote())
            return;

        note Note = new note();
        Note.setTitle(title);
        Note.setDescription(description);

        DatabaseHandler databasehandler = new DatabaseHandler(this);
        if (!isUpdateNode) {
            databasehandler.addNote(Note);
            Toast.makeText(this, "Note added successfully", Toast.LENGTH_LONG).show();
        } else {
            Note.setId(noteId);
            databasehandler.updateNote(Note);
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_LONG).show();
        }

        List<note> notes = databasehandler.getAllNotes();
        MainActivity.notesAdapter.clear();
        MainActivity.notesAdapter.addAll(notes);
        MainActivity.notesAdapter.notifyDataSetChanged();

        super.onBackPressed();
    }

    public void deleteNote() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    public void onClick (DialogInterface dialog ,int which){
                    DatabaseHandler databasehandler = new DatabaseHandler(EditNoteActivity.this);

                    databasehandler.deleteNote(String.valueOf(noteId));

                    List<note> notes = databasehandler.getAllNotes();
                    MainActivity.notesAdapter.clear();
                    MainActivity.notesAdapter.addAll(notes);
                    MainActivity.notesAdapter.notifyDataSetChanged();

                    EditNoteActivity.this.onBackPressed();
                    }})
                .setNegativeButton("No", null)
                .show();
    }


    private Boolean isValidNote() {
        if (title.isEmpty() && description.isEmpty()) {
            Toast.makeText(this, "Please enter title and description", Toast.LENGTH_LONG).show();
            return false;
        } else if (title.isEmpty()) {
            Toast.makeText(this, "Please enter title", Toast.LENGTH_LONG).show();
            return false;
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Please enter description", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
