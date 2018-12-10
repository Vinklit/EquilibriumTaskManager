package organizer.chemgames.equilibrium;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TaskManagerEquilibrium  extends Activity {

    private static final int ADD_TODO_ITEM_REQUEST = 0;

    ToDoListAdapter mAdapter;
    ToDoListAdapter2 mAdapter2;
    RecyclerView lv;
    RecyclerView lv2;
    //long current_time;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.bottom_buttons );

        lv= findViewById(R.id.listview);
        lv.setLayoutManager(new LinearLayoutManager(this));

        lv2= findViewById(R.id.listview2);
        lv2.setLayoutManager(new LinearLayoutManager(this));



        TextView footerView = (TextView) findViewById( R.id.footerView );
        footerView.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mAddNewToDoIntent = new Intent( TaskManagerEquilibrium.this, MainActivity.class );
                startActivityForResult( mAddNewToDoIntent, ADD_TODO_ITEM_REQUEST );
            }} );

        mAdapter = new ToDoListAdapter( this, new ToDoListAdapter.ItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
            }} );

                lv.setAdapter( mAdapter );
                lv.setBackgroundColor( getResources().getColor( R.color.colorAccent ) );
                ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper( new SwipeToDeleteCallback( mAdapter ) );
                itemTouchHelper1.attachToRecyclerView( lv );

        mAdapter2 = new ToDoListAdapter2( this, new ToDoListAdapter2.ItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                v.setBackgroundColor( getResources().getColor( R.color.colorAccent ) );
            }} );


        lv2.setAdapter( mAdapter2 );
        lv2.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
        ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper( new SwipeToDeleteCallback2 ( mAdapter2 ) );
        itemTouchHelper2.attachToRecyclerView( lv2 );




    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == ADD_TODO_ITEM_REQUEST){
            if (resultCode == RESULT_OK){

                Task mToDoItem = new Task(data);
                if(mToDoItem.getTitle().equalsIgnoreCase( "A" )){
                    mAdapter.add(mToDoItem);

                }
                else if (mToDoItem.getTitle().equalsIgnoreCase( "B" )){
                    mAdapter2.add(mToDoItem);
                }
                else mAdapter.add(mToDoItem);

            } } }


    @Override
    public void onResume() {
        super.onResume(); }

    @Override
    protected void onPause() {
        super.onPause(); }
}


