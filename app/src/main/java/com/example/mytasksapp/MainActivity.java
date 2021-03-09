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
import android.os.Bundle;
import android.view.View;

import com.example.mytasksapp.adapters.ListItemAdapter;
import com.example.mytasksapp.data.ListItemModel;
import com.example.mytasksapp.data.MainViewModel;
import com.example.mytasksapp.data.TaskItemModel;
import com.example.mytasksapp.utils.JSONUtils;
import com.example.mytasksapp.utils.NetworkUtils;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray> {
    private RecyclerView recyclerViewMainScreen;
    private RecyclerView recyclerViewTasks;
    private RecyclerView recyclerViewCompletedTasks;

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
        recyclerViewMainScreen.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listAdapter = new ListItemAdapter();
        listAdapter.setViewModel(viewModel);
        recyclerViewMainScreen.setAdapter(listAdapter);
        loaderManager = LoaderManager.getInstance(this);
        downloadData();
        LiveData<List<ListItemModel>> listLiveData = viewModel.getAllLists();
        listLiveData.observe(this, new Observer<List<ListItemModel>>() {
            @Override
            public void onChanged(List<ListItemModel> listItemModels) {
                listAdapter.setListItemModels(listItemModels);
            }
        });
//        LiveData<List<TaskItemModel>> taskLiveData = viewModel.get  observer
    }

    public void onClickAddList(View view) {
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
        ArrayList<ListItemModel> listItemModels = JSONUtils.getLists(data);
        ArrayList<TaskItemModel> taskItemModels = JSONUtils.getTasks(data);
        if (listItemModels != null && !listItemModels.isEmpty()) {
            viewModel.deleteAllLists();
            listAdapter.clear();
            for (ListItemModel listItemModel : listItemModels) {
                viewModel.insertList(listItemModel);
            }
            listAdapter.setListItemModels(listItemModels);
        }
        if (taskItemModels != null && !taskItemModels.isEmpty()) {
            viewModel.deleteAllTasks();
            for (TaskItemModel taskItemModel : taskItemModels) {
                viewModel.insertTask(taskItemModel);
            }
            listAdapter.setTaskItemModels(taskItemModels);
        }
        isLoading = false;
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONArray> loader) {

    }

    public MainViewModel getViewModel() {
        return viewModel;
    }
}