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


public class MainActivity extends Activity {

    private ArrayList<Task> data;
    TaskAdapter adapter;
    Button add;
    TextView titlelabel;
    Thread t;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        titlelabel = (TextView)findViewById( R.id.TitleLabel );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializeData();

        adapter = new TaskAdapter(MainActivity.this, data, new TaskAdapter.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                data.get( position ).cancelTimer(  );
                deleteItem (position);
            }}  ) ;



        add = (Button)findViewById( R.id.addtask );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              final Task t = new Task(10, "Task3");
              addtask( t);
              t.launchTimer();
                Thread  thr  = new Thread() {
                    @Override
                    public void run() {
                        //TODO: sometimes progress goes to 9 instead of 8
                        while (t.getProgress() < t.getEnd()-1) {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyItemChanged(data.indexOf( t ) );
                                    recyclerView.setItemAnimator(null);
                                }} );
                            try {
                                Thread.sleep( 100 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } }
                    }}; thr.start();
            }} );
        recyclerView.setAdapter(adapter);

    }



    public void addtask(Task item) {
        data.add(item);
        adapter.notifyItemInserted( data.indexOf( item ) );
        }


    public void deleteItem(int position) {
       data.remove(position);
       adapter.notifyItemRemoved(position);
    }


    private void initializeData() {
        data = new ArrayList<>();
        data.add( new Task(10, "Default_stopped"));
    }


}
