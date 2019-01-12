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

    public static final String ITEM_SEP = System.getProperty("line.separator");

    int i;
    int progress;
    Timer timer;
    TimerTask timerTask;

    public enum Category {FAM, PROF, EDUC, SPORT, HOBB};
    public final static String NAME = "name";
    public final static String CATEGORY = "category";
    public final static String DATE = "date";
    public final static String SETDATE = "set_date";
    public final static String CALDATE = "cal_date";
    public final static String PROG_WHEN_BACK = "prog_when_back";
    public final static String TIME_WHEN_BACK = "time_when_back";

    String taskname = new String();
    private Category mycategory = Category.FAM;
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);
    private Date mDate = new Date();
    private long mset_date = 0;
    private long mcal_date = 0;
    private int mprog_when_back = 0;
    private long time_when_back = 0;



        Task (String name, Category category, Date date, long set_date, long cal_date, int prog_when_back, long time_when_back){
            this.taskname = name;
            this.mycategory = category;
            this.mDate = date;
            this.mset_date = set_date;
            this.mcal_date = cal_date;
            this.mprog_when_back = prog_when_back;
            this.time_when_back = time_when_back;
        }

    Task(Intent intent) {
        taskname = intent.getStringExtra(Task.NAME);
        mycategory = Category.valueOf(intent.getStringExtra(Task.CATEGORY));
        mset_date = intent.getLongExtra(Task.SETDATE, 0 );
        mcal_date = intent.getLongExtra(Task.CALDATE, 0 );
        mprog_when_back = intent.getIntExtra(Task.PROG_WHEN_BACK, 0 );
        time_when_back = intent.getLongExtra(Task.TIME_WHEN_BACK, 0 );
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


    //j defines the delay if the application was closed for some time, is calculated in MainActivity
 //long k, h
    public Timer launchTimerwithDelayReset(long del, final long current_time, final long time_back, final int prg){

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //maximum duration: 1 year
                if (i<32000000){
                    System.out.println("Time's up!"+i);
                    //formula doeesn't work if back pressed 2nd time
                    setProgress( prg + (int) ((100 / getProg())* (i)+ (int) ((100 / getProg())* ((current_time-time_back)/1000))));
                    i++; }
            }
        }; timer.schedule(timerTask, del, 1000);
        return timer;
    }

    public Timer launchTimerwithDelay(long del){

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //maximum duration: 1 year
                if (i<32000000){
                    System.out.println("Time's up!"+i);
                    setProgress( (int) ((100 / getProg()) * i));
                    i++; }
            }
        }; timer.schedule(timerTask, del, 1000);
        return timer;
    }

   public Timer launchTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //maximum duration: 1 year
                if (i<32000000){
                    System.out.println("Time's up!"+i);
                        setProgress( (int) ((100 / getProg()) * i));
                    i++; }
            }
        }; timer.schedule(timerTask, 0, 1000);
        return timer;
    }

    public Timer launchTimerReset(final long current_time, final long time_back, final int prg){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //maximum duration: 1 year
                if (i<32000000){
                    System.out.println("Time's up!"+i);
                    setProgress( prg + (int) ((100 / getProg())* (i)+ (int) ((100 / getProg())* ((current_time-time_back)/1000))));
                    i++; }
            }
        }; timer.schedule(timerTask, 0, 1000);
        return timer;
    }

    public void cancelTimer (){
           timer.cancel();
           timer.purge();
    }

    public long getTime_when_back() {
        return time_when_back;
    }

    public void setTime_when_back(long time_when_back) {
        this.time_when_back = time_when_back;
    }

    public String getName() {
        return taskname;
    }
    public void setName(String name) {
        this.taskname = name;
    }

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMprog_when_back() {
        return mprog_when_back;
    }

    public void setMprog_when_back(int mprog_when_back) {
        this.mprog_when_back = mprog_when_back;
    }

    public String getTaskname() {
        return taskname;
    }

    public Category getCategory() { return mycategory; }
    public void setCategory(Category category) { mycategory = category; }

    public Date getDate() {
        return mDate; }

    public void setDate(Date date) {
        mDate = date; }

    public long getMset_date() {
        return mset_date;
    }

    public void setMset_date(long mset_date) {
        this.mset_date = mset_date;
    }

    public long getMcal_date() {
        return mcal_date;
    }

    public void setMcal_date(long mcal_date) {
        this.mcal_date = mcal_date;
    }

    public static void packageIntent(Intent intent, Category category, String name, String date, long set_date, long cal_date, int prog_when_back, long time_when_back) {

        intent.putExtra(Task.NAME, name);
        intent.putExtra(Task.CATEGORY, category.toString());
        intent.putExtra(Task.DATE, date);
        intent.putExtra(Task.SETDATE, set_date);
        intent.putExtra(Task.CALDATE, cal_date);
        intent.putExtra(Task.PROG_WHEN_BACK, prog_when_back);
        intent.putExtra(Task.TIME_WHEN_BACK, time_when_back);

    }

    public String toString() {
        return taskname + ITEM_SEP + mycategory + ITEM_SEP + FORMAT.format(mDate) + ITEM_SEP + mset_date + ITEM_SEP + mcal_date + ITEM_SEP + mprog_when_back + ITEM_SEP + time_when_back;
    }



}



