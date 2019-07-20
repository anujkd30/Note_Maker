package com.example.notes2;
/**
 * Created by ANUJ KD on 6/21/2017.
 */
public class note {
    private  int id;
    private  String title;
    private  String Description;

    public note(){}

    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public String getTitle(){return title;}
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription(){return Description;}
    public void setDescription(String Description){
        this.Description = Description;
    }
}
