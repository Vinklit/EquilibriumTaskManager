package organizer.chemgames.equilibrium;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class MainActivity extends Activity {

    TextView progres;
    TextView somedata;
    ProgressBar progressBar;
    Thread mThread;
    Task myTask;
    Timer myTimer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        progres = (TextView)findViewById( R.id.progress );
        progressBar = (ProgressBar)findViewById( R.id.progressbar );
        somedata = (TextView)findViewById( R.id.datastring );


        myTask = new Task(10);
        myTimer = new Timer();
        myTimer.schedule(myTask, 0, 1000);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mThread = new Thread( ) {
            int progress;
            @Override
            public void run() {
                System.out.println("START");
                progress = myTask.getProgress();
                while (myTask.getProgress() <= 9){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //adapter.notifyDataSetChanged();
                            // dans l'idéal, séparer ce qui ce passe run on UI thread
                            progres.setText( ""+myTask.getProgress());
                            progressBar.setProgress( myTask.getProgress() );
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("END!!!");
            }
        };
        mThread.start();
    }
}
