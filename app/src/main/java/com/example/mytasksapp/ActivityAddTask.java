package com.example.mytasksapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.adapters.CategoryAdapter;
import com.example.mytasksapp.data.CategoryItem;
import com.example.mytasksapp.data.MainViewModel;
import com.example.mytasksapp.data.TodoPost;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddTask extends AppCompatActivity {
    private ImageView imageViewBack;
    private ImageView imageViewSubmitTask;
    private EditText editTextTaskTitle;
    private List<CategoryItem> categoryItems = new ArrayList<>();
    private RecyclerView recyclerViewAddCategories;
    private int checkedId;

    private List<CategoryItem> categoryItemList = new ArrayList<>();
    MainViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewSubmitTask = findViewById(R.id.imageViewSubmitTask);
        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(MainViewModel.class);
        recyclerViewAddCategories = findViewById(R.id.recyclerViewAddCategories);
        CategoryAdapter categoryAdapter = new CategoryAdapter();
        categoryAdapter.setViewModel(viewModel);
        recyclerViewAddCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewAddCategories.setAdapter(categoryAdapter);
        LiveData<List<CategoryItem>> liveData = viewModel.getCategories();
        liveData.observe(this, new Observer<List<CategoryItem>>() {
            @Override
            public void onChanged(List<CategoryItem> categoryItems) {
                categoryAdapter.setCategoryItems(categoryItems);
                categoryItemList = categoryItems;
                for (CategoryItem categoryItem : categoryItems) {
                    if (categoryItem.isChecked()) {
                        Log.i("testrun", categoryItem.getTitle());
                        checkedId = categoryItem.getId();
                    }
                }
            }
        });
    }

    public void onClickGoToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isLoaded", true);
        startActivity(intent);
    }

    public void onClickSubmitTask(View view) {
        Log.i("testrun", "clicked");
        String text = editTextTaskTitle.getText().toString();
        Log.i("testrun", Integer.toString(checkedId));
        if (checkedId != 0) {
            TodoPost todoPost = new TodoPost(checkedId, text);
            viewModel.postTodo(checkedId, todoPost);
            Intent intent = new Intent();
            intent.putExtra("isLoaded", true);
            startActivity(intent);
        }
    }
}
