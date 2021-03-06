package com.example.mytasksapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.R;
import com.example.mytasksapp.data.MainViewModel;
import com.example.mytasksapp.pojo.MyList;
import com.example.mytasksapp.pojo.Todo;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListViewHolder> {
    private List<MyList> listItemModels;
    private List<Todo> todos;
    TaskAdapter tasksAdapter;
    TaskAdapter completedTaskAdapter;
    private MainViewModel viewModel;


    class ListViewHolder extends  RecyclerView.ViewHolder {
        private TextView textViewListTitle;
        private TextView textViewCompleted;
        private ImageView imageViewCompletedArrow;
        private RecyclerView recyclerViewTasks;
        private RecyclerView recyclerViewCompletedTasks;

        private boolean isVisible = true;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewListTitle = itemView.findViewById(R.id.textViewListTitle);
            this.recyclerViewTasks = itemView.findViewById(R.id.recyclerViewTasks);
            this.recyclerViewCompletedTasks = itemView.findViewById(R.id.recyclerViewCompletedTasks);
            this.textViewCompleted = itemView.findViewById(R.id.textViewCompleted);
            this.imageViewCompletedArrow = itemView.findViewById(R.id.imageViewCompletedArrow);
            textViewCompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isVisible) {
                        imageViewCompletedArrow.animate().rotation(180);
                        recyclerViewCompletedTasks.animate().alpha(0).scaleY(0).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                recyclerViewCompletedTasks.setVisibility(View.GONE);
                            }
                        });

                    }
                    else {
                        imageViewCompletedArrow.animate().rotation(0);
                        recyclerViewCompletedTasks.setVisibility(View.VISIBLE);
                        recyclerViewCompletedTasks.animate().scaleY(1).alpha(1);
                    }
                    isVisible = !isVisible;
                }
            });
        }
    }

    public ListItemAdapter() {
        this.listItemModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        MyList listItemModel = listItemModels.get(position);
        List<Todo> tasks = filterTasksByListId(listItemModel.getId());
        List<Todo> completedTasks = filterCompletedTasksByListId(listItemModel.getId());
        holder.textViewListTitle.setText(listItemModel.getTitle());
        holder.recyclerViewTasks.setLayoutManager(new LinearLayoutManager(holder.recyclerViewTasks.getContext(), LinearLayoutManager.VERTICAL, false));
        holder.recyclerViewCompletedTasks.setLayoutManager(new LinearLayoutManager(holder.recyclerViewTasks.getContext(),
                LinearLayoutManager.VERTICAL, false));
        tasksAdapter = new TaskAdapter();
        tasksAdapter.setViewModel(viewModel);
        completedTaskAdapter = new TaskAdapter();
        completedTaskAdapter.setViewModel(viewModel);
        tasksAdapter.setTaskItemModels(tasks);
        completedTaskAdapter.setTaskItemModels(completedTasks);
        holder.recyclerViewTasks.setAdapter(tasksAdapter);
        holder.recyclerViewCompletedTasks.setAdapter(completedTaskAdapter);
    }

    @Override
    public int getItemCount() {
        return listItemModels.size();
    }

    public void setListItemModels(List<MyList> listItemModels) {
        this.listItemModels = listItemModels;
        notifyDataSetChanged();
    }

    public void setTodos(List<Todo> todos) {
        if(tasksAdapter != null) tasksAdapter.clear();
        this.todos = todos;
        notifyDataSetChanged();
    }

    public List<Todo> filterTasksByListId(int listId) {
        List<Todo> result = new ArrayList<>();
        if(todos != null && todos.size() > 0) {
            for (Todo todo : todos) {
                if (todo.getListId() == listId && !todo.isChecked()) {
                    result.add(todo);
                }
            }
        }
        return result;
    }

    public List<Todo> filterCompletedTasksByListId(int listId) {
        List<Todo> result = new ArrayList<>();
        if(todos != null && todos.size() > 0) {
            for (Todo task : todos) {
                if (task.getListId() == listId && task.isChecked()) {
                    result.add(task);
                }
            }
        }
        return result;
    }

    public void setViewModel(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void clear() {
        this.listItemModels.clear();
        notifyDataSetChanged();
    }
}
