package com.shusheng.codepath.simpletodo.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.shusheng.codepath.simpletodo.R;
import com.shusheng.codepath.simpletodo.adapter.TasksAdapter;
import com.shusheng.codepath.simpletodo.fragments.DeleteTaskDialogFragment;
import com.shusheng.codepath.simpletodo.fragments.EditTaskDialogFragment;
import com.shusheng.codepath.simpletodo.models.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements EditTaskDialogFragment.EditTaskDialogListener,
    DeleteTaskDialogFragment.DeleteTaskDialogListener {

  EditText etNewTask;
  ArrayList<Task> tasksList;
  TasksAdapter tasksAdapter;
  ListView tasksListView;
  ImageButton imgBtnAddTodo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    etNewTask = (EditText) findViewById(R.id.edit_text_new_task);
    tasksList = new ArrayList<>(SQLite.select().from(Task.class).queryList());
    Collections.sort(tasksList, new TaskComparator());

    imgBtnAddTodo = (ImageButton) findViewById(R.id.imgbtn_add_todo);

    tasksAdapter = new TasksAdapter(this, tasksList);
    tasksListView = (ListView) findViewById(R.id.tasks_list_view);
    tasksListView.setAdapter(tasksAdapter);

    setupListViewListener();
    setupEditTextListener();
    setupImgBtnListener();
  }

  private void setupImgBtnListener() {
    imgBtnAddTodo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        etNewTask.requestFocus();
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(etNewTask, InputMethodManager.SHOW_IMPLICIT);

      }
    });
  }

  private void setupEditTextListener() {
    etNewTask.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
          Task newTask = new Task(etNewTask.getText().toString());
          newTask.save();
          tasksList.add(newTask);
          Collections.sort(tasksList, new TaskComparator());
          tasksAdapter.notifyDataSetChanged();
          etNewTask.setText("");
        }
        return false;
      }
    });
  }

  private void setupListViewListener() {
    tasksListView.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapter,
                                         View item, int pos, long id) {
            showAlertDialog(tasksList.get(pos), pos);
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

  private void showEditDialog(Task task, int pos) {
    FragmentManager fm = getSupportFragmentManager();
    EditTaskDialogFragment editTaskDialogFragment = EditTaskDialogFragment.newInstance(task, pos);
    editTaskDialogFragment.show(fm, "fragment_edit_task");
  }

  private void showAlertDialog(Task task, int pos) {
    FragmentManager fm = getSupportFragmentManager();
    DeleteTaskDialogFragment dtDialog = DeleteTaskDialogFragment.newInstance(task, pos);
    dtDialog.show(fm, "fragment_alert");
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
    Collections.sort(tasksList, new TaskComparator());
    tasksAdapter.notifyDataSetChanged();
  }

  @Override
  public void onBackPressed() {
    tasksList = new ArrayList<>(SQLite.select().from(Task.class).queryList());
    tasksAdapter.notifyDataSetChanged();
    finish();
  }

  @Override
  public void onConfirmDeleteDialog(Task task, int pos) {
    task.delete();
    tasksList.remove(pos);
    tasksAdapter.notifyDataSetChanged();

  }

  class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
      int statusComp = o1.getStatus() - o2.getStatus();
      return ((statusComp == 0) ? o1.getPriority() - o2.getPriority() : statusComp);
    }

  }
}
