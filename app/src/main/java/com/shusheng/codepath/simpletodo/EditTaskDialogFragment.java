package com.shusheng.codepath.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.shusheng.codepath.simpletodo.data.Task;

import org.parceler.Parcels;

import java.text.ParseException;
import java.util.Calendar;

public class EditTaskDialogFragment extends DialogFragment {

  public interface EditTaskDialogListener {
    void onFinishEditDialog(Task task, int pos);
  }

  private EditText etTitle;
  private DatePicker dpDueDate;
  private Task task;
  private int pos;
  private Button btnSave;

  public EditTaskDialogFragment() {
    // Empty constructor for DialogFragment
  }

  public static EditTaskDialogFragment newInstance(Task task, int pos) {
    EditTaskDialogFragment frag = new EditTaskDialogFragment();
    Bundle args = new Bundle();
    args.putParcelable("task", Parcels.wrap(task));
    args.putInt("pos", pos);
    frag.setArguments(args);
    return frag;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_edit_task, container);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Get field from view
    etTitle = (EditText) view.findViewById(R.id.edit_text_edit_task);
    dpDueDate = (DatePicker) view.findViewById(R.id.date_picker_due_date);
    dpDueDate.setMinDate(System.currentTimeMillis());
    // Fetch arguments from bundle and set title
    task = Parcels.unwrap(getArguments().getParcelable("task"));
    pos = getArguments().getInt("pos");
    btnSave = (Button) view.findViewById(R.id.btn_save_task);
    btnSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        EditTaskDialogListener listener = (EditTaskDialogListener) getActivity();
        task.setTitle(etTitle.getText().toString());
        try {
          task.setDateFromString(String.format("%d %d %d", dpDueDate.getMonth() + 1, dpDueDate.getDayOfMonth(), dpDueDate.getYear()));
        } catch (ParseException e) {
          e.printStackTrace();
        }
        listener.onFinishEditDialog(task, pos);
        dismiss();
      }
    });

    if (task.getTitle() != null || task.getDueDate() != null) {
      etTitle.setText(task.getTitle());
      etTitle.setSelection(etTitle.getText().length());

      Calendar c = Calendar.getInstance();
      c.setTime(task.getDueDate());
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH);
      int day = c.get(Calendar.DAY_OF_MONTH);
      dpDueDate.updateDate(year, month, day);
    }
    // Show soft keyboard automatically and request focus to field
    etTitle.requestFocus();
    getDialog().getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }
}
