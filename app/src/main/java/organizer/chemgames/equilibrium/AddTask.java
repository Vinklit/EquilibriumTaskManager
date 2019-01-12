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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddTask extends Activity {

    private static final long MILLIS_IN_SECOND = 1000L;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;
    private static final int DAYS_IN_YEAR = 365; //I know this value is more like 365.24...
    private static final long MILLISECONDS_IN_YEAR = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_YEAR;
    private static final long MILLISECONDS_IN_FIVE_MIN = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * 5;
    private static final long MILLISECONDS_IN_24H = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY;
    private static final long MILLISECONDS_IN_7D = MILLISECONDS_IN_24H *7;
    private static final long MILLISECONDS_IN_14D = MILLISECONDS_IN_24H *14;
    private static final long MILLISECONDS_IN_MONTH = MILLISECONDS_IN_YEAR/12;
    private static String s_time;
    private static String s_date;
    private static TextView t_date;
    private static TextView t_time;
    private static String end_s_time;
    private static String end_s_date;
    private static TextView end_t_date;
    private static TextView end_t_time;
    private Date date;
    private EditText title;
    private RadioGroup categoryGroup;
    private RadioGroup scheduleGroup;
    private long caldate ;
    private long setdate ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        title = (EditText) findViewById(R.id.title);
        t_date = (TextView) findViewById(R.id.date);
        t_time = (TextView) findViewById(R.id.time);
        end_t_date = (TextView) findViewById(R.id.end_date);
        end_t_time = (TextView) findViewById(R.id.end_time);
        categoryGroup = (RadioGroup)findViewById( R.id.categoryGroup );
        scheduleGroup = (RadioGroup)findViewById( R.id.scheduleGroup );

        // Default is current time
        date = new Date();
        date = new Date(date.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        t_date.setText(s_date);
        setendDateString( c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) );
        end_t_date.setText(end_s_date);
        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
        t_time.setText(s_time);
        setendTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
        end_t_time.setText(end_s_time);


        final Button startdatePickerButton = (Button) findViewById(R.id.date_picker_button);
        startdatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showstartDatePickerDialog();
            }});

        final Button starttimePickerButton = (Button) findViewById(R.id.time_picker_button);
        starttimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showstartTimePickerDialog();
            }});

        final Button enddatePickerButton = (Button) findViewById(R.id.end_date_picker_button);
        enddatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showendDatePickerDialog();
            }});

        final Button endtimePickerButton = (Button) findViewById(R.id.end_time_picker_button);
        endtimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showendTimePickerDialog();
            }});

        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }});

        final CheckBox schedule = (CheckBox)findViewById( R.id.schedulenow );

        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("");
                date = new Date();
                date = new Date(date.getTime());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                t_date.setText(s_date);
                setendDateString( c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) );
                end_t_date.setText(end_s_date);
                setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
                t_time.setText(s_time);
                setendTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
                end_t_time.setText(end_s_time);
                categoryGroup.clearCheck();
                scheduleGroup.clearCheck();
                schedule.setChecked( false );
            }
        });

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             String titleString = title.getText().toString();


                Task.Category category = null;
                switch (categoryGroup.getCheckedRadioButtonId()){
                    case R.id.social:
                        category = Task.Category.FAM ;
                        break;
                    case  R.id.professionnal:
                        category = Task.Category.PROF;
                        break;
                    case  R.id.education:
                        category = Task.Category.EDUC;
                        break;
                    case R.id.sport:
                        category = Task.Category.SPORT;
                        break;
                    case  R.id.hobbies:
                        category = Task.Category.HOBB;
                        break; }


                String wholeDate = end_s_date + " " + end_s_time;
                SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.US);
                Date d = null;
                try { d = format.parse(wholeDate); } catch (ParseException e) { e.printStackTrace(); }
                setdate = d.getTime();


                if (schedule.isChecked()) {
                    Calendar cc = Calendar.getInstance();
                    caldate = cc.getTimeInMillis();
                }

                else {
                    String startDate = s_date + " " + s_time;
                    SimpleDateFormat startformat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.US);
                    Date startd = null;
                    try { startd = startformat.parse(startDate); } catch (ParseException e) { e.printStackTrace(); }
                    caldate = startd.getTime();
                    }

                switch (scheduleGroup.getCheckedRadioButtonId()){
                    case R.id.daily:
                       setdate = caldate + MILLISECONDS_IN_24H;
                        //setdate = caldate + 60000;
                        break;
                    case  R.id.weekly:
                        setdate = caldate + MILLISECONDS_IN_7D;
                        //setdate = caldate + 120000;
                        break;
                    case  R.id.biweekly:
                        setdate = caldate + MILLISECONDS_IN_14D;
                        break;
                    case R.id.monthly:
                        setdate = caldate + MILLISECONDS_IN_MONTH;
                        break;
                }

                // Package data to send to main
                Intent intent = new Intent();
                Task.packageIntent(intent, category,  titleString, wholeDate, setdate, caldate, 0, 0);
                setResult(RESULT_OK, intent);
                finish();


            }});
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;
        if (monthOfYear < 10) mon = "0" + monthOfYear;
        if (dayOfMonth < 10) day = "0" + dayOfMonth;
        s_date = year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute, int seconde) {
        String hour = "" + hourOfDay;
        String min = "" + minute;
        String sec = "" + seconde;
        if (hourOfDay < 10) hour = "0" + hourOfDay;
        if (minute < 10) min = "0" + minute;
        if (seconde < 10) sec = "0" + seconde;
        s_time = hour + ":" + min + ":" + sec;
    }

    private static void setendDateString(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;
        if (monthOfYear < 10) mon = "0" + monthOfYear;
        if (dayOfMonth < 10) day = "0" + dayOfMonth;
        end_s_date = year + "-" + mon + "-" + day;
    }

    private static void setendTimeString(int hourOfDay, int minute, int seconde) {
        String hour = "" + hourOfDay;
        String min = "" + minute;
        String sec = "" + seconde;
        if (hourOfDay < 10) hour = "0" + hourOfDay;
        if (minute < 10) min = "0" + minute;
        if (seconde < 10) sec = "0" + seconde;
        end_s_time = hour + ":" + min + ":" + sec;
    }

    public static class startDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datedialog = new DatePickerDialog(getActivity(), this, year, month, day);
            long current = c.getTimeInMillis();
            long current_plusone_year = current +  MILLISECONDS_IN_YEAR;
            datedialog.getDatePicker().setMaxDate(current_plusone_year);
            datedialog.getDatePicker().setMinDate( current );
            return datedialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);
            t_date.setText(s_date);
        }}


    public static class startTimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            long current = c.getTimeInMillis();
            long current_plusfive = current +  MILLISECONDS_IN_FIVE_MIN;
            c.setTimeInMillis( current_plusfive );
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog timedialog = new TimePickerDialog(getActivity(), this, hour, minute, false);
            return timedialog;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute, 0);
            t_time.setText(s_time);
        }}


    public static class endDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datedialog = new DatePickerDialog(getActivity(), this, year, month, day);
            long current = c.getTimeInMillis();
            long current_plusone_year = current +  MILLISECONDS_IN_YEAR;
            datedialog.getDatePicker().setMaxDate(current_plusone_year);
            datedialog.getDatePicker().setMinDate( current );
            return datedialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setendDateString(year, monthOfYear, dayOfMonth);
            end_t_date.setText(end_s_date);
        }}


    public static class endTimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            long current = c.getTimeInMillis();
            long current_plusfive = current +  MILLISECONDS_IN_FIVE_MIN;
            c.setTimeInMillis( current_plusfive );
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog timedialog = new TimePickerDialog(getActivity(), this, hour, minute, false);
            return timedialog;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setendTimeString(hourOfDay, minute, 0);
            end_t_time.setText(end_s_time);
        }}



    private void showstartDatePickerDialog() {
        DialogFragment newFragment = new startDatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void showstartTimePickerDialog() {
        DialogFragment newFragment = new startTimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    private void showendDatePickerDialog() {
        DialogFragment newFragment = new endDatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void showendTimePickerDialog() {
        DialogFragment newFragment = new endTimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }


}