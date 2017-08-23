package com.shusheng.codepath.simpletodo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.shusheng.codepath.simpletodo.data.Task;

import org.parceler.Parcels;

/**
 * @author Shusheng LU
 */

public class DeleteTaskDialogFragment extends DialogFragment {

  private Task task;
  private int pos;

  public interface DeleteTaskDialogListener {
    void onConfirmDeleteDialog(Task task, int pos);
  }


  public DeleteTaskDialogFragment() {
    // Empty constructor
  }

  public static DeleteTaskDialogFragment newInstance(Task task, int pos) {
    DeleteTaskDialogFragment frag = new DeleteTaskDialogFragment();
    Bundle args = new Bundle();
    args.putParcelable("task", Parcels.wrap(task));
    args.putInt("pos", pos);
    frag.setArguments(args);
    return frag;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    task = Parcels.unwrap(getArguments().getParcelable("task"));
    pos = getArguments().getInt("pos");
    String title = "Delete " + task.getTitle();
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setTitle(title);
    alertDialogBuilder.setMessage("Are you sure you want to delete this task?");
    alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        DeleteTaskDialogListener listener = (DeleteTaskDialogListener) getActivity();
        listener.onConfirmDeleteDialog(task, pos);
        dialog.dismiss();
      }
    });
    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (dialog != null) {
          dialog.dismiss();
        }
      }

    });
    return alertDialogBuilder.create();
  }

  @Override
  public void onStart() {
    super.onStart();
    Button posBtn = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
    Button negBtn = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE);
    posBtn.setTextColor(Color.RED);
    posBtn.setAllCaps(false);
    negBtn.setAllCaps(false);

  }

}
