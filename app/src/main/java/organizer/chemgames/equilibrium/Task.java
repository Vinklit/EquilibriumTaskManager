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
        int i=0;
        int mfin=0;
        int progress;
        String taskname = "Default name";
        Timer timer;
        TimerTask timerTask;
        boolean runstatus;


        Task (int mfin, String name, boolean runstatus){
            this.mfin=mfin;
            this.taskname = name;
            this.runstatus = runstatus;
        }




    public Timer getTimer(){
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

    public boolean getRunstatus (){
        return runstatus;
    }
    public void setRunstatus(boolean runstatus) {
        this.runstatus = runstatus;
    }


    public String getTaskname() {
        return taskname;
    }
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

}



