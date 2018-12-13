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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private ArrayList<Task> data;
    TaskAdapter adapter;
    Timer myTimers[];
    Thread myThreads[];
    int j;
    int progress;
    Button add;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeData();
        //schedule default data
       /* myTimers = new Timer[data.size()];
        for (int z = 0; z < data.size(); z++){
            myTimers[z] = new Timer();
            myTimers[z].schedule(data.get(z), 0, 1000);
        }*/




        adapter = new TaskAdapter(MainActivity.this, data, new TaskAdapter.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                deleteItem (position);
                Toast.makeText( MainActivity.this, "Clicked"+position, Toast.LENGTH_LONG ).show();
            }
        }  ) ;
        recyclerView.setAdapter(adapter);




        add = (Button)findViewById( R.id.addtask );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task t = new Task(5, "Task3");
                addtask( t);
                scheduletask( t );
            }} );




    }

    @Override
    protected void onResume() {
        super.onResume();
        myThreads = new Thread[data.size()];
        for (int z = 0; z < data.size(); z++){
            launchThread(myThreads[z] );
        }
    }



    public void scheduletask(Task item) {
        Timer t = new Timer();
        t.schedule( item, 0, 1000 );
    }

    public void addtask(Task item) {
        data.add(item);
        adapter.notifyDataSetChanged();
        }

    public void deleteItem(int position) {
       data.remove(position);
       adapter.notifyItemRemoved(position);
    }


    public void launchThread(Thread t ){
        t = new Thread( ) {
            @Override
            public void run() {
            while (true){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    adapter.notifyDataSetChanged(); }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } } }};
            t.start();}


    private void initializeData() {
        data = new ArrayList<>();
        data.add( new Task(10, "Default"));
    }


}
