package com.shusheng.codepath.simpletodo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.shusheng.codepath.simpletodo.adapter.TasksAdapter;
import com.shusheng.codepath.simpletodo.data.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements EditTaskDialogFragment.EditTaskDialogListener {

  EditText etNewTask;
  ArrayList<Task> tasksList;
  TasksAdapter tasksAdapter;
  ListView tasksListView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    etNewTask = (EditText) findViewById(R.id.edit_text_new_task);
    tasksList = new ArrayList<>(SQLite.select().from(Task.class).queryList());

    tasksAdapter = new TasksAdapter(this, tasksList);
    tasksListView = (ListView) findViewById(R.id.tasks_list_view);
    tasksListView.setAdapter(tasksAdapter);
    setupListViewListener();
    setupEditTextListener();
  }

  private void setupEditTextListener() {
    etNewTask.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
          Task newTask = new Task(etNewTask.getText().toString());
          newTask.save();
          tasksList.add(newTask);
          tasksAdapter.notifyDataSetChanged();
          etNewTask.setText("");
        }
        return false;
      }
    });
  }

//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.menu_main, menu);
//    return true;
//  }

  private void setupListViewListener() {
    tasksListView.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapter,
                                         View item, int pos, long id) {

            Task selectedTask = (Task) tasksListView.getItemAtPosition(pos);
            selectedTask.delete();
            tasksList.remove(pos);
            tasksAdapter.notifyDataSetChanged();
            return true;
          }
        });

    tasksListView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapter,
                                  View item, int pos, long id) {
            showEditDialog((Task) tasksListView.getItemAtPosition(pos), pos);
          }
        }
    );
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.miAddTask:
        showEditDialog(new Task(), tasksList.size());
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void showEditDialog(Task task, int pos) {
    FragmentManager fm = getSupportFragmentManager();
    EditTaskDialogFragment editTaskDialogFragment = EditTaskDialogFragment.newInstance(task, pos);
    editTaskDialogFragment.show(fm, "fragment_edit_task");
  }

  @Override
  public void onFinishEditDialog(Task task, int pos) {
    task.save();
    if (pos == tasksList.size()) {
      tasksList.add(task);
    } else {
      Task oldTask = tasksList.get(pos);
      oldTask.setTitle(task.getTitle());
      oldTask.setDueDate(task.getDueDate());
    }
    tasksAdapter.notifyDataSetChanged();
  }

  @Override
  public void onBackPressed() {
    tasksList = new ArrayList<>(SQLite.select().from(Task.class).queryList());
    tasksAdapter.notifyDataSetChanged();
    finish();
  }
}
