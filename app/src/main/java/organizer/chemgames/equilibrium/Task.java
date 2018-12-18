package organizer.chemgames.equilibrium;

import android.content.Intent;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class Task {
    public final static String NAME = "name";
    public final static String END = "end";
    public final static String DATE = "date";

        int i=0;
        int progress=0;
        Timer timer;
        TimerTask timerTask;

        String taskname = new String();
        int mfin=0;
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);
    private Date mDate = new Date();


        Task (String name, int mfin, Date date){
            this.taskname = name;
            this.mfin=mfin;
            this.mDate = date;
        }

    Task(Intent intent) {
        taskname = intent.getStringExtra(Task.NAME);
        mfin = intent.getIntExtra(Task.END, 0  );
        try {
            mDate = Task.FORMAT.parse(intent.getStringExtra(Task.DATE));
        } catch (ParseException e) {
            mDate = new Date();
        } }



   public Timer launchTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (i<mfin){
                    System.out.println("Time's up!"+i);
                    progress=i;
                    setProgress( i );
                    i++; }
                else i =0;
            }
        }; timer.schedule(timerTask, 0, 1000);
        return timer;
    }

    public void cancelTimer (){
           timer.cancel();
           timer.purge();
    }


    public int getEnd (){
        return mfin;
    }
    public void setEnd(int mfin) {
        this.mfin = mfin;
    }

    public int getProgress (){
           return progress;
        }
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTaskname() {
        return taskname;
    }
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public Date getDate() {
        return mDate; }

    public void setDate(Date date) {
        mDate = date; }



    public static void packageIntent(Intent intent, String name, int mfin, String date) {

        intent.putExtra(Task.NAME, name);
        intent.putExtra(Task.END, mfin);
        intent.putExtra(Task.DATE, date);

    }

}



