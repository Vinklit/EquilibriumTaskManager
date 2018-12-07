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



    public final static String TITLE = "title";
    public final static String PRIORITY = "priority";
    public final static String STATUS = "status";
    public final static String DATE = "date";
    public final static String PROGRESS = "progress";
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    private String mTitle = new String();
    private Priority mPriority = Priority.LOW;
    private Status mStatus = Status.NOTDONE;
    private Date mDate = new Date();
    private int mProgress = 0;


    Task(String title, Priority priority, Status status, Date date, int progress) {
        this.mTitle = title;
        this.mPriority = priority;
        this.mStatus = status;
        this.mDate = date;
        this.mProgress = progress;
    }

    Task(Intent intent) {

        mTitle = intent.getStringExtra(Task.TITLE);
        mPriority = Priority.valueOf(intent.getStringExtra(Task.PRIORITY));
        mStatus = Status.valueOf(intent.getStringExtra(Task.STATUS));
        mProgress = intent.getIntExtra(Task.PROGRESS, 0  );

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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }


    public static void packageIntent(Intent intent, String title,
                                     Priority priority, Status status, String date, int progress) {

        intent.putExtra(Task.TITLE, title);
        intent.putExtra(Task.PRIORITY, priority.toString());
        intent.putExtra(Task.STATUS, status.toString());
        intent.putExtra(Task.DATE, date);
        intent.putExtra(Task.PROGRESS, progress);

    }

}
