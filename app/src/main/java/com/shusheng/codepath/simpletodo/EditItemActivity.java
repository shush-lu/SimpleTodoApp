package com.shusheng.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

  EditText etEditItem;
  DatePicker dpDueDate;
  int pos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    String content = getIntent().getStringExtra("content");
    pos = getIntent().getIntExtra("pos", 0);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);
    etEditItem = (EditText) findViewById(R.id.etEditItem);
    etEditItem.setText(content);
    etEditItem.setSelection(etEditItem.getText().length());

    dpDueDate = (DatePicker) findViewById(R.id.picker_due_date);
    dpDueDate.setMinDate(System.currentTimeMillis());
  }

  public void onSaveItem(View v) {
    Intent data = new Intent();
    data.putExtra("newContent", etEditItem.getText().toString());
    data.putExtra("date", String.format("%d %d %d", dpDueDate.getMonth(),dpDueDate.getDayOfMonth(),dpDueDate.getYear()));
    data.putExtra("pos", pos);
    setResult(RESULT_OK, data);
    finish();
  }
}
