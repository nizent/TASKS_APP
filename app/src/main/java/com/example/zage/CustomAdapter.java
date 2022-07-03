package com.example.zage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zage.database.DatabaseAdapter;
import com.example.zage.model.Task;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Task> mDataSet;

    public CustomAdapter(List<Task> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(mDataSet.get(position).getTitle());
        viewHolder.getDescriptionTextViewTextView().setText(mDataSet.get(position).getDescription());
        viewHolder.getDateTextViewTextView().setText(mDataSet.get(position).getEndDate().toString());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView descriptionTextView;
        private final TextView dateTextView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {}
            });
            textView = (TextView) v.findViewById(R.id.textView);
            descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);
            dateTextView = (TextView) v.findViewById(R.id.dateTextView);
        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getDescriptionTextViewTextView() {
            return descriptionTextView;
        }
        public TextView getDateTextViewTextView() {
            return dateTextView;
        }
    }
}
