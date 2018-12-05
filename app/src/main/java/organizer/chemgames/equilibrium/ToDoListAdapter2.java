package organizer.chemgames.equilibrium;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ToDoListAdapter2 extends RecyclerView.Adapter<ToDoListAdapter2.ViewHolder> {

    private List<Task> mData = new ArrayList<Task>();
    private LayoutInflater mInflater;
    private ItemClickListener listener;

    // data is passed into the constructor
    ToDoListAdapter2(Context context, ItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;

    }

    public void add(Task item) {
        mData.add(item);
        notifyDataSetChanged(); }


    public void deleteItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged(); }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int pos) {
        return pos; }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.task_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Task toDoItem = mData.get(position);
        holder.title.setText(toDoItem.getTitle());
        holder.status.setChecked(toDoItem.getStatus().equals(Task.Status.DONE));
        holder.status.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked)   {
                    toDoItem.setStatus(Task.Status.DONE);
                }
                else    {
                    toDoItem.setStatus(Task.Status.NOTDONE);
                } }});
        holder.priority.setText(toDoItem.getPriority().toString());
        holder.date.setText(Task.FORMAT.format(toDoItem.getDate()));
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView title;
        CheckBox status;
        TextView priority;
        TextView date;

        ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titleView);
            status = (CheckBox) view.findViewById(R.id.statusCheckBox);
            priority = (TextView) view.findViewById(R.id.priorityView);
            date = (TextView) view.findViewById(R.id.dateView);
        }
    }


    public interface ItemClickListener {
        public void onItemClick(View v, int position);
    }

}


