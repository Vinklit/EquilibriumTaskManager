package organizer.chemgames.equilibrium;

import android.content.Intent;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Task extends TimerTask {



    public enum Priority {
        LOW, MED, HIGH
    };

    public enum Status {
        NOTDONE, DONE
    };

    int i;
    int z;
    int progress = 0;

//TODO: implémente runnable, lorsqu'on lance une thread, il a son compteur personnel

    public final static String TITLE = "title";
    public final static String PRIORITY = "priority";
    public final static String STATUS = "status";
    public final static String DATE = "date";
    public final static String SETDATE = "set_date";
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    private String mTitle = new String();
    private Priority mPriority = Priority.LOW;
    private Status mStatus = Status.NOTDONE;
    private Date mDate = new Date();
    private long mset_date = 0;


    Task(String title, Priority priority, Status status, Date date, long set_date) {
        this.mTitle = title;
        this.mPriority = priority;
        this.mStatus = status;
        this.mDate = date;
        this.mset_date = set_date;
    }

    Task(Intent intent) {

        mTitle = intent.getStringExtra(Task.TITLE);
        mPriority = Priority.valueOf(intent.getStringExtra(Task.PRIORITY));
        mStatus = Status.valueOf(intent.getStringExtra(Task.STATUS));
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


    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }


    public long getSal_date() {
        return mset_date;
    }

    public void setSet_date(int set_date) {
        mset_date = set_date;
    }



                //affichage seulement

                @Override
                public void run() {
                    //this repeats every 1000 ms
                    long scheduled_time = mDate.getTime();
                    String aat= String.valueOf(scheduled_time);
                    double st =  Double.parseDouble(aat);
                    long when_scheduled = mset_date;
                    String sst= String.valueOf(when_scheduled);
                    double ws =  Double.parseDouble(sst);

                    double b = st-ws; //scheduled - when scheduled
                    final double prog = b/1000;
                    if (i<100 ){
                        z = ( (int) (100 / prog * i) );
                        i++; }
                }

                public int getTimerProgress(){
                return z;
                }








    public static void packageIntent(Intent intent, String title,
                                     Priority priority, Status status, String date,  long set_date) {

        intent.putExtra(Task.TITLE, title);
        intent.putExtra(Task.PRIORITY, priority.toString());
        intent.putExtra(Task.STATUS, status.toString());
        intent.putExtra(Task.DATE, date);
        intent.putExtra(Task.SETDATE, set_date);


    }

}
