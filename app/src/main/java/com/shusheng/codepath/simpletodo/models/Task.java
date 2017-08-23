package com.shusheng.codepath.simpletodo.models;

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

  public Task(String title) {
    setTitle(title);
    setPriority(1);
    setStatus(0);
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

  @Column
  String note;

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  /*0 --> High
    1 --> Medium
    2 --> Low
   */
  @Column
  int priority;

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  /* 0 --> To do
     1 --> Done
   */
  @Column
  int status;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
