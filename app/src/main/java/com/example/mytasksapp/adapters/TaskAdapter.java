package com.example.mytasksapp.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.MainActivity;
import com.example.mytasksapp.R;
import com.example.mytasksapp.data.MainViewModel;
import com.example.mytasksapp.data.TaskItemModel;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TaskItemModel> taskItemModels;
    private boolean isChecked = false;
    private TaskItemModel taskItemModel;
    private MainViewModel viewModel;

    public TaskAdapter() {
        this.taskItemModels = new ArrayList<>();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTaskTitle;
        private ImageView imageViewTaskIcon;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTaskTitle = itemView.findViewById(R.id.textViewTaskTitle);
            this.imageViewTaskIcon = itemView.findViewById(R.id.imageViewTaskIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskItemModel updatedTask = new TaskItemModel(taskItemModel.getId(), taskItemModel.getListId(),
                            taskItemModel.getText(), !taskItemModel.isChecked());
//                    viewModel.updateTask(updatedTask);
                }
            });
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
        taskItemModel = taskItemModels.get(position);
        holder.textViewTaskTitle.setText(taskItemModel.getText());
        isChecked = taskItemModel.isChecked();
        if(isChecked) {
            holder.imageViewTaskIcon.setImageResource(R.drawable.ic_check);
            holder.textViewTaskTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return taskItemModels.size();
    }

    public void setTaskItemModels(List<TaskItemModel> taskItemModels) {
        this.taskItemModels = taskItemModels;
        notifyDataSetChanged();
    }

    public void clear() {
        this.taskItemModels.clear();
        notifyDataSetChanged();
    }

    public void setViewModel(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
