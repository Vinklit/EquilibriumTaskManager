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
        int i=0;
        int mfin=0;
        int progress;
        String taskname = "Default name";

        Task (int fin, String name ){
            this.mfin=fin;
            this.taskname = name;
        }

    public void run() {
            if (i<mfin){
                System.out.println("Time's up!"+i);
                progress=i;
                setProgress( i );
                i++; }
            else i =0; }


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
}



