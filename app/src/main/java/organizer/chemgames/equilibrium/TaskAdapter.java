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
    Context mContext;
    private ItemClickListener listener;


    public TaskAdapter(Context mContext, ArrayList<Task> data, ItemClickListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }





    // Create new views (invoked by the layout manager)
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskitem, null);


        // create ViewHolder

        final ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        itemLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        Task currentItem = (Task) getItem(position);
        viewHolder.itemName.setText(currentItem.getTaskname());
        viewHolder.progress.setText(""+currentItem.getProgress());
        viewHolder.runstatus.setText(""+currentItem.getRunstatus());
        viewHolder.progressBar.setProgress(currentItem.getProgress());
    }


    public Object getItem(int position) {
        return data.get(position); //returns the item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {

       TextView itemName;
       TextView progress;
        TextView runstatus;
       ProgressBar progressBar;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemName = (TextView)itemLayoutView.findViewById( R.id.title );
            progress = (TextView)itemLayoutView.findViewById( R.id.progress );
            runstatus = (TextView)itemLayoutView.findViewById( R.id.runstatus );
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



