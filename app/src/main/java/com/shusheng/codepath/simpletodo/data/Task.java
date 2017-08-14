package com.shusheng.codepath.simpletodo.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(database = SimpleTodoDatabase.class)
@Parcel(analyze = {Task.class})
public class Task extends BaseModel {

  SimpleDateFormat sfSet = new SimpleDateFormat("MM dd yyyy");
  SimpleDateFormat sfGet = new SimpleDateFormat("MMM dd yyyy");
//Todo find better way to display date

  // empty constructor needed by the Parceler library
  public Task() {
  }

  public Task(String title, String date) {
    setTitle(title);
    try {
      setDateFromString(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Column
  @PrimaryKey(autoincrement = true)
  int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column
  String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Column
  Date dueDate;

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public void setDateFromString(String date) throws ParseException {
    sfSet.setLenient(true);
    this.dueDate = sfSet.parse(date);
  }
  // Todo: catch exception

  public String getDataInString() {
    return sfGet.format(dueDate);
  }
}
