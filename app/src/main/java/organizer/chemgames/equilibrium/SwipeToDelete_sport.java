package organizer.chemgames.equilibrium;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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
    }




}