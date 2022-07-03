package com.example.zage.taskTouchHelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zage.RecyclerViewFragment;

public class TaskTouchHelperCallback extends ItemTouchHelper.Callback {
    private final RecyclerViewFragment mAdapter;

    public TaskTouchHelperCallback(RecyclerViewFragment mAdapter){
        this.mAdapter = mAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = 0;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction==ItemTouchHelper.START){
            mAdapter.onItemDismiss(viewHolder.getBindingAdapterPosition());
        }
    }
}