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
    public final static String SETDATE = "set_date";
    public final static String CALDATE = "cal_date";

        int i;
        int progress;
        Timer timer;
        TimerTask timerTask;

        String taskname = new String();
        int mfin=0;
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);
    private Date mDate = new Date();
    private long mset_date = 0;
    private long mcal_date = 0;


        Task (String name, int mfin, Date date, long set_date, long cal_date){
            this.taskname = name;
            this.mfin=mfin;
            this.mDate = date;
            this.mset_date = set_date;
            this.mcal_date = cal_date;
        }

    Task(Intent intent) {
        taskname = intent.getStringExtra(Task.NAME);
        mfin = intent.getIntExtra(Task.END, 0  );
        mset_date = intent.getLongExtra(Task.SETDATE, 0 );
        mcal_date = intent.getLongExtra(Task.CALDATE, 0 );
        try {
            mDate = Task.FORMAT.parse(intent.getStringExtra(Task.DATE));
        } catch (ParseException e) {
            mDate = new Date();
        } }



//make a void function to link to holder

    public double getProg() {
        final String aat = String.valueOf( mcal_date );
        final double st = Double.parseDouble( aat );

        final String sst = String.valueOf( mset_date );
        final double ws = Double.parseDouble( sst );

        final double b = ws-st;  //scheduled - current
        final double prog = b / 1000;
        return prog;
    }
    //progressBar.setProgress( (int) (100 / (prog) * holder.i) );



   public Timer launchTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //TODO: i < maximum possible time (1 year) (or progress), seems like it is the same counter
                // TODO: but individual timers can be stopped from UI thread :)
                //TODO: because of double, small delay between the tasks
                if (i<1000){
                    System.out.println("Time's up!"+i);
                    if ((int) (100 / (getProg()) * i)<100) {
                        setProgress( (int) (100 / (getProg()) * i) );
                    }
                    else  setProgress(100 );
                    i++; }
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

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTaskname() {
        return taskname;
    }

    public Date getDate() {
        return mDate; }

    public void setDate(Date date) {
        mDate = date; }

    public long getSet_date() {
        return mset_date;
    }

    public void setSet_date(int set_date) {
        mset_date = set_date;
    }

    public long getCal_date() {
        return mcal_date;
    }

    public void setCal_date(int cal_date) {
        mcal_date = cal_date;
    }



    public static void packageIntent(Intent intent, String name, int mfin, String date, long set_date, long cal_date) {

        intent.putExtra(Task.NAME, name);
        intent.putExtra(Task.END, mfin);
        intent.putExtra(Task.DATE, date);
        intent.putExtra(Task.SETDATE, set_date);
        intent.putExtra(Task.CALDATE, cal_date);

    }

}



