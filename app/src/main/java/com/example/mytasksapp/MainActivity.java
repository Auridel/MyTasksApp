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
import com.example.mytasksapp.utils.JSONUtils;
import com.example.mytasksapp.utils.NetworkUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray> {
    private RecyclerView recyclerViewMainScreen;
    private FloatingActionButton floatingActionButtonAddList;

    private MainViewModel viewModel;
    private ListItemAdapter listAdapter;

    private boolean isLoading = false;
    private static final int LOADER_ID = 7346;
    private LoaderManager loaderManager;

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
        loaderManager = LoaderManager.getInstance(this);
        LiveData<List<ListItemModel>> listLiveData = viewModel.getAllLists();
        listLiveData.observe(this, new Observer<List<ListItemModel>>() {
            @Override
            public void onChanged(List<ListItemModel> listItemModels) {
                listAdapter.setListItemModels(listItemModels);
            }
        });
        LiveData<List<TaskItemModel>> taskLiveData = viewModel.getAllTasks();
        taskLiveData.observe(this, new Observer<List<TaskItemModel>>() {
            @Override
            public void onChanged(List<TaskItemModel> taskItemModels) {
                listAdapter.setTaskItemModels(taskItemModels);
            }
        });
        downloadData();
    }

    public void onClickAddList(View view) {
        Intent intent = new Intent(this, ActivityAddTask.class);
        startActivity(intent);
    }

    private void downloadData() {
        URL url = NetworkUtils.buildURLToLists();
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<JSONArray> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.DataLoader dataLoader = new NetworkUtils.DataLoader(this, args);
        dataLoader.setOnStartLoadingListener(new NetworkUtils.DataLoader.OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                isLoading = true;
            }
        });
        return dataLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONArray> loader, JSONArray data) {
        List<ListItemModel> listItemModels = JSONUtils.getLists(data);
        ArrayList<TaskItemModel> taskItemModels = JSONUtils.getTasks(data);
        //из-за выполнения в разных потоках возникают баги. переделать для выполнения в 1 потоке
        if (listItemModels != null && !listItemModels.isEmpty()) {
            viewModel.deleteAllLists();
            listAdapter.clear();
            for (ListItemModel listItemModel : listItemModels) {
                viewModel.insertList(listItemModel);
            }
            convertItems(listItemModels);
        }
        if (taskItemModels != null && !taskItemModels.isEmpty()) {
            viewModel.deleteAllTasks();
            for (TaskItemModel taskItemModel : taskItemModels) {
                viewModel.insertTask(taskItemModel);
            }
        }
        isLoading = false;
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONArray> loader) {

    }

    private void convertItems(List<ListItemModel> listItemModels) {
        for (int i = 0; i < listItemModels.size(); i++) {
            boolean isChecked = i == 0;
            CategoryItem categoryItem = new CategoryItem(listItemModels.get(i).getId(), listItemModels.get(i).getTitle(), isChecked);
            viewModel.insertCategory(categoryItem);
        }
    }

    public void onClickEditLists(View view) {
    }
}