package organizer.chemgames.equilibrium;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TaskManagerEquilibrium  extends Activity {

    private static final int ADD_TODO_ITEM_REQUEST = 0;

    ToDoListAdapter mAdapter;
    RecyclerView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.bottom_buttons );
        lv= findViewById(R.id.listview);
        lv.setLayoutManager(new LinearLayoutManager(this));

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
        lv.setAdapter(mAdapter);

        lv.setBackgroundColor( getResources().getColor( R.color.colorAccent ) );
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(lv);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == ADD_TODO_ITEM_REQUEST){
            if (resultCode == RESULT_OK){

                Task mToDoItem = new Task(data);
                mAdapter.add(mToDoItem);
            } } }


    @Override
    public void onResume() {
        super.onResume(); }

    @Override
    protected void onPause() {
        super.onPause(); }
}


