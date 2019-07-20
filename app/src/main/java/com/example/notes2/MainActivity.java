package com.example.notes2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{

    Button addNote;
    DatabaseHandler databasehandler;
    public static NotesAdapter notesAdapter;
    ListView noteListView;
    List<note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNote=(Button)findViewById(R.id.addNote);
        addNote.setOnClickListener(this);

        databasehandler = new DatabaseHandler(this);
        noteList = databasehandler.getAllNotes();

        notesAdapter = new NotesAdapter(this,noteList);

        noteListView = (ListView) findViewById(R.id.listViewNotes);
        try{
            noteListView.setAdapter(notesAdapter);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void onClick(View v){

        Intent myIntent = new Intent(MainActivity.this,EditNoteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("source","addPress");

        myIntent.putExtras(bundle);

        startActivity(myIntent);
    }
}
