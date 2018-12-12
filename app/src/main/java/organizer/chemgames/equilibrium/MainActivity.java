package organizer.chemgames.equilibrium;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class MainActivity extends Activity {

    private ArrayList<Task> data;
    TaskAdapter adapter;
    Timer myTimer;
    Timer myTimers[];
    Thread mThread;
    Thread mThreads[];
    Task myTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeData();


        adapter = new TaskAdapter(this, data) ;
        recyclerView.setAdapter(adapter);
        //Pour le futur, faire une liste des timers
        myTimers = new Timer[data.size()];
        for (int z = 0; z < data.size(); z++){
            myTimers[z] = new Timer();
        }
        for (int z = 0; z < data.size(); z++){
            myTimers[z].schedule(data.get(z), 0, 1000);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int z = 0; z < data.size(); z++){
            mThreads = new Thread[data.size()];
            launchThread(mThreads[z], data.get(z).getProgress());
        }

    }



public void launchThread(Thread t, final int progress){

            t = new Thread( ) {

            @Override
            public void run() {
            System.out.println("START");
            while (progress <= 8){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
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
    t.start();}


    private void initializeData() {
        data = new ArrayList<>();
        data.add( new Task(10, "Task1"));
        data.add(new Task(30, "Task2"));
    }


}
