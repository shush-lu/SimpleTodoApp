package com.shusheng.codepath.simpletodo.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(database = SimpleTodoDatabase.class)
public class Task extends BaseModel {

    SimpleDateFormat sf = new SimpleDateFormat("MM dd yyyy");

    @Column
    @PrimaryKey (autoincrement = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    private Date dueDate;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setDateFromString(String date) throws ParseException {
        sf.setLenient(true);
        this.dueDate = sf.parse(date);
    }
    // Todo: catch exception

    public String getDataInString(){
        return sf.format(dueDate);
    }
}
