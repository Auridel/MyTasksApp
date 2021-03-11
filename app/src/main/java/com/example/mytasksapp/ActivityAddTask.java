package com.example.mytasksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytasksapp.data.CategoryItem;

import java.util.ArrayList;

public class ActivityAddTask extends AppCompatActivity {
    private ImageView imageViewBack;
    private ImageView imageViewSubmitTask;
    private EditText editTextTaskTitle;
    private ArrayList<CategoryItem> categoryItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewSubmitTask = findViewById(R.id.imageViewSubmitTask);
        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
    }

    public void onClickGoToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickSubmitTask(View view) {
    }
}
