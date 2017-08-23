package com.shusheng.codepath.simpletodo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shusheng.codepath.simpletodo.R;
import com.shusheng.codepath.simpletodo.models.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TasksAdapter extends ArrayAdapter<Task> {

  HashMap<Integer, String> priorityMap;
  CheckBox cbStatus;
  TextView tvPriority;
  TextView tvTaskTitle;
  TextView tvDueDate;
  Task task;

  public TasksAdapter(Context context, ArrayList<Task> tasks) {
    super(context, 0, tasks);
  }

  public interface ChangeStatusDialogListener {
    void onFinishChangeSttDialog(Task task, int pos);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    task = getItem(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
    }
    tvTaskTitle = (TextView) convertView.findViewById(R.id.text_view_task_title);
    tvDueDate = (TextView) convertView.findViewById(R.id.text_view_due_date);
    tvPriority = (TextView) convertView.findViewById(R.id.text_view_priority);
    cbStatus = (CheckBox) convertView.findViewById(R.id.check_box_status);

    tvTaskTitle.setText(task.getTitle());

    if (task.getStatus() == 1) {
      tvTaskTitle.setTextColor(Color.GRAY);
      tvTaskTitle.setPaintFlags(tvTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    } else {
      tvTaskTitle.setTextColor(Color.BLACK);
      tvTaskTitle.setPaintFlags(tvTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }

    if (task.getDueDate() != null) {
      tvDueDate.setText(task.getDataInString());
    } else {
      tvDueDate.setText("");
    }
    priorityMap = new HashMap<>();
    priorityMap.put(0, "High");
    priorityMap.put(1, "Medium");
    priorityMap.put(2, "Low");

    int priority = task.getPriority();

    tvPriority.setText(priorityMap.get(task.getPriority()));
    if (priority == 0) {
      tvPriority.setTextColor(Color.rgb(128, 0, 0));
    } else if (priority == 1) {
      tvPriority.setTextColor(Color.rgb(218, 165, 32));
    } else {
      tvPriority.setTextColor(Color.rgb(109, 192, 102));
    }
    cbStatus.setChecked(task.getStatus() != 0);
    cbStatus.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(cbStatus.isEnabled()) {
          task.setStatus(1);
        } else {
          task.setStatus(0);
        }
      }
    });
    return convertView;
  }

}
