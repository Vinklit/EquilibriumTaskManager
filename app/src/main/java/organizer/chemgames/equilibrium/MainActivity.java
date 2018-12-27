package organizer.chemgames.equilibrium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class MainActivity extends Activity {

    //TODO: consommation de RAM en temps réel

    //TODO: Floating action button (coursera)

    //TODO: swipe to delete (left) or reschedule (right) with dialog box

    //TODO: sort by treemap

    //TODO: make fragments with daily, weekly, monthly, full overview of progress

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
    RecyclerView recyclerView_other;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

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
        recyclerView_other = (RecyclerView) findViewById(R.id.recyclerView_other);
        recyclerView_other.setLayoutManager(new LinearLayoutManager(this));

        adapter_fam = new TaskAdapter_fam(this, new TaskAdapter_fam.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                adapter_fam.getItem( position ).cancelTimer();
                adapter_fam.deleteItem (position); }}  ) ;
        recyclerView_fam.setAdapter(adapter_fam);

        adapter_prof = new TaskAdapter_prof(this, new TaskAdapter_prof.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                adapter_prof.getItem( position ).cancelTimer();
                adapter_prof.deleteItem (position); }}  ) ;
        recyclerView_prof.setAdapter(adapter_prof);

        adapter_educ = new TaskAdapter_educ(this, new TaskAdapter_educ.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                adapter_educ.getItem( position ).cancelTimer();
                adapter_educ.deleteItem (position); }}  ) ;
        recyclerView_educ.setAdapter(adapter_educ);

        adapter_sport = new TaskAdapter_sport(this, new TaskAdapter_sport.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                adapter_sport.getItem( position ).cancelTimer();
                adapter_sport.deleteItem (position); }}  ) ;
        recyclerView_sport.setAdapter(adapter_sport);

        adapter_hobb = new TaskAdapter_hobb(this, new TaskAdapter_hobb.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                adapter_hobb.getItem( position ).cancelTimer();
                adapter_hobb.deleteItem (position); }}  ) ;
        recyclerView_hobb.setAdapter(adapter_hobb);

        adapter_other = new TaskAdapter_other(this, new TaskAdapter_other.ItemClickListener(){

            @Override
            public void onItemClick(View v, int position) {
                adapter_other.getItem( position ).cancelTimer();
                adapter_other.deleteItem (position); }}  ) ;
        recyclerView_other.setAdapter(adapter_other);




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

               //if else statement goes here
               if(t.getCategory()==Task.Category.FAM) {
                   adapter_fam.add( t );
                   t.launchTimer();
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
                    t.launchTimer();
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
                   t.launchTimer();
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
                   t.launchTimer();
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
                   t.launchTimer();
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

               else if(t.getCategory()==Task.Category.OTHER) {
                   adapter_other.add( t );
                   t.launchTimer();
                   thr = new Thread() {
                       @Override
                       public void run() {
                           while (t.getProgress() < 100 && t.getProgress() >= 0) {
                               runOnUiThread( new Runnable() {
                                   @Override
                                   public void run() {
                                       adapter_other.notifyItemChanged( adapter_other.index( t ) );
                                       recyclerView_other.setItemAnimator( null );
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

               else  {
                   adapter_other.add( t );
                   t.launchTimer();
                   thr = new Thread() {
                       @Override
                       public void run() {
                           while (t.getProgress() < 100 && t.getProgress() >= 0) {
                               runOnUiThread( new Runnable() {
                                   @Override
                                   public void run() {
                                       adapter_other.notifyItemChanged( adapter_other.index( t ) );
                                       recyclerView_other.setItemAnimator( null );
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


            //if else statement ends here


            } } }




    @Override
    public void onResume() {
        super.onResume();
        }

    @Override
    protected void onPause() {
        super.onPause(); }






}




