package com.shusheng.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.shusheng.codepath.simpletodo.data.Task;

import org.parceler.Parcels;

import java.text.ParseException;

public class EditItemActivity extends AppCompatActivity {

  EditText etEditItem;
  DatePicker dpDueDate;
  Task task;
  int pos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    task = Parcels.unwrap(getIntent().getParcelableExtra("task"));
    if (task == null) {
      task = new Task();
    }
    pos = getIntent().getIntExtra("pos", 0);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);
    etEditItem = (EditText) findViewById(R.id.etEditItem);
    etEditItem.setText(task.getTitle());
    etEditItem.setSelection(etEditItem.getText().length());

    dpDueDate = (DatePicker) findViewById(R.id.picker_due_date);
    dpDueDate.setMinDate(System.currentTimeMillis());
  }

  public void onSaveItem(View v) {
    Intent data = new Intent();
    task.setTitle(etEditItem.getText().toString());
    try {
      task.setDateFromString(String.format("%d %d %d", dpDueDate.getMonth(), dpDueDate.getDayOfMonth(), dpDueDate.getYear()));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    data.putExtra("task", Parcels.wrap(task));
    data.putExtra("pos", pos);
    setResult(RESULT_OK, data);
    finish();
  }
}
