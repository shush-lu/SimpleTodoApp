package com.shusheng.codepath.simpletodo;


import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteDialogFragment extends DialogFragment {

  private EditText etNote;
  private Button btnSaveNote;

  public interface EditNoteDialogListener {
    void onFinishEditNoteDialog(String inputText);
  }

  public EditNoteDialogFragment() {
    // Required empty public constructor
  }

  public static EditNoteDialogFragment newInstance(String note) {
    EditNoteDialogFragment frag = new EditNoteDialogFragment();
    Bundle args = new Bundle();
    args.putString("note", note);
    frag.setArguments(args);
    return frag;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_edit_note, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    etNote = (EditText) view.findViewById(R.id.edit_text_edit_note);
    btnSaveNote = (Button) view.findViewById(R.id.btn_save_note);

    String note = getArguments().getString("note");
    etNote.setText(note);
    etNote.setSelection(etNote.getText().length());

    btnSaveNote.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendBackResult();
      }
    });
  }

  private void sendBackResult() {
    EditNoteDialogListener listener = (EditNoteDialogListener) getTargetFragment();
    listener.onFinishEditNoteDialog(etNote.getText().toString());
    dismiss();
  }

  public void onResume() {
    // Store access variables for window and blank point
    Window window = getDialog().getWindow();
    Point size = new Point();
    // Store dimensions of the screen in `size`
    Display display = window.getWindowManager().getDefaultDisplay();
    display.getSize(size);
    // Set the width of the dialog proportional to 75% of the screen width
    window.setLayout((int) (size.x * 0.8), WindowManager.LayoutParams.WRAP_CONTENT);
    window.setGravity(Gravity.CENTER);
    // Call super onResume after sizing
    super.onResume();
  }

}
