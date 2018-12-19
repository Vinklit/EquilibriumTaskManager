package organizer.chemgames.equilibrium;
import java.util.ArrayList;
import java.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    //TODO: consommation de RAM en temps réel

    private static final int REQUEST = 0;

    TaskAdapter adapter;
    Button add;
    TextView titlelabel;
    RecyclerView recyclerView;
    Thread  thr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        titlelabel = (TextView)findViewById( R.id.TitleLabel );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        adapter = new TaskAdapter(this, new TaskAdapter.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                adapter.getItem( position ).cancelTimer();
                adapter.deleteItem (position);
            }}  ) ;



        add = (Button)findViewById( R.id.addtask );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mAddNewToDoIntent = new Intent( MainActivity.this, AddTask.class );
                startActivityForResult( mAddNewToDoIntent, REQUEST );
            }} );
        recyclerView.setAdapter(adapter);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST){
            if (resultCode == RESULT_OK){
               final Task t = new Task(intent);
               adapter.add(t);
                 t.launchTimer();
                 thr  = new Thread() {
            @Override
            public void run() {
                //TODO: the progress continues once you leave the activity: The thread should only be launched onResume, stopped onPause

                while (t.getProgress() < 100) {
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemChanged(adapter.index(t) );
                            recyclerView.setItemAnimator(null);
                        }} );
                    try {
                        Thread.sleep( 100 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } }
                    t.setProgress(100 );
                    t.cancelTimer();

            }}; thr.start(); } } }




    @Override
    public void onResume() {
        super.onResume();
        }

    @Override
    protected void onPause() {
        super.onPause(); }
}




