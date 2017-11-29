package com.uniqgrid.uniqgridforms;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteFormFragment extends Fragment {

    int mYear,mMonth,mDay;
    String reportDate;
    Long dateValue;
    DatePickerDialog.OnDateSetListener dateSetListener;

    TextView tvReportDate;

    LinearLayout llReportDate;

    Button bSubmit;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_complete, container, false);

        tvReportDate = (TextView) customView.findViewById(R.id.tvReportDate);
        llReportDate = (LinearLayout) customView.findViewById(R.id.llReportDate);
        bSubmit = (Button) customView.findViewById(R.id.bSubmit);

        // Getting Current Date and Time
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        dateValue = c.getTimeInMillis();

        // Setting Default Time and Date
        reportDate = mDay + "-" + (mMonth + 1) + "-" + mYear;
        tvReportDate.setText(reportDate);

        SimpleDateFormat f1 = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = null;
        try {
            d1 = f1.parse(reportDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final long time_to_add = dateValue - d1.getTime();

        // Date Picker
        dateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;

                reportDate = mDay + "-" + (mMonth + 1) + "-" + mYear;
                tvReportDate.setText(reportDate);

                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d = f.parse(reportDate);

                    dateValue = d.getTime() + time_to_add;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        llReportDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),dateSetListener,mYear,mMonth,mDay);
                datePickerDialog.getWindow().setWindowAnimations(R.style.DialogAnimationUpDown);
                datePickerDialog.show();
            }
        });


        return  customView;


    }

}
