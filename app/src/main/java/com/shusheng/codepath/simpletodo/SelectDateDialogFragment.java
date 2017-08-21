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
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class SelectDateDialogFragment extends DialogFragment {

  private DatePicker dpDueDate;
  private Button btnSeleteDate;

  private Date dueDate;

  public interface SelectDateDialogListener {
    void onFinishSelectDateDialog(String inputText);
  }


  public SelectDateDialogFragment() {
  }

  public static SelectDateDialogFragment newInstance(Date dueDate) {
    SelectDateDialogFragment frag = new SelectDateDialogFragment();
    Bundle args = new Bundle();
    if (dueDate == null) {
      args.putLong("dueDate", System.currentTimeMillis());
    } else {
      args.putLong("dueDate", dueDate.getTime());
    }
    frag.setArguments(args);
    return frag;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_select_date, container);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    dpDueDate = (DatePicker) view.findViewById(R.id.date_picker_due_date);
    btnSeleteDate = (Button) view.findViewById(R.id.btn_save_date);

    dueDate = new Date(getArguments().getLong("dueDate"));
    Calendar c = Calendar.getInstance();
    c.setTime(dueDate);
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    dpDueDate.updateDate(year, month, day);

    btnSeleteDate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendBackResult();
      }
    });
  }

  public void sendBackResult() {
    SelectDateDialogListener listener = (SelectDateDialogListener) getTargetFragment();
    listener.onFinishSelectDateDialog(String.format("%d %d %d", dpDueDate.getMonth() + 1, dpDueDate.getDayOfMonth(), dpDueDate.getYear()));
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
