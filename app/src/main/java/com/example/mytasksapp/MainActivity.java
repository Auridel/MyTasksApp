package com.example.mytasksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mytasksapp.adapters.ListItemAdapter;
import com.example.mytasksapp.data.CategoryItem;
import com.example.mytasksapp.data.MainViewModel;
import com.example.mytasksapp.pojo.MyList;
import com.example.mytasksapp.pojo.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMainScreen;
    private FloatingActionButton floatingActionButtonAddList;
    private boolean isLoaded = false;

    private MainViewModel viewModel;
    private ListItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if(intent.hasExtra("isLoaded")) {
            boolean fromIntent = intent.getBooleanExtra("isLoaded", false);
            isLoaded = fromIntent;
        }
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(MainViewModel.class);
        recyclerViewMainScreen = findViewById(R.id.recyclerViewMainScreen);
        floatingActionButtonAddList = findViewById(R.id.floatingActionButton);
        floatingActionButtonAddList.setColorFilter(Color.WHITE);
        recyclerViewMainScreen.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listAdapter = new ListItemAdapter();
        listAdapter.setViewModel(viewModel);
        recyclerViewMainScreen.setAdapter(listAdapter);
        viewModel.getAllLists().observe(this, new Observer<List<MyList>>() {
            @Override
            public void onChanged(List<MyList> myLists) {
                listAdapter.setListItemModels(myLists);
            }
        });
        viewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                listAdapter.setTodos(todos);
            }
        });
        if(!isLoaded) {
            Log.i("testrun", "load");
            viewModel.loadData();
        }
    }

    public void onClickAddList(View view) {
        Intent intent = new Intent(this, ActivityAddTask.class);
        startActivity(intent);
    }

    public void onClickEditLists(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("hello", "world");
        BottomSheetModal bottomSheetModal = new BottomSheetModal(bundle, viewModel, this);
        bottomSheetModal.show(getSupportFragmentManager(), "BottomSheetModal");
    }
}