package com.shusheng.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  ArrayList<String> items;
  ArrayAdapter<String> itemsAdapter;
  ListView lvItems;

  private final int REQUEST_CODE = 10;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    lvItems = (ListView) findViewById(R.id.lvItems);
    readItems();
    itemsAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, items);
    lvItems.setAdapter(itemsAdapter);
    setupListViewListener();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  private void setupListViewListener() {
    lvItems.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapter,
                                         View item, int pos, long id) {
            items.remove(pos);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            return true;
          }
        });

    lvItems.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapter,
                                  View item, int pos, long id) {
            Intent i = new Intent(MainActivity.this, EditItemActivity.class);
            i.putExtra("content", items.get(pos));
            i.putExtra("pos", pos);
            startActivityForResult(i, REQUEST_CODE);
          }
        }
    );
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
      String newContent = data.getExtras().getString("newContent");
      int pos = data.getExtras().getInt("pos", 0);
      if (pos == items.size()) {
        items.add(newContent);
      } else {
        items.set(pos, newContent);
      }
      itemsAdapter.notifyDataSetChanged();
      writeItems();
    }
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      items = new ArrayList<String>(FileUtils.readLines(todoFile));
    } catch (IOException e) {
      items = new ArrayList<String>();
    }
  }

  private void writeItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      FileUtils.writeLines(todoFile, items);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.miAddTask:
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("content", "");
        i.putExtra("pos", items.size());
        startActivityForResult(i, REQUEST_CODE);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
