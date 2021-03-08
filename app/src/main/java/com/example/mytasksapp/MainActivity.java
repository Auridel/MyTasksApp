package com.example.mytasksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMainScreen;
    private RecyclerView recyclerViewTasks;
    private RecyclerView recyclerViewCompletedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewMainScreen = findViewById(R.id.recyclerViewMainScreen);
    }

    public void onClickAddList(View view) {
    }
}