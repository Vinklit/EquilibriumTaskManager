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
import android.util.Log;
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
    //milliseconds in one hour
    //final long current_time = c.getTimeInMillis();
   /* final long cmillis = current_time % 1000;
    final long csecond = (current_time/ 1000) % 60;
    final long cminute = (current_time / (1000 * 60)) % 60;
    final long chour = (current_time / (1000 * 60 * 60)) % 24;*/
    Timer timer;
    int i;


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

        final long scheduled_time = toDoItem.getDate().getTime();
        final long smillis = scheduled_time % 1000;
        final long ssecond = (scheduled_time / 1000) % 60;
        final long sminute = (scheduled_time / (1000 * 60)) % 60;
        final long shour = (scheduled_time / (1000 * 60 * 60)) % 24;
        final String aat= String.valueOf(scheduled_time);
        final double st =  Double.parseDouble(aat);

        final long when_scheduled = toDoItem.getSal_date();
        final long millis = when_scheduled % 1000;
        final long second = (when_scheduled / 1000) % 60;
        final long minute = (when_scheduled / (1000 * 60)) % 60;
        final long hour = (when_scheduled / (1000 * 60 * 60)) % 24;
        final String sst= String.valueOf(when_scheduled);
        final double ws =  Double.parseDouble(sst);

        final long current_time = toDoItem.getCal_date();
        final long cmillis = current_time % 1000;
        final long csecond = (current_time/ 1000) % 60;
        final long cminute = (current_time / (1000 * 60)) % 60;
        final long chour = (current_time / (1000 * 60 * 60)) % 24;
        final String cct= String.valueOf(current_time);
        final double ct =  Double.parseDouble(cct);

        final double a = ct-ws;//current- when scheduled
        final double b = st-ws; //scheduled - when scheduled

        final double prog = (a/b)*10000000;
        final int k = (int)prog;

        final long period = 100;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (i<1000000000){
                    holder.progressBar.setProgress(i);
                    i +=k;
                }else{
                    //closing the timer
                    timer.cancel();
                }
            }
        }, 0, period);

        holder.date.setText("prog"+k );

      /*  holder.timerz = new CountDownTimer(timerz, 100) {
            public void onTick(long millisUntilFinished) {

                holder.progressBar.setProgress((int)millisUntilFinished/1000);
                holder.date.setText("a"+a+"b"+b+"prog"+prog+"long"+timerz );
            }

            public void onFinish() {
                holder.progressBar.setProgress(10);
                holder.date.setText("a"+a+"b"+b+"prog"+prog+"long"+timerz);
            }
        }.start();*/


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


