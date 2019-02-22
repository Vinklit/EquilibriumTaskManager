package organizer.chemgames.equilibrium;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class MainActivityThreadTest extends Activity {

    //TODO: sort by treemap, sort also completed tasks

    //TODO: cancel timer on restart

    //TODO: make a list of recyclerviews and give a possibility to edit/delete categories (future), or set a list of cat. at the beginning

    //TODO:reschedule (right)- dialog box. Reschedule: dialog box with time only: change category, start and end time.
    //Example: if daily task: tomorrow or schedule, weekly: next week or schedule

    //TODO: consommation de RAM en temps réel

    //TODO: make fragments with daily, weekly, monthly, full overview of progress

    //TODO DESIGN:
    // TODO - Floating action button (coursera)
    // TODO - Expandable areas,  resolve the change size on delete issue




    private static final int REQUEST = 0;

    public static final String ITEM_SEP = System.getProperty("line.separator");


    TaskAdapter_fam adapter_fam;
    TaskAdapter_fam adapter_prof;
    TaskAdapter_fam adapter_educ;
    TaskAdapter_fam adapter_sport;
    TaskAdapter_fam adapter_hobb;
    TaskAdapter_fam adapter_adm;
    FloatingActionButton add;
    Thread  thr;
    Thread  thr2;
    RecyclerView recyclerView_fam;
    RecyclerView recyclerView_prof;
    RecyclerView recyclerView_educ;
    RecyclerView recyclerView_sport;
    RecyclerView recyclerView_hobb;
    RecyclerView recyclerView_adm;
    ConstraintLayout test;
    ConstraintLayout test2;
    TextView text1;
    TextView text2;
    Task st;
    Task t;

    int a;
    int b;
    int i;

    long timeWhenCancelled =0;
    int progressWhenCancelled =0;
    long timecurr=0;
    int j = 0;
    DatabaseHelper myDb;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toast.makeText( MainActivityThreadTest.this,"create", Toast.LENGTH_LONG ).show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST && resultCode == RESULT_OK){
            Task lt = new Task(intent);
            saveItem(lt);
            this.recreate();
            Toast.makeText( MainActivityThreadTest.this,"OAR"+lt.getName(), Toast.LENGTH_LONG ).show();
            } }


  @Override
    public void onResume() {
        super.onResume();

      add = (FloatingActionButton)findViewById( R.id.addtask );
      add.setOnClickListener( new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent( MainActivityThreadTest.this, AddTask.class );
              startActivityForResult( intent, REQUEST );
          }} );

      recyclerView_fam = (RecyclerView) findViewById(R.id.recyclerView_fam);
      recyclerView_fam.setLayoutManager(new LinearLayoutManager(this));
      recyclerView_prof = (RecyclerView) findViewById(R.id.recyclerView_prof);
      recyclerView_prof.setLayoutManager(new LinearLayoutManager(this));
      recyclerView_educ = (RecyclerView) findViewById(R.id.recyclerView_educ);
      recyclerView_educ.setLayoutManager(new LinearLayoutManager(this));
      recyclerView_sport = (RecyclerView) findViewById(R.id.recyclerView_sport);
      recyclerView_sport.setLayoutManager(new LinearLayoutManager(this));
      recyclerView_hobb = (RecyclerView) findViewById(R.id.recyclerView_hobb);
      recyclerView_hobb.setLayoutManager(new LinearLayoutManager(this));
      recyclerView_adm = (RecyclerView) findViewById(R.id.recyclerView_adm);
      recyclerView_adm.setLayoutManager(new LinearLayoutManager(this));

      adapter_fam = new TaskAdapter_fam(this, new TaskAdapter_fam.ItemClickListener(){
          @Override
          public void onItemClick(View v, int position) {
              //edit task
          }}  ) ;
      recyclerView_fam.setAdapter(adapter_fam);

      ItemTouchHelper itemTouchHelperFam = new ItemTouchHelper( new SwipeToDelete_fam( adapter_fam ) );
      itemTouchHelperFam.attachToRecyclerView( recyclerView_fam );


      adapter_prof = new TaskAdapter_fam(this, new TaskAdapter_fam.ItemClickListener(){
          @Override
          public void onItemClick(View v, int position) {
              //edit task
          }}  ) ;
      recyclerView_prof.setAdapter(adapter_prof);

      ItemTouchHelper itemTouchHelperProf = new ItemTouchHelper( new SwipeToDelete_fam( adapter_prof ) );
      itemTouchHelperProf.attachToRecyclerView( recyclerView_prof );


      adapter_educ = new TaskAdapter_fam(this, new TaskAdapter_fam.ItemClickListener(){
          @Override
          public void onItemClick(View v, int position) {
              //edit task
          }}  ) ;
      recyclerView_educ.setAdapter(adapter_educ);

      ItemTouchHelper itemTouchHelperEduc = new ItemTouchHelper( new SwipeToDelete_fam( adapter_educ ) );
      itemTouchHelperEduc.attachToRecyclerView( recyclerView_educ );

      adapter_sport = new TaskAdapter_fam(this, new TaskAdapter_fam.ItemClickListener(){
          @Override
          public void onItemClick(View v, int position) {
              //edit task
          }}  ) ;
      recyclerView_sport.setAdapter(adapter_sport);

      ItemTouchHelper itemTouchHelperSport = new ItemTouchHelper( new SwipeToDelete_fam( adapter_sport ) );
      itemTouchHelperSport.attachToRecyclerView( recyclerView_sport );

      adapter_hobb = new TaskAdapter_fam(this, new TaskAdapter_fam.ItemClickListener(){
          @Override
          public void onItemClick(View v, int position) {
              //edit task
          }}  ) ;
      recyclerView_hobb.setAdapter(adapter_hobb);
      ItemTouchHelper itemTouchHelperHobb = new ItemTouchHelper( new SwipeToDelete_fam( adapter_hobb ) );
      itemTouchHelperHobb.attachToRecyclerView( recyclerView_hobb );


      adapter_adm = new TaskAdapter_fam(this, new TaskAdapter_fam.ItemClickListener(){
          @Override
          public void onItemClick(View v, int position) {
              //edit task
          }}  ) ;
      recyclerView_adm.setAdapter(adapter_adm);

      ItemTouchHelper itemTouchHelperAdm = new ItemTouchHelper( new SwipeToDelete_fam( adapter_adm ) );
      itemTouchHelperAdm.attachToRecyclerView( recyclerView_adm );

      Toast.makeText( MainActivityThreadTest.this,"resume", Toast.LENGTH_LONG ).show();

      getItems();
    }







    // Load stored Tasks

        private void getItems() {
            try {
                myDb = new DatabaseHelper(this);
                Cursor res = myDb.getAllData();


                while (res.moveToNext()) {
                    String updname = res.getString( 1 );
                    String updcategory = res.getString( 2 );
                    String upddate = res.getString( 3 );
                    String updset_date = res.getString( 4 );
                    String updcal_date = res.getString( 5 );
                    String uprog_when_back = res.getString( 6 );
                    String utime_when_back = res.getString( 7 );


                    final Task t = new Task( updname, Task.Category.valueOf( updcategory ), Task.FORMAT.parse( upddate ), Long.valueOf( updset_date ), Long.valueOf( updcal_date ), Integer.valueOf( uprog_when_back ), Long.valueOf( utime_when_back ) );

                        if (t.getCategory()==Task.Category.FAM ){
                        adapter_fam.add( t );}
                        if (t.getCategory()==Task.Category.PROF ){
                        adapter_prof.add( t );}
                    if (t.getCategory()==Task.Category.EDUC ){
                        adapter_educ.add( t );}
                    if (t.getCategory()==Task.Category.SPORT ){
                        adapter_sport.add( t );}
                    if (t.getCategory()==Task.Category.HOBB ){
                        adapter_hobb.add( t );}
                    if (t.getCategory()==Task.Category.OTHER ){
                        adapter_adm.add( t );}

                        Calendar cac = Calendar.getInstance();
                        timecurr = cac.getTimeInMillis();
                        long del = t.getMcal_date() - timecurr;
                        int pb = t.getMprog_when_back();
                        long tb = t.getTime_when_back();
                        if (timecurr >= t.getMcal_date()) {
                            t.launchTimerReset( timecurr, tb, pb );
                        } else t.launchTimerwithDelayReset( del, timecurr, tb, pb );

                        // text1.setText(s );
                        thr2 = new Thread() {
                            @Override
                            public void run() {
                                while (t.getProgress() < 100 && t.getProgress() >= 0) {
                                    runOnUiThread( new Runnable() {
                                        @Override
                                        public void run() {
                                            if (t.getCategory()==Task.Category.FAM ){
                                            adapter_fam.notifyItemChanged( adapter_fam.index( t ) );
                                            recyclerView_fam.setItemAnimator( null );
                                            }
                                            if (t.getCategory()==Task.Category.PROF ){
                                                adapter_prof.notifyItemChanged( adapter_prof.index( t ) );
                                                recyclerView_prof.setItemAnimator( null );
                                            }
                                            if (t.getCategory()==Task.Category.EDUC ){
                                                adapter_educ.notifyItemChanged( adapter_educ.index( t ) );
                                                recyclerView_educ.setItemAnimator( null );
                                            }
                                            if (t.getCategory()==Task.Category.SPORT ){
                                                adapter_sport.notifyItemChanged( adapter_sport.index( t ) );
                                                recyclerView_sport.setItemAnimator( null );
                                            }
                                            if (t.getCategory()==Task.Category.HOBB ){
                                                adapter_hobb.notifyItemChanged( adapter_hobb.index( t ) );
                                                recyclerView_hobb.setItemAnimator( null );
                                            }
                                            if (t.getCategory()==Task.Category.OTHER ){
                                                adapter_adm.notifyItemChanged( adapter_adm.index( t ) );
                                                recyclerView_adm.setItemAnimator( null );
                                            }
                                        }
                                    } );
                                    try {
                                        Thread.sleep( 100 );
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                t.cancelTimer();
                            }};thr2.start(); }

                } catch(ParseException e){
                    e.printStackTrace(); } }



    // Save Tasks to SQL file

    private void saveItem(Task n) {

        Calendar c = Calendar.getInstance();
        timeWhenCancelled = c.getTimeInMillis();
        progressWhenCancelled = n.getProgress();

            //Update, not save if already saved

            myDb.insertData(
                    n.getName(),
                    n.getCategory().toString(),
                    Task.FORMAT.format(n.getDate()),
                    String.valueOf(n.getMset_date()),
                    String.valueOf(n.getMcal_date()),  String.valueOf(progressWhenCancelled), String.valueOf(timeWhenCancelled)
            );
        }








       //**************** SWIPE TO DELETE***************************





    public class SwipeToDelete_fam extends ItemTouchHelper.SimpleCallback {
        private TaskAdapter_fam famadapter;
        ColorDrawable background;

        public SwipeToDelete_fam(TaskAdapter_fam adapter) {
            super( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
            famadapter = adapter;
            background = new ColorDrawable();
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {

                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setMessage("Are you sure you want to delete the task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Get the position of the item to be deleted

                                int position = viewHolder.getAdapterPosition();
                                Task n = famadapter.getItem( position );
                                //n.setProgress( 100 );
                                famadapter.deleteTask( position );
                                myDb.deleteItem(String.valueOf(n.getMcal_date()) );
                                dialog.cancel();
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                famadapter.notifyDataSetChanged();
                                dialog.cancel();
                            }})
                        .create()
                        .show(); }

            if (direction == ItemTouchHelper.RIGHT) {

                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setMessage("Reschedule the task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Get the position of the item to be deleted

                                int position = viewHolder.getAdapterPosition();
                                famadapter.getItem( position ).cancelTimer();
                                dialog.cancel();
                                // Then you can remove this item from the adapter
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                famadapter.notifyDataSetChanged();
                                dialog.cancel();
                            }})
                        .create()
                        .show(); }
        }}







}




