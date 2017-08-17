package com.shusheng.codepath.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shusheng.codepath.simpletodo.data.Task;

import org.parceler.Parcels;

import java.text.ParseException;

public class EditTaskDialogFragment extends DialogFragment implements SelectDateDialogFragment.SelectDateDialogListener {

  public interface EditTaskDialogListener {
    void onFinishEditDialog(Task task, int pos);
  }

  private EditText etTitle;
  private TextView tvDueDate;
  private Task task;
  private int pos;
  private Button btnDone;

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
    tvDueDate = (TextView) view.findViewById(R.id.text_view_due_date);
    btnDone = (Button) view.findViewById(R.id.btn_save_task);

    // Fetch arguments from bundle
    task = Parcels.unwrap(getArguments().getParcelable("task"));
    pos = getArguments().getInt("pos");

    tvDueDate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showEditDialog();
      }
    });

    btnDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        EditTaskDialogListener listener = (EditTaskDialogListener) getActivity();
        task.setTitle(etTitle.getText().toString());
        listener.onFinishEditDialog(task, pos);
        dismiss();
      }
    });

    if (task.getTitle() != null) {
      etTitle.setText(task.getTitle());
      etTitle.setSelection(etTitle.getText().length());
    }

    if (task.getDueDate() != null) {
      tvDueDate.setText(String.format("Due %s", task.getDataInString()));
    }

    // Show soft keyboard automatically and request focus to field
    etTitle.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

  }

  @Override
  public void onResume() {
    // Get existing layout params for the window
    ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
    // Assign window properties to fill the parent
    params.width = WindowManager.LayoutParams.MATCH_PARENT;
    params.height = WindowManager.LayoutParams.MATCH_PARENT;
    getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    // Call super onResume after sizing
    super.onResume();
  }

  private void showEditDialog() {
    FragmentManager fm = getFragmentManager();
    SelectDateDialogFragment selectDateDialogFragment = SelectDateDialogFragment.newInstance(task.getDueDate());
    selectDateDialogFragment.setTargetFragment(EditTaskDialogFragment.this, 300);
    selectDateDialogFragment.show(fm, "fragment_selete_date");
  }


  @Override
  public void onFinishEditDialog(String inputText) {
    try {
      task.setDateFromString(inputText);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    tvDueDate.setText(String.format("Due %s", task.getDataInString()));
    task.save();
  }

}
