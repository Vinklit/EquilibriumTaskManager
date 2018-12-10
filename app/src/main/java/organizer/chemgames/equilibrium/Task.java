package organizer.chemgames.equilibrium;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task {

    public enum Priority {
        LOW, MED, HIGH
    };

    public enum Status {
        NOTDONE, DONE
    };

//TODO: implémente runnable, lorsqu'on lance une thread, il a son compteur personnel

    public final static String TITLE = "title";
    public final static String PRIORITY = "priority";
    public final static String STATUS = "status";
    public final static String RUNSTATUS = "runstatus";
    public final static String DATE = "date";
    public final static String CALDATE = "cal_date";
    public final static String SETDATE = "set_date";
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    private String mTitle = new String();
    private Priority mPriority = Priority.LOW;
    private Status mStatus = Status.NOTDONE;
    private int mRunstatus = 0;
    private Date mDate = new Date();
    private long mcal_date = 0;
    private long mset_date = 0;


    Task(String title, Priority priority, Status status, int runstatus, Date date, long cal_date, long set_date) {
        this.mTitle = title;
        this.mPriority = priority;
        this.mStatus = status;
        this.mRunstatus = runstatus;
        this.mDate = date;
        this.mcal_date = cal_date;
        this.mset_date = set_date;
    }

    Task(Intent intent) {

        mTitle = intent.getStringExtra(Task.TITLE);
        mPriority = Priority.valueOf(intent.getStringExtra(Task.PRIORITY));
        mStatus = Status.valueOf(intent.getStringExtra(Task.STATUS));
        mRunstatus = intent.getIntExtra(Task.RUNSTATUS, 0  );
        mcal_date = intent.getLongExtra(Task.CALDATE, 0  );
        mset_date = intent.getLongExtra(Task.SETDATE, 0 );

        try {
            mDate = Task.FORMAT.parse(intent.getStringExtra(Task.DATE));
        } catch (ParseException e) {
            mDate = new Date();
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public int getRunstatus() {
        return mRunstatus;
    }

    public void setRunstatus(int runstatus) {
        mRunstatus = runstatus;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public long getCal_date() {
        return mcal_date;
    }

    public void setCal_date(long cal_date) {
        mcal_date = cal_date;
    }

    public long getSal_date() {
        return mset_date;
    }

    public void setSet_date(int set_date) {
        mset_date = set_date;
    }


    public static void packageIntent(Intent intent, String title,
                                     Priority priority, Status status, int runstatus, String date, long cal_date, long set_date) {

        intent.putExtra(Task.TITLE, title);
        intent.putExtra(Task.PRIORITY, priority.toString());
        intent.putExtra(Task.STATUS, status.toString());
        intent.putExtra(Task.RUNSTATUS, runstatus);
        intent.putExtra(Task.DATE, date);
        intent.putExtra(Task.CALDATE, cal_date);
        intent.putExtra(Task.SETDATE, set_date);


    }

}
