package organizer.chemgames.equilibrium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends Activity {

    //TODO: make a list of recyclerviews and give a possibility to edit/delete categories (future)

    //TODO: swipe to delete (left) or reschedule (right)- dialog box. Reschedule: dialog box with time only: change category, start and end time.
    //Example: if daily task: tomorrow or schedule, weekly: next week or schedule

    //TODO: sort by treemap

    //TODO : if category not selected, make it mandatory

    //TODO: consommation de RAM en temps réel

    //TODO: make fragments with daily, weekly, monthly, full overview of progress

    //TODO DESIGN:
    // TODO - Floating action button (coursera)
    // TODO - Expandable areas




    private static final int REQUEST = 0;

    TaskAdapter_fam adapter_fam;
    TaskAdapter_prof adapter_prof;
    TaskAdapter_educ adapter_educ;
    TaskAdapter_sport adapter_sport;
    TaskAdapter_hobb adapter_hobb;
    TaskAdapter_other adapter_other;
    FloatingActionButton add;
    Thread  thr;
    RecyclerView recyclerView_fam;
    RecyclerView recyclerView_prof;
    RecyclerView recyclerView_educ;
    RecyclerView recyclerView_sport;
    RecyclerView recyclerView_hobb;
    ConstraintLayout test;
    ConstraintLayout test2;

    int a;
    int b;
    int i;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        test = (ConstraintLayout)findViewById( R.id.expendfam );
        test2 = (ConstraintLayout) findViewById( R.id.collapsefam );

        recyclerView_fam = (RecyclerView) findViewById(R.id.recyclerView_fam);
        recyclerView_fam.setLayoutManager(new LinearLayoutManager(this));

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        a = (displaymetrics.heightPixels * 70) / 100;
        b= (displaymetrics.heightPixels * 12) / 100;

        recyclerView_prof = (RecyclerView) findViewById(R.id.recyclerView_prof);
        recyclerView_prof.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_educ = (RecyclerView) findViewById(R.id.recyclerView_educ);
        recyclerView_educ.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_sport = (RecyclerView) findViewById(R.id.recyclerView_sport);
        recyclerView_sport.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_hobb = (RecyclerView) findViewById(R.id.recyclerView_hobb);
        recyclerView_hobb.setLayoutManager(new LinearLayoutManager(this));

        adapter_fam = new TaskAdapter_fam(this, new TaskAdapter_fam.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
                }}  ) ;


        recyclerView_fam.setAdapter(adapter_fam);
        ItemTouchHelper itemTouchHelperFam = new ItemTouchHelper( new SwipeToDelete_fam( adapter_fam ) );
        itemTouchHelperFam.attachToRecyclerView( recyclerView_fam );
        test.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_fam.getLayoutParams().height = a;
                adapter_fam.notifyDataSetChanged();
                test.setVisibility( View.INVISIBLE);
                test2.setVisibility( View.VISIBLE );


            }
        } );

        test2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView_fam.getLayoutParams().height = b;
                adapter_fam.notifyDataSetChanged();
                test2.setVisibility( View.INVISIBLE);
                test.setVisibility( View.VISIBLE );
            }

        } );



        adapter_prof = new TaskAdapter_prof(this, new TaskAdapter_prof.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );




                 }}  ) ;
        recyclerView_prof.setAdapter(adapter_prof);
        ItemTouchHelper itemTouchHelperProf = new ItemTouchHelper( new SwipeToDelete_prof( adapter_prof ) );
        itemTouchHelperProf.attachToRecyclerView( recyclerView_prof );

        adapter_educ = new TaskAdapter_educ(this, new TaskAdapter_educ.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) ); }}  ) ;
        recyclerView_educ.setAdapter(adapter_educ);
        ItemTouchHelper itemTouchHelperEduc = new ItemTouchHelper( new SwipeToDelete_educ( adapter_educ ) );
        itemTouchHelperEduc.attachToRecyclerView( recyclerView_educ );

        adapter_sport = new TaskAdapter_sport(this, new TaskAdapter_sport.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) ); }}  ) ;
        recyclerView_sport.setAdapter(adapter_sport);
        ItemTouchHelper itemTouchHelperSport = new ItemTouchHelper( new SwipeToDelete_sport( adapter_sport ) );
        itemTouchHelperSport.attachToRecyclerView( recyclerView_sport );

        adapter_hobb = new TaskAdapter_hobb(this, new TaskAdapter_hobb.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                //edit task
                v.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) ); }}  ) ;
        recyclerView_hobb.setAdapter(adapter_hobb);
        ItemTouchHelper itemTouchHelperHobb = new ItemTouchHelper( new SwipeToDelete_hobb( adapter_hobb ) );
        itemTouchHelperHobb.attachToRecyclerView( recyclerView_hobb );




        add = (FloatingActionButton)findViewById( R.id.addtask );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mAddNewToDoIntent = new Intent( MainActivity.this, AddTask.class );
                startActivityForResult( mAddNewToDoIntent, REQUEST );
            }} );
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST){
            if (resultCode == RESULT_OK){
               final Task t = new Task(intent);
                Calendar cc = Calendar.getInstance();
                long timenow = cc.getTimeInMillis();
                long del = t.getMcal_date()-timenow;

               //if else statement goes here
               if(t.getCategory()==Task.Category.FAM) {
                   adapter_fam.add( t );

         /* if (adapter_fam.getItemCount()>1){
                        i = recyclerView_fam.getHeight();
                       recyclerView_fam.getLayoutParams().height = i;
                       adapter_fam.notifyDataSetChanged();
                       }*/

            if (timenow >= t.getMcal_date()){
                t.launchTimer();
            }
            else t.launchTimerwithDelay( del );

                   thr = new Thread() {
                       @Override
                       public void run() {
                           while (t.getProgress() < 100 && t.getProgress() >= 0) {
                               runOnUiThread( new Runnable() {
                                   @Override
                                   public void run() {
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
                       }
                   };
                   thr.start();




               }

                else if(t.getCategory()==Task.Category.PROF) {
                    adapter_prof.add( t );

                   if (adapter_prof.getItemCount()>2){
                       i = recyclerView_prof.getHeight();
                       recyclerView_prof.getLayoutParams().height = i;
                       adapter_prof.notifyDataSetChanged();


                       test.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               recyclerView_prof.getLayoutParams().height = a;
                               adapter_prof.notifyDataSetChanged();

                           }
                       } );

                       test2.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {

                               recyclerView_prof.getLayoutParams().height = i;
                               adapter_prof.notifyDataSetChanged();
                           }

                       } );

                   }

                   if (timenow >= t.getMcal_date()){
                       t.launchTimer();
                   }
                   else t.launchTimerwithDelay( del );
                    thr = new Thread() {
                        @Override
                        public void run() {
                            while (t.getProgress() < 100 && t.getProgress() >= 0) {
                                runOnUiThread( new Runnable() {
                                    @Override
                                    public void run() {
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
                        }
                    };
                    thr.start();
                }

               else if(t.getCategory()==Task.Category.EDUC) {
                   adapter_educ.add( t );

                   if (adapter_educ.getItemCount()>2){
                       i = recyclerView_educ.getHeight();
                       recyclerView_educ.getLayoutParams().height = i;
                       adapter_educ.notifyDataSetChanged();


                       test.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               recyclerView_educ.getLayoutParams().height = a;
                               adapter_educ.notifyDataSetChanged();

                           }
                       } );

                       test2.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {

                               recyclerView_educ.getLayoutParams().height = i;
                               adapter_educ.notifyDataSetChanged();
                           }

                       } );

                   }
                   if (timenow >= t.getMcal_date()){
                       t.launchTimer();
                   }
                   else t.launchTimerwithDelay( del );
                   thr = new Thread() {
                       @Override
                       public void run() {
                           while (t.getProgress() < 100 && t.getProgress() >= 0) {
                               runOnUiThread( new Runnable() {
                                   @Override
                                   public void run() {
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
                       }
                   };
                   thr.start();
               }

               else if(t.getCategory()==Task.Category.SPORT) {
                   adapter_sport.add( t );

                   if (adapter_sport.getItemCount()>2){
                       i = recyclerView_sport.getHeight();
                       recyclerView_sport.getLayoutParams().height = i;
                       adapter_sport.notifyDataSetChanged();


                       test.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               recyclerView_sport.getLayoutParams().height = a;
                               adapter_sport.notifyDataSetChanged();

                           }
                       } );

                       test2.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {

                               recyclerView_sport.getLayoutParams().height = i;
                               adapter_sport.notifyDataSetChanged();
                           }

                       } );

                   }

                   if (timenow >= t.getMcal_date()){
                       t.launchTimer();
                   }
                   else t.launchTimerwithDelay( del );
                   thr = new Thread() {
                       @Override
                       public void run() {
                           while (t.getProgress() < 100 && t.getProgress() >= 0) {
                               runOnUiThread( new Runnable() {
                                   @Override
                                   public void run() {
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
                       }
                   };
                   thr.start();
               }

               else if(t.getCategory()==Task.Category.HOBB) {
                   adapter_hobb.add( t );
                   if (adapter_hobb.getItemCount()>2){
                       i = recyclerView_hobb.getHeight();
                       recyclerView_hobb.getLayoutParams().height = i;
                       adapter_hobb.notifyDataSetChanged();


                       test.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               recyclerView_hobb.getLayoutParams().height = a;
                               adapter_hobb.notifyDataSetChanged();

                           }
                       } );

                       test2.setOnClickListener( new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {

                               recyclerView_hobb.getLayoutParams().height = i;
                               adapter_hobb.notifyDataSetChanged();
                           }

                       } );

                   }

                   if (timenow >= t.getMcal_date()){
                       t.launchTimer();
                   }
                   else t.launchTimerwithDelay( del );
                   thr = new Thread() {
                       @Override
                       public void run() {
                           while (t.getProgress() < 100 && t.getProgress() >= 0) {
                               runOnUiThread( new Runnable() {
                                   @Override
                                   public void run() {
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
                       }
                   };
                   thr.start();
               }




            } } }











}




