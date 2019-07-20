package com.example.notes2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by ANUJ KD on 6/22/2017.
 */

public class NotesAdapter extends ArrayAdapter<note> {
    private List<note> noteList;
    private Context context;

    public NotesAdapter(Context context, List<note> noteList){
        super(context,R.layout.list_notes,noteList);
        this.noteList=noteList;
        this.context=context;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View v=convertView;

        if(v==null){
            LayoutInflater vi= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_notes,null);
        }

        final note Note= noteList.get(position);

        if(Note!=null){
            TextView title= (TextView) v.findViewById(R.id.title);
            TextView description= (TextView) v.findViewById(R.id.description);
            TextView index= (TextView) v.findViewById(R.id.index);

            if(title!=null){
                title.setText(Note.getTitle());
                index.setText((position+1) + ".");
            }

            if(description!=null){
                description.setText(Note.getDescription());
            }
        }

        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V) {
            Intent myIntent = new Intent(context,EditNoteActivity.class);
            Bundle bundle = new Bundle();

            note Note= noteList.get(position);
            bundle.putString("source","editPress");
            bundle.putString("noteTitle",Note.getTitle());
            bundle.putString("noteDescription",Note.getDescription());
            bundle.putInt("noteId",Note.getId());

            myIntent.putExtras(bundle);
            context.startActivity(myIntent);
        }
       });
        return v;
    }
}
