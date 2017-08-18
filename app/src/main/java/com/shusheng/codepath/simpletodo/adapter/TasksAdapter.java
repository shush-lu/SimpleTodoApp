package com.shusheng.codepath.simpletodo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shusheng.codepath.simpletodo.R;
import com.shusheng.codepath.simpletodo.data.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TasksAdapter extends ArrayAdapter<Task> {

  HashMap<Integer, String> priorityMap;

  public TasksAdapter(Context context, ArrayList<Task> tasks) {
    super(context, 0, tasks);
  }


  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Task task = getItem(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
    }
    TextView tvTaskTitle = (TextView) convertView.findViewById(R.id.text_view_task_title);
    TextView tvDueDate = (TextView) convertView.findViewById(R.id.text_view_due_date);
    TextView tvPriority = (TextView) convertView.findViewById(R.id.text_view_priority);

    tvTaskTitle.setText(task.getTitle());
    if (task.getDueDate() != null) {
      tvDueDate.setText(task.getDataInString());
    }
    priorityMap = new HashMap<>();
    priorityMap.put(0,"High");
    priorityMap.put(1,"Medium");
    priorityMap.put(2,"Low");

    tvPriority.setText(priorityMap.get(task.getPriority()));

    return convertView;
  }
}
