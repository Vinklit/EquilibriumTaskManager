package organizer.chemgames.equilibrium;
import java.util.ArrayList;
import java.util.Timer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private ArrayList<Task> data;
    TaskAdapter adapter;
    Timer timer;
    Button add;
    TextView titlelabel;
    Thread t;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        titlelabel = (TextView)findViewById( R.id.TitleLabel );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeData();

        adapter = new TaskAdapter(MainActivity.this, data, new TaskAdapter.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //onItemClick doesn't work while UI thread is running
                Toast.makeText( MainActivity.this, "Clicked on "+position, Toast.LENGTH_LONG ).show();
                Timer ti = data.get(position).getTimer();
                ti.cancel();
                //timer doesn't stop unless "else i=0" removed!!!!
                ti.purge();
                deleteItem (position);
            }
        }  ) ;



        add = (Button)findViewById( R.id.addtask );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Task t = new Task(10, "Task3", false);
                addtask( t);
                if (!t.getRunstatus()){
                    t.getTimer();

                Thread  thr  = new Thread() {
                    @Override
                    public void run() {

                        while (t.getProgress() < t.getEnd()-1) {
                 //here has to be individual for each thread, or put a lock on a thread, otherwise synchronized for last one
                            runOnUiThread( new Runnable() {

                                @Override
                                public void run() {
                                    //TODO: if thread != null (???)
                                    adapter.notifyDataSetChanged();
                                }} );
                            try {
                                //sort of works if thread sleeps for >3s
                                Thread.sleep( 100 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } }
                    }}; thr.start();
                    t.setRunstatus( true );}
            }} );

        recyclerView.setAdapter(adapter);

    }



    public void addtask(Task item) {
        data.add(item);
        adapter.notifyDataSetChanged();
        }


    public void deleteItem(int position) {
       data.remove(position);
       adapter.notifyItemRemoved(position);
    }


    private void initializeData() {
        data = new ArrayList<>();
        data.add( new Task(10, "Default_stopped", false));
    }


}
