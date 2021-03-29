package com.example.mytasksapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.adapters.AddListAdapter;
import com.example.mytasksapp.data.MainViewModel;
import com.example.mytasksapp.pojo.MyList;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheetModal extends BottomSheetDialogFragment {
    private Bundle bundle;
    private MainViewModel viewModel;
    private RecyclerView recyclerViewLists;
    private Context context;

    private ConstraintLayout addList;

    public BottomSheetModal(Bundle bundle, MainViewModel viewModel, Context context) {
        this.bundle = bundle;
        this.viewModel = viewModel;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_list, container, false);
        addList = view.findViewById(R.id.constraintLayoutAddList);
        recyclerViewLists = view.findViewById(R.id.recyclerViewAddList);
        recyclerViewLists.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        AddListAdapter adapter = new AddListAdapter();
        viewModel.getAllLists().observe(this, new Observer<List<MyList>>() {
            @Override
            public void onChanged(List<MyList> myLists) {
                adapter.setMyListList(myLists);
            }
        });
        recyclerViewLists.setAdapter(adapter);
        if(bundle != null) {
        }
        return view;
    }
}
