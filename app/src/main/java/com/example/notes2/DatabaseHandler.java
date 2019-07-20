package com.example.notes2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANUJ KD on 6/21/2017.
 */

public  class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context ){
        super(context, DatabaseValues.DATABASE_NAME,null,DatabaseValues.DATABASE_VERSION);
        SQLiteDatabase db= this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(DatabaseValues.TABLE_NOTES_CREATE);}

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL(DatabaseValues.TABLE_NOTES_DROP);
        onCreate(db);
    }

    public void addNote(note Note){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues noteValues=new ContentValues();
        noteValues.put(DatabaseValues.NOTES_TITLE,Note.getTitle());
        noteValues.put(DatabaseValues.NOTES_DESCRIPTION,Note.getDescription());

        db.insert(DatabaseValues.TABLE_NOTES,null,noteValues);
        db.close();
    }

    public void updateNote(note Note){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues noteValues=new ContentValues();
        noteValues.put(DatabaseValues.NOTES_TITLE,Note.getTitle());
        noteValues.put(DatabaseValues.NOTES_DESCRIPTION,Note.getDescription());

        db.update(DatabaseValues.TABLE_NOTES,noteValues,DatabaseValues.NOTES_ID + " = ?",
                new String[]{String.valueOf(Note.getId())});
        db.close();
    }

    public void deleteNote(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        String deleteQuery= "DELETE FROM "+DatabaseValues.TABLE_NOTES+" WHERE "+DatabaseValues.NOTES_ID+" = ' "+id+" '";

        db.execSQL(deleteQuery);
        db.close();
    }

    public List<note> getAllNotes(){
        List<note> notes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery= "SELECT * FROM " + DatabaseValues.TABLE_NOTES;
        Cursor cursor= db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                note Note = new note();
                Note.setId(Integer.parseInt(cursor.getString(0)));
                Note.setTitle(cursor.getString(1));
                Note.setDescription(cursor.getString(2));

                notes.add(Note);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return notes;
    }
}