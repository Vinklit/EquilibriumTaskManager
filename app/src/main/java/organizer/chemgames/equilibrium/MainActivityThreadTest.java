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
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class MainActivityThreadTest extends Activity {

    //TODO: sort by treemap, sort also completed tasks

   //TODO: on restart, notify dataset changed

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
    TaskAdapter_prof adapter_prof;
    TaskAdapter_educ adapter_educ;
    TaskAdapter_sport adapter_sport;
    TaskAdapter_hobb adapter_hobb;
    TaskAdapter_other adapter_adm;
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

        myDb = new DatabaseHelper(this);

 // myDb.deleteData();

        test = (ConstraintLayout)findViewById( R.id.expendfam );
        test2 = (ConstraintLayout) findViewById( R.id.collapsefam );

        text1 = (TextView)findViewById( R.id.titlelabelprof );


        add = (FloatingActionButton)findViewById( R.id.addtask );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivityThreadTest.this, AddTask.class );
                startActivityForResult( intent, REQUEST );
            }} );

        recyclerView_fam = (RecyclerView) findViewById(R.id.recyclerView_fam);
        recyclerView_fam.setLayoutManager(new LinearLayoutManager(this));


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        a = (displaymetrics.heightPixels * 70) / 100;
        b= (displaymetrics.heightPixels * 20) / 100;

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
                //v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
                }}  ) ;
       /* if (adapter_fam.getItemCount()>2){
            i = recyclerView_fam.getHeight();
            recyclerView_fam.getLayoutParams().height = i;
            adapter_fam.notifyDataSetChanged();
        }*/
        recyclerView_fam.setAdapter(adapter_fam);
        ItemTouchHelper itemTouchHelperFam = new ItemTouchHelper( new SwipeToDelete_fam( adapter_fam ) );
        itemTouchHelperFam.attachToRecyclerView( recyclerView_fam );
       /* test.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_fam.getLayoutParams().height = a;
                adapter_fam.notifyDataSetChanged();
                test.setVisibility( View.INVISIBLE);
                test2.setVisibility( View.VISIBLE );
            }
        } );*/

      /*  test2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView_fam.getLayoutParams().height = b;
                adapter_fam.notifyDataSetChanged();
                test2.setVisibility( View.INVISIBLE);
                test.setVisibility( View.VISIBLE );
            }

        } );*/




     adapter_prof = new TaskAdapter_prof(this, new TaskAdapter_prof.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                //v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
                 }}  ) ;

        recyclerView_prof.setAdapter(adapter_prof);
        ItemTouchHelper itemTouchHelperProf = new ItemTouchHelper( new SwipeToDelete_prof( adapter_prof ) );
        itemTouchHelperProf.attachToRecyclerView( recyclerView_prof );



        adapter_educ = new TaskAdapter_educ(this, new TaskAdapter_educ.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                //v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
                }}  ) ;


        recyclerView_educ.setAdapter(adapter_educ);
        ItemTouchHelper itemTouchHelperEduc = new ItemTouchHelper( new SwipeToDelete_educ( adapter_educ ) );
        itemTouchHelperEduc.attachToRecyclerView( recyclerView_educ );

        adapter_sport = new TaskAdapter_sport(this, new TaskAdapter_sport.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                //v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
            }}  ) ;
        recyclerView_sport.setAdapter(adapter_sport);
        ItemTouchHelper itemTouchHelperSport = new ItemTouchHelper( new SwipeToDelete_sport( adapter_sport ) );
        itemTouchHelperSport.attachToRecyclerView( recyclerView_sport );

        adapter_hobb = new TaskAdapter_hobb(this, new TaskAdapter_hobb.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                //v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
            }}  ) ;
        recyclerView_hobb.setAdapter(adapter_hobb);
        ItemTouchHelper itemTouchHelperHobb = new ItemTouchHelper( new SwipeToDelete_hobb( adapter_hobb ) );
        itemTouchHelperHobb.attachToRecyclerView( recyclerView_hobb );


        adapter_adm = new TaskAdapter_other(this, new TaskAdapter_other.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                //v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
            }}  ) ;
        recyclerView_adm.setAdapter(adapter_adm);
        ItemTouchHelper itemTouchHelperAdm = new ItemTouchHelper( new SwipeToDelete_adm( adapter_adm ) );
        itemTouchHelperAdm.attachToRecyclerView( recyclerView_adm );


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST && resultCode == RESULT_OK){
               st = new Task(intent);
              Calendar cc = Calendar.getInstance();
                long timenow = cc.getTimeInMillis();
                long del = st.getMcal_date()-timenow;


               //if else statement goes here
          if(st.getCategory()==Task.Category.FAM) {


                   adapter_fam.add( st );
                   Calendar c = Calendar.getInstance();
                   timeWhenCancelled = c.getTimeInMillis();
                 saveItem(st);


           if (timenow >= st.getMcal_date()){
                st.launchTimer();
            }
            else st.launchTimerwithDelay( del );

                   thr = new Thread() {
                       @Override
                       public void run() {
                           while (st.getProgress() < 100 && st.getProgress() >= 0) {
                               runOnUiThread( new Runnable() {
                                   @Override
                                   public void run() {
                                       //t.setProgress( 20 );
                                       adapter_fam.notifyItemChanged( adapter_fam.index( st ) );
                                       recyclerView_fam.setItemAnimator( null );
                                   }
                               } );
                               try {
                                   Thread.sleep( 100 );
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }
                           }
                           st.cancelTimer();
                         //set Prog to > 32000000 in order to pur completed tasks on top
                       }
                   };
                   thr.start(); }

           else if(st.getCategory()==Task.Category.PROF) {


                adapter_prof.add( st );
                Calendar c = Calendar.getInstance();
                timeWhenCancelled = c.getTimeInMillis();
                saveItem(st);



                if (timenow >= st.getMcal_date()){
                    st.launchTimer();
                }
                else st.launchTimerwithDelay( del );

                thr = new Thread() {
                    @Override
                    public void run() {
                        while (st.getProgress() < 100 && st.getProgress() >= 0) {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    //t.setProgress( 20 );
                                    adapter_prof.notifyItemChanged( adapter_prof.index( st ) );
                                    recyclerView_prof.setItemAnimator( null );
                                }
                            } );
                            try {
                                Thread.sleep( 100 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        st.cancelTimer();
                        //set Prog to > 32000000 in order to pur completed tasks on top
                    }
                };
                thr.start(); }


           else if(st.getCategory()==Task.Category.EDUC) {


                adapter_educ.add( st );
                Calendar c = Calendar.getInstance();
                timeWhenCancelled = c.getTimeInMillis();
               saveItem(st);



                if (timenow >= st.getMcal_date()){
                    st.launchTimer();
                }
                else st.launchTimerwithDelay( del );

                thr = new Thread() {
                    @Override
                    public void run() {
                        while (st.getProgress() < 100 && st.getProgress() >= 0) {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    //t.setProgress( 20 );
                                    adapter_educ.notifyItemChanged( adapter_educ.index( st ) );
                                    recyclerView_educ.setItemAnimator( null );
                                }
                            } );
                            try {
                                Thread.sleep( 100 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        st.cancelTimer();
                        //set Prog to > 32000000 in order to pur completed tasks on top
                    }
                };
                thr.start(); }


          else  if(st.getCategory()==Task.Category.SPORT) {


                adapter_sport.add( st );
                Calendar c = Calendar.getInstance();
                timeWhenCancelled = c.getTimeInMillis();
              saveItem(st);



                if (timenow >= st.getMcal_date()){
                    st.launchTimer();
                }
                else st.launchTimerwithDelay( del );

                thr = new Thread() {
                    @Override
                    public void run() {
                        while (st.getProgress() < 100 && st.getProgress() >= 0) {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    //t.setProgress( 20 );
                                    adapter_sport.notifyItemChanged( adapter_sport.index( st ) );
                                    recyclerView_sport.setItemAnimator( null );
                                }
                            } );
                            try {
                                Thread.sleep( 100 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        st.cancelTimer();
                        //set Prog to > 32000000 in order to pur completed tasks on top
                    }
                };
                thr.start(); }

           else if(st.getCategory()==Task.Category.HOBB) {


                adapter_hobb.add( st );
                Calendar c = Calendar.getInstance();
                timeWhenCancelled = c.getTimeInMillis();
              saveItem(st);



                if (timenow >= st.getMcal_date()){
                    st.launchTimer();
                }
                else st.launchTimerwithDelay( del );

                thr = new Thread() {
                    @Override
                    public void run() {
                        while (st.getProgress() < 100 && st.getProgress() >= 0) {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    //t.setProgress( 20 );
                                    adapter_hobb.notifyItemChanged( adapter_hobb.index( st ) );
                                    recyclerView_hobb.setItemAnimator( null );
                                }
                            } );
                            try {
                                Thread.sleep( 100 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        st.cancelTimer();
                        //set Prog to > 32000000 in order to pur completed tasks on top
                    }
                };
                thr.start(); }

           else {


                adapter_adm.add( st );
                Calendar c = Calendar.getInstance();
                timeWhenCancelled = c.getTimeInMillis();
               saveItem(st);



                if (timenow >= st.getMcal_date()){
                    st.launchTimer();
                }
                else st.launchTimerwithDelay( del );

                thr = new Thread() {
                    @Override
                    public void run() {
                        while (st.getProgress() < 100 && st.getProgress() >= 0) {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    //t.setProgress( 20 );
                                    adapter_adm.notifyItemChanged( adapter_adm.index( st ) );
                                    recyclerView_adm.setItemAnimator( null );
                                }
                            } );
                            try {
                                Thread.sleep( 100 );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        st.cancelTimer();
                        //set Prog to > 32000000 in order to pur completed tasks on top
                    }
                };
                thr.start(); }




            } }


  @Override
    public void onResume() {
        super.onResume();
      if (adapter_fam.getItemCount()==0){
      //place in the right adapter
          getItemsFam();}
      if (adapter_prof.getItemCount()==0){
          //place in the right adapter
          getItemsProf();}

      if (adapter_educ.getItemCount()==0){
          //place in the right adapter
          getItemsEduc();}

      if (adapter_sport.getItemCount()==0){
          //place in the right adapter
          getItemsSport();}

      if (adapter_hobb.getItemCount()==0){
          //place in the right adapter
          getItemsHobb();}

      if (adapter_adm.getItemCount()==0){
          //place in the right adapter
          getItemsAdm();}
    }





    // Load stored Tasks

        private void getItemsFam() {
            try {
                Cursor res = myDb.getAllData();


                while (res.moveToNext()) {
                    String updname = res.getString( 1 );
                    String updcategory = res.getString( 2 );
                    String upddate = res.getString( 3 );
                    String updset_date = res.getString( 4 );
                    String updcal_date = res.getString( 5 );
                    String uprog_when_back = res.getString( 6 );
                    String utime_when_back = res.getString( 7 );


                    t = new Task( updname, Task.Category.valueOf( updcategory ), Task.FORMAT.parse( upddate ), Long.valueOf( updset_date ), Long.valueOf( updcal_date ), Integer.valueOf( uprog_when_back ), Long.valueOf( utime_when_back ) );

                    //classification by urgency
                        if (t.getCategory()==Task.Category.FAM){

                        adapter_fam.add( t );}
                        String nm = t.getName();
                        Task.Category cat = t.getCategory();
                        Date dt = t.getDate();
                        long cd = t.getMcal_date();

                        Calendar cac = Calendar.getInstance();
                        timecurr = cac.getTimeInMillis();
                        long del = t.getMcal_date() - timecurr;

                        int pb = t.getMprog_when_back();
                        long tb = t.getTime_when_back();

                        String s = nm + ITEM_SEP + "" + cat + ITEM_SEP + Task.FORMAT.format( dt ) + ITEM_SEP + String.valueOf( t.getMset_date() ) + ITEM_SEP + String.valueOf( cd ) + ITEM_SEP + String.valueOf( pb ) + ITEM_SEP + String.valueOf( tb );

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
                                          adapter_fam.notifyDataSetChanged();
                                            adapter_fam.notifyItemChanged( adapter_fam.index( t ) );
                                            recyclerView_fam.setItemAnimator( null );
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




    private void getItemsProf() {
        try {
            Cursor res = myDb.getAllData();


            while (res.moveToNext()) {
                String updname = res.getString( 1 );
                String updcategory = res.getString( 2 );
                String upddate = res.getString( 3 );
                String updset_date = res.getString( 4 );
                String updcal_date = res.getString( 5 );
                String uprog_when_back = res.getString( 6 );
                String utime_when_back = res.getString( 7 );


                t = new Task( updname, Task.Category.valueOf( updcategory ), Task.FORMAT.parse( upddate ), Long.valueOf( updset_date ), Long.valueOf( updcal_date ), Integer.valueOf( uprog_when_back ), Long.valueOf( utime_when_back ) );

                //classification by urgency

                if (t.getCategory()==Task.Category.PROF){
                adapter_prof.add( t );}
                String nm = t.getName();
                Task.Category cat = t.getCategory();
                Date dt = t.getDate();
                long cd = t.getMcal_date();

                Calendar cac = Calendar.getInstance();
                timecurr = cac.getTimeInMillis();
                long del = t.getMcal_date() - timecurr;

                int pb = t.getMprog_when_back();
                long tb = t.getTime_when_back();

                String s = nm + ITEM_SEP + "" + cat + ITEM_SEP + Task.FORMAT.format( dt ) + ITEM_SEP + String.valueOf( t.getMset_date() ) + ITEM_SEP + String.valueOf( cd ) + ITEM_SEP + String.valueOf( pb ) + ITEM_SEP + String.valueOf( tb );

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
                                    adapter_prof.notifyDataSetChanged();
                                    adapter_prof.notifyItemChanged( adapter_prof.index( t ) );
                                    recyclerView_prof.setItemAnimator( null );
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



    private void getItemsEduc() {
        try {
            Cursor res = myDb.getAllData();


            while (res.moveToNext()) {
                String updname = res.getString( 1 );
                String updcategory = res.getString( 2 );
                String upddate = res.getString( 3 );
                String updset_date = res.getString( 4 );
                String updcal_date = res.getString( 5 );
                String uprog_when_back = res.getString( 6 );
                String utime_when_back = res.getString( 7 );


                t = new Task( updname, Task.Category.valueOf( updcategory ), Task.FORMAT.parse( upddate ), Long.valueOf( updset_date ), Long.valueOf( updcal_date ), Integer.valueOf( uprog_when_back ), Long.valueOf( utime_when_back ) );

                //classification by urgency

                if (t.getCategory()==Task.Category.EDUC){
                    adapter_educ.add( t );}
                String nm = t.getName();
                Task.Category cat = t.getCategory();
                Date dt = t.getDate();
                long cd = t.getMcal_date();

                Calendar cac = Calendar.getInstance();
                timecurr = cac.getTimeInMillis();
                long del = t.getMcal_date() - timecurr;

                int pb = t.getMprog_when_back();
                long tb = t.getTime_when_back();

                String s = nm + ITEM_SEP + "" + cat + ITEM_SEP + Task.FORMAT.format( dt ) + ITEM_SEP + String.valueOf( t.getMset_date() ) + ITEM_SEP + String.valueOf( cd ) + ITEM_SEP + String.valueOf( pb ) + ITEM_SEP + String.valueOf( tb );

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
                                    adapter_educ.notifyDataSetChanged();
                                    adapter_educ.notifyItemChanged( adapter_educ.index( t ) );
                                    recyclerView_educ.setItemAnimator( null );
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

    private void getItemsSport() {
        try {
            Cursor res = myDb.getAllData();


            while (res.moveToNext()) {
                String updname = res.getString( 1 );
                String updcategory = res.getString( 2 );
                String upddate = res.getString( 3 );
                String updset_date = res.getString( 4 );
                String updcal_date = res.getString( 5 );
                String uprog_when_back = res.getString( 6 );
                String utime_when_back = res.getString( 7 );


                t = new Task( updname, Task.Category.valueOf( updcategory ), Task.FORMAT.parse( upddate ), Long.valueOf( updset_date ), Long.valueOf( updcal_date ), Integer.valueOf( uprog_when_back ), Long.valueOf( utime_when_back ) );

                //classification by urgency

                if (t.getCategory()==Task.Category.SPORT){
                    adapter_sport.add( t );}
                String nm = t.getName();
                Task.Category cat = t.getCategory();
                Date dt = t.getDate();
                long cd = t.getMcal_date();

                Calendar cac = Calendar.getInstance();
                timecurr = cac.getTimeInMillis();
                long del = t.getMcal_date() - timecurr;

                int pb = t.getMprog_when_back();
                long tb = t.getTime_when_back();

                String s = nm + ITEM_SEP + "" + cat + ITEM_SEP + Task.FORMAT.format( dt ) + ITEM_SEP + String.valueOf( t.getMset_date() ) + ITEM_SEP + String.valueOf( cd ) + ITEM_SEP + String.valueOf( pb ) + ITEM_SEP + String.valueOf( tb );

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
                                    adapter_sport.notifyDataSetChanged();
                                    adapter_sport.notifyItemChanged( adapter_sport.index( t ) );
                                    recyclerView_sport.setItemAnimator( null );
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

    private void getItemsHobb() {
        try {
            Cursor res = myDb.getAllData();


            while (res.moveToNext()) {
                String updname = res.getString( 1 );
                String updcategory = res.getString( 2 );
                String upddate = res.getString( 3 );
                String updset_date = res.getString( 4 );
                String updcal_date = res.getString( 5 );
                String uprog_when_back = res.getString( 6 );
                String utime_when_back = res.getString( 7 );


                t = new Task( updname, Task.Category.valueOf( updcategory ), Task.FORMAT.parse( upddate ), Long.valueOf( updset_date ), Long.valueOf( updcal_date ), Integer.valueOf( uprog_when_back ), Long.valueOf( utime_when_back ) );

                //classification by urgency

                if (t.getCategory()==Task.Category.HOBB){
                    adapter_hobb.add( t );}
                String nm = t.getName();
                Task.Category cat = t.getCategory();
                Date dt = t.getDate();
                long cd = t.getMcal_date();

                Calendar cac = Calendar.getInstance();
                timecurr = cac.getTimeInMillis();
                long del = t.getMcal_date() - timecurr;

                int pb = t.getMprog_when_back();
                long tb = t.getTime_when_back();

                String s = nm + ITEM_SEP + "" + cat + ITEM_SEP + Task.FORMAT.format( dt ) + ITEM_SEP + String.valueOf( t.getMset_date() ) + ITEM_SEP + String.valueOf( cd ) + ITEM_SEP + String.valueOf( pb ) + ITEM_SEP + String.valueOf( tb );

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
                                    adapter_hobb.notifyDataSetChanged();
                                    adapter_hobb.notifyItemChanged( adapter_hobb.index( t ) );
                                    recyclerView_hobb.setItemAnimator( null );
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

    private void getItemsAdm() {
        try {
            Cursor res = myDb.getAllData();


            while (res.moveToNext()) {
                String updname = res.getString( 1 );
                String updcategory = res.getString( 2 );
                String upddate = res.getString( 3 );
                String updset_date = res.getString( 4 );
                String updcal_date = res.getString( 5 );
                String uprog_when_back = res.getString( 6 );
                String utime_when_back = res.getString( 7 );


                t = new Task( updname, Task.Category.valueOf( updcategory ), Task.FORMAT.parse( upddate ), Long.valueOf( updset_date ), Long.valueOf( updcal_date ), Integer.valueOf( uprog_when_back ), Long.valueOf( utime_when_back ) );

                //classification by urgency

                if (t.getCategory()==Task.Category.OTHER){
                    adapter_adm.add( t );}
                String nm = t.getName();
                Task.Category cat = t.getCategory();
                Date dt = t.getDate();
                long cd = t.getMcal_date();

                Calendar cac = Calendar.getInstance();
                timecurr = cac.getTimeInMillis();
                long del = t.getMcal_date() - timecurr;

                int pb = t.getMprog_when_back();
                long tb = t.getTime_when_back();

                String s = nm + ITEM_SEP + "" + cat + ITEM_SEP + Task.FORMAT.format( dt ) + ITEM_SEP + String.valueOf( t.getMset_date() ) + ITEM_SEP + String.valueOf( cd ) + ITEM_SEP + String.valueOf( pb ) + ITEM_SEP + String.valueOf( tb );

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
                                    adapter_adm.notifyDataSetChanged();
                                    adapter_adm.notifyItemChanged( adapter_adm.index( t ) );
                                    recyclerView_adm.setItemAnimator( null );
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
            //Toast.makeText( MainActivity.this, String.valueOf(n.getMcal_date())+ITEM_SEP+ String.valueOf(n.getMset_date())+ITEM_SEP+ String.valueOf(progressWhenCancelled)+ITEM_SEP+String.valueOf(timeWhenCancelled), Toast.LENGTH_LONG ).show();

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
                                famadapter.getItem( position ).cancelTimer();
                                famadapter.deleteItem(position);
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
                                famadapter.deleteItem(position);
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

    public class SwipeToDelete_prof extends ItemTouchHelper.SimpleCallback {
        private TaskAdapter_prof profadapter;
        ColorDrawable background;

        public SwipeToDelete_prof(TaskAdapter_prof adapter) {
            super( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
            profadapter = adapter;
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
                                Task n = profadapter.getItem( position );
                                profadapter.getItem( position ).cancelTimer();
                                profadapter.deleteItem(position);
                                myDb.deleteItem(String.valueOf(n.getMcal_date()) );

                                dialog.cancel();
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                profadapter.notifyDataSetChanged();
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
                                profadapter.getItem( position ).cancelTimer();
                                profadapter.deleteItem(position);
                                dialog.cancel();
                                // Then you can remove this item from the adapter
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                profadapter.notifyDataSetChanged();
                                dialog.cancel();
                            }})
                        .create()
                        .show(); }
        }}

    public class SwipeToDelete_educ extends ItemTouchHelper.SimpleCallback {
        private TaskAdapter_educ educadapter;
        ColorDrawable background;

        public SwipeToDelete_educ(TaskAdapter_educ adapter) {
            super( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
            educadapter = adapter;
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
                                Task n = educadapter.getItem( position );
                                educadapter.getItem( position ).cancelTimer();
                                educadapter.deleteItem(position);
                                myDb.deleteItem(String.valueOf(n.getMcal_date()) );

                                dialog.cancel();
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                educadapter.notifyDataSetChanged();
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
                                educadapter.getItem( position ).cancelTimer();
                                educadapter.deleteItem(position);
                                dialog.cancel();
                                // Then you can remove this item from the adapter
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                educadapter.notifyDataSetChanged();
                                dialog.cancel();
                            }})
                        .create()
                        .show(); }
        }}

    public class SwipeToDelete_sport extends ItemTouchHelper.SimpleCallback {
        private TaskAdapter_sport sportadapter;
        ColorDrawable background;

        public SwipeToDelete_sport(TaskAdapter_sport adapter) {
            super( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
            sportadapter = adapter;
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
                                Task n = sportadapter.getItem( position );
                                sportadapter.getItem( position ).cancelTimer();
                                sportadapter.deleteItem(position);
                                myDb.deleteItem(String.valueOf(n.getMcal_date()) );

                                dialog.cancel();
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sportadapter.notifyDataSetChanged();
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
                                sportadapter.getItem( position ).cancelTimer();
                                sportadapter.deleteItem(position);
                                dialog.cancel();
                                // Then you can remove this item from the adapter
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sportadapter.notifyDataSetChanged();
                                dialog.cancel();
                            }})
                        .create()
                        .show(); }
        }}

    public class SwipeToDelete_hobb extends ItemTouchHelper.SimpleCallback {
        private TaskAdapter_hobb hobbadapter;
        ColorDrawable background;

        public SwipeToDelete_hobb(TaskAdapter_hobb adapter) {
            super( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
            hobbadapter = adapter;
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
                                Task n = hobbadapter.getItem( position );
                                hobbadapter.getItem( position ).cancelTimer();
                                hobbadapter.deleteItem(position);
                                myDb.deleteItem(String.valueOf(n.getMcal_date()) );

                                dialog.cancel();
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                hobbadapter.notifyDataSetChanged();
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
                                hobbadapter.getItem( position ).cancelTimer();
                                hobbadapter.deleteItem(position);
                                dialog.cancel();
                                // Then you can remove this item from the adapter
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                hobbadapter.notifyDataSetChanged();
                                dialog.cancel();
                            }})
                        .create()
                        .show(); }
        }}

    public class SwipeToDelete_adm extends ItemTouchHelper.SimpleCallback {
        private TaskAdapter_other admadapter;
        ColorDrawable background;

        public SwipeToDelete_adm(TaskAdapter_other adapter) {
            super( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
            admadapter = adapter;
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
                                Task n = admadapter.getItem( position );
                                admadapter.getItem( position ).cancelTimer();
                                admadapter.deleteItem(position);
                                myDb.deleteItem(String.valueOf(n.getMcal_date()) );

                                dialog.cancel();
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                admadapter.notifyDataSetChanged();
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
                                admadapter.getItem( position ).cancelTimer();
                                admadapter.deleteItem(position);
                                dialog.cancel();
                                // Then you can remove this item from the adapter
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                admadapter.notifyDataSetChanged();
                                dialog.cancel();
                            }})
                        .create()
                        .show(); }
        }}




}




