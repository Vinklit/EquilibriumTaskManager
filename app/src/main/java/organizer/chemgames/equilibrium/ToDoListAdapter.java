package organizer.chemgames.equilibrium;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.*;


public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private List<Task> mData = new ArrayList<Task>();

    Calendar c = Calendar.getInstance();
    private LayoutInflater mInflater;
    private ItemClickListener listener;

    // data is passed into the constructor
    ToDoListAdapter(Context context, ItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;

    }

    public ToDoListAdapter(TaskManagerEquilibrium context, ToDoListAdapter2.ItemClickListener itemClickListener) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
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

        if (holder.timerz != null) {
            holder.timerz.cancel();
        }
        long timer = toDoItem.getDate().getTime();
        long timer2 = c.getTimeInMillis();
        final long timerz = (timer-timer2)/100000;
        holder.timerz = new CountDownTimer(timerz, 1000) {
            public void onTick(long millisUntilFinished) {
                holder.progressBar.setProgress((int)millisUntilFinished/1000);
                holder.date.setText(""+(int)millisUntilFinished/1000);
            }

            public void onFinish() {
                holder.progressBar.setProgress(10);
                holder.date.setText("");
            }
        }.start();


       /* int progress = toDoItem.getProgress();
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(holder.progressBar, "progress", 0, progress);
        progressAnimator.setDuration(1000);
        progressAnimator.start();*/

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView title;
        CheckBox status;
        TextView priority;
        TextView date;
        ProgressBar progressBar;
        CountDownTimer timerz;

        ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titleView);
            status = (CheckBox) view.findViewById(R.id.statusCheckBox);
            priority = (TextView) view.findViewById(R.id.priorityView);
            date = (TextView) view.findViewById(R.id.dateView);
            progressBar = (ProgressBar)view.findViewById( R.id.progressbar );

        }
    }


    public interface ItemClickListener {
        public void onItemClick(View v, int position);
    }

}


