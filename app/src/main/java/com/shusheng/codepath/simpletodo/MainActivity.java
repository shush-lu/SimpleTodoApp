package com.shusheng.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.shusheng.codepath.simpletodo.adapter.TasksAdapter;
import com.shusheng.codepath.simpletodo.data.Task;

import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  ArrayList<Task> tasksList = new ArrayList<>();
  TasksAdapter tasksAdapter;
  ListView tasksListView;

  private final int REQUEST_CODE = 10;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    tasksAdapter = new TasksAdapter(this, tasksList);
    tasksListView = (ListView) findViewById(R.id.tasks_list_view);
    tasksListView.setAdapter(tasksAdapter);
    setupListViewListener();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  private void setupListViewListener() {
//    lvItems.setOnItemLongClickListener(
//        new AdapterView.OnItemLongClickListener() {
//          @Override
//          public boolean onItemLongClick(AdapterView<?> adapter,
//                                         View item, int pos, long id) {
//            items.remove(pos);
//            itemsAdapter.notifyDataSetChanged();
//            writeItems();
//            return true;
//          }
//        });

//    lvItems.setOnItemClickListener(
//        new AdapterView.OnItemClickListener() {
//          @Override
//          public void onItemClick(AdapterView<?> adapter,
//                                  View item, int pos, long id) {
//            Intent i = new Intent(MainActivity.this, EditItemActivity.class);
//            i.putExtra("content", items.get(pos));
//            i.putExtra("pos", pos);
//            startActivityForResult(i, REQUEST_CODE);
//          }
//        }
//    );
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
      String newContent = data.getExtras().getString("newContent");
      String date = data.getExtras().getString("date");
      int pos = data.getExtras().getInt("pos", 0);
      if (pos == tasksList.size()) {
        Task task = new Task();
        task.setTitle(newContent);
        try {
          task.setDateFromString(date);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        task.save();
        tasksList.add(task);
      } else {
        Task task = tasksList.get(pos);
        task.setTitle(newContent);
        task.save();
      }
      tasksAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.miAddTask:
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("content", "");
        i.putExtra("pos", tasksList.size());
        startActivityForResult(i, REQUEST_CODE);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
