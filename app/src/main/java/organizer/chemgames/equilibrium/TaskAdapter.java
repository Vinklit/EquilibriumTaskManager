package organizer.chemgames.equilibrium;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> data = new ArrayList<Task>();
    private LayoutInflater mInflater;
    private ItemClickListener listener;


    TaskAdapter(Context context, ItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    public TaskAdapter(MainActivity context) {
    }





    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.taskitem, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        Task currentItem = data.get(position);
        viewHolder.itemName.setText(currentItem.getTaskname());
        viewHolder.progress.setText(""+currentItem.getProgress());
        viewHolder.progressBar.setProgress(currentItem.getProgress());
        viewHolder.date.setText(""+currentItem.getDate());
    }


    public Task getItem(int position) {
        return data.get(position); //returns the item at the specified position
    }

    public void add(Task item) {
        data.add(item);
        notifyDataSetChanged();
    }


    public void deleteItem(int position) {
        data.remove(position);
        notifyItemRemoved(position); }


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
            super(itemLayoutView);
            itemName = (TextView)itemLayoutView.findViewById( R.id.title );
            progress = (TextView)itemLayoutView.findViewById( R.id.progress );
            date = (TextView)itemLayoutView.findViewById( R.id.runstatus );
            progressBar = (ProgressBar)itemLayoutView.findViewById( R.id.progressbar );

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



