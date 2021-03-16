package com.example.mytasksapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mytasksapp.adapters.ListItemAdapter;
import com.example.mytasksapp.data.CategoryItem;
import com.example.mytasksapp.data.ListItemModel;
import com.example.mytasksapp.data.MainViewModel;
import com.example.mytasksapp.data.TaskItemModel;
import com.example.mytasksapp.pojo.MyList;
import com.example.mytasksapp.pojo.Todo;
import com.example.mytasksapp.utils.JSONUtils;
import com.example.mytasksapp.utils.NetworkUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMainScreen;
    private FloatingActionButton floatingActionButtonAddList;

    private MainViewModel viewModel;
    private ListItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                for (MyList myList : myLists) {
                    List<Todo> todos = myList.getTodos();
                    for (Todo todo: todos) {
                        Log.i("testrun", todo.getText());
                    }
                }
            }
        });
        viewModel.loadData();
    }

    public void onClickAddList(View view) {
        Intent intent = new Intent(this, ActivityAddTask.class);
        startActivity(intent);
    }


    private void convertItems(List<ListItemModel> listItemModels) {
        for (int i = 0; i < listItemModels.size(); i++) {
            boolean isChecked = i == 0;
            CategoryItem categoryItem = new CategoryItem(listItemModels.get(i).getId(), listItemModels.get(i).getTitle(), isChecked);
//            viewModel.insertCategory(categoryItem);
        }
    }

    public void onClickEditLists(View view) {
    }
}