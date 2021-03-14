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
import com.example.mytasksapp.data.AddTaskResult;
import com.example.mytasksapp.data.CategoryItem;
import com.example.mytasksapp.data.ListItemModel;
import com.example.mytasksapp.data.MainViewModel;
import com.example.mytasksapp.data.Server;
import com.example.mytasksapp.data.TaskRequest;
import com.example.mytasksapp.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityAddTask extends AppCompatActivity {
    private ImageView imageViewBack;
    private ImageView imageViewSubmitTask;
    private EditText editTextTaskTitle;
    private List<CategoryItem> categoryItems = new ArrayList<>();
    private RecyclerView recyclerViewAddCategories;

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
            }
        });
    }

    public void onClickGoToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickSubmitTask(View view) {
        String text = editTextTaskTitle.getText().toString();
        Log.i("res123", text);
        if(!text.trim().isEmpty()) {
            int listId = categoryItemList.get(0).getId();
            JSONObject task = new JSONObject();
            try {
                task.put("list_id", listId);
                task.put("text", text);
                task.put("checked", false);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com") // Адрес сервера
                        .addConverterFactory(GsonConverterFactory.create()) // говорим ретрофиту что для сериализации необходимо использовать GSON
                        .build();

                Server service = retrofit.create(Server.class);
                Call<List<AddTaskResult>> call = service.addTask(new TaskRequest(listId, text));
                call.enqueue(new Callback<List<AddTaskResult>>() {
                    @Override
                    public void onResponse(Call<List<AddTaskResult>> call, Response<List<AddTaskResult>> response) {
                        if (response.isSuccessful()) {
                            // запрос выполнился успешно, сервер вернул Status 200
                        } else {
                            // сервер вернул ошибку
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AddTaskResult>> call, Throwable t) {
                        // ошибка во время выполнения запроса
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
