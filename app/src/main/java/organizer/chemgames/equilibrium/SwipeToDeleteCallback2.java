package organizer.chemgames.equilibrium;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SwipeToDeleteCallback2 extends ItemTouchHelper.SimpleCallback {
    private ToDoListAdapter2 mAdapter2;
   ColorDrawable background;

    public SwipeToDeleteCallback2(ToDoListAdapter2 adapter2) {
        super( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
        mAdapter2 = adapter2;
        background = new ColorDrawable(Color.BLUE);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter2.deleteItem(position);
    }




}