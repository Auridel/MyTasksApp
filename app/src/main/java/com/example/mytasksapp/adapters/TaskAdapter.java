package com.example.mytasksapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.R;
import com.example.mytasksapp.data.TaskItemModel;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    List<TaskItemModel> taskItemModels;

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTaskTitle;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTaskTitle = itemView.findViewById(R.id.textViewTaskTitle);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItemModel taskItemModel = taskItemModels.get(position);
        holder.textViewTaskTitle.setText(taskItemModel.getText());
    }

    @Override
    public int getItemCount() {
        return taskItemModels.size();
    }

    public void setTaskItemModels(List<TaskItemModel> taskItemModels) {
        this.taskItemModels = taskItemModels;
    }
}
