package com.example.zage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zage.database.DatabaseAdapter;
import com.example.zage.model.Task;
import com.example.zage.taskTouchHelper.TaskTouchHelperCallback;

import java.util.List;

public class RecyclerViewFragment extends Fragment {
    protected List<Task> mDataset;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected CustomAdapter mAdapter;
    private DatabaseAdapter dbAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DatabaseAdapter(this.getContext());
        initDataSet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ItemTouchHelper.Callback callback = new TaskTouchHelperCallback(this);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void initDataSet(){
        mDataset = dbAdapter.getTaskRows();
    }

    public void onItemDismiss(int position) {
        dbAdapter.remove(mDataset.get(position).getId());
        mDataset.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}