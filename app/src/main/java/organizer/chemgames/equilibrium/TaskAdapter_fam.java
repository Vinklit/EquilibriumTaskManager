package organizer.chemgames.equilibrium;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class TaskAdapter_fam extends RecyclerView.Adapter<TaskAdapter_fam.ViewHolder> {
    private List<Task> data= new ArrayList<Task>();
    private LayoutInflater mInflater;
    private ItemClickListener listener;
    int parent_height;
    ViewHolder viewHolder;



    TaskAdapter_fam(Context context, ItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.taskitem, parent, false);
       viewHolder = new ViewHolder(view);


       // if(some condition){
//inflate layout 1
       // }else{
//inflate layout 2
       // }


/* if (data.size() > 2) {
          ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
          parent_height = parent.getHeight();
          params.height = parent_height; //height recycleviewer
          parent.setLayoutParams( params );
       }*/




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Task currentItem = data.get(position);
        viewHolder.itemName.setText(currentItem.getTaskname());
        if (currentItem.getProgress()<99 && currentItem.getProg()>0) {
            viewHolder.progress.setText( "" + currentItem.getProgress() );
            viewHolder.progressBar.setProgress(currentItem.getProgress());
        } else  {viewHolder.progress.setText( "completed" + 100 );
            viewHolder.progressBar.setProgress(100);
        }
        viewHolder.date.setText(Task.FORMAT.format(currentItem.getDate()));



   /* if (data.size() > 2) {
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            parent_height =  viewHolder.itemView.getHeight() ;
            params.height = parent_height; //height recycleviewer
            viewHolder.itemView.setLayoutParams( params );
     }*/

    }

    public void clear() {

        data.clear();
        notifyDataSetChanged();

    }


    public Task getItem(int position) {
        return data.get(position);
    }

    public void add(Task item) {
        data.add(item);

        int pos = index( item );
        notifyDataSetChanged();
        notifyItemChanged( pos );
        Collections.sort(data, new Comparator<Task>() {
            @Override

            // 1. Sort depending on name, can also be by task type (daily, weekly, monthly...) or priority
           /* public int compare(Task t1, Task t2)
            { return  t1.getTaskname().compareTo(t2.getTaskname()); }*/

           // 2. Sort by "urgency"
            public int compare(Task t1, Task t2)
            {   int i1 = (int)t1.getProg();
                int i2 = (int)t2.getProg();
                return ((Integer)i1).compareTo(i2); }
        });
    }


    public int setAdapterHeight(){
        return parent_height;
    }


    public void deleteItem(int position) {
        data.remove( position );

        notifyItemRemoved(position);
    }


    public int index (Task item) {
       int i =  data.indexOf( item );
       return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView progress;
        TextView date;
        ProgressBar progressBar;


        public ViewHolder(View itemLayoutView) {
            super( itemLayoutView );
            itemName = (TextView) itemLayoutView.findViewById( R.id.title );
            progress = (TextView) itemLayoutView.findViewById( R.id.progress );
            date = (TextView) itemLayoutView.findViewById( R.id.runstatus );
            progressBar = (ProgressBar) itemLayoutView.findViewById( R.id.progressbar );

        }

    }
    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener {
        public void onItemClick(View v, int position);
    }





}






