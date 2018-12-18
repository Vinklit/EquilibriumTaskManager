package organizer.chemgames.equilibrium;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;




public class AddTask extends Activity {

    private EditText name;
    private static TextView date;
    private static TextView time;
    private Date todayDate;
    private static String t_date;
    private static String t_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        name = (EditText) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        //current date and time

        todayDate = new Date();
        todayDate = new Date(todayDate.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(todayDate);
        setDateDisplay(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        date.setText(t_date);
        setTimeDisplay(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.MILLISECOND));
        time.setText(t_time);


        //Chose new date and time

        final Button datePickerButton = (Button) findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "date"); }});

        final Button timePickerButton = (Button) findViewById(R.id.time_picker_button);
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "time"); }});


        // Cancel Button

        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();

            }
        });

        // Send data to main activity

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            /*    Task.Status status = null;
                switch (mStatusRadioGroup.getCheckedRadioButtonId()){
                    case R.id.statusDone:
                        status = Task.Status.DONE;
                        break;
                    case R.id.statusNotDone:
                        status = Task.Status.NOTDONE;
                        break;
                }*/


                // Construct the Date string
                String taskname = name.getText().toString();
                String setDate = t_date + "" + t_time;

                // Package ToDoItem data into an Intent
                Intent intent = new Intent();
                //TODO: add method to Task
                Task.packageIntent(intent, taskname, 15, setDate);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }




    private static void setDateDisplay(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;
        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        t_date = year + "-" + mon + "-" + day;
    }

    private static void setTimeDisplay(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;
        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;
        t_time = hour + ":" + min + ":00";
    }

    // Deadline date

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day); }
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setDateDisplay(year, monthOfYear, dayOfMonth);
            date.setText(t_date);
        }}

    // Deadline time

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, true); }
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            setTimeDisplay(hour, minute, 0);
            time.setText(t_time);
        }}



}
