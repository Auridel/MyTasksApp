package com.example.mytasksapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<ListItemModel>> listLiveData;
    private LiveData<List<TaskItemModel>> taskLiveData;

    private static TaskDatabase taskDatabase;
    private static ListDatabase listDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        taskDatabase = TaskDatabase.getInstance(getApplication());
        listDatabase = ListDatabase.getInstance(getApplication());
        listLiveData = listDatabase.listDao().getLists();
        taskLiveData = taskDatabase.taskDao().getAllTasks();
    }

    public LiveData<List<ListItemModel>> getAllLists() {
        try {
            return new GetAllLists().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllLists() {
        try {
            new DeleteAllListsTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteList(ListItemModel listItemModel) {
        try {
            new DeleteListTask().execute(listItemModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertList(ListItemModel listItemModel) {
        try {
            new InsertListTask().execute(listItemModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateList(ListItemModel listItemModel) {
        try {
            new UpdateListTask().execute(listItemModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class GetAllLists extends AsyncTask<Void, Void, LiveData<List<ListItemModel>>>{
        @Override
        protected LiveData<List<ListItemModel>> doInBackground(Void... voids) {
            LiveData<List<ListItemModel>> lists;
            lists = listDatabase.listDao().getLists();
            return lists;
        }
    }

    private static class DeleteAllListsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            listDatabase.listDao().deleteAllLists();
            return null;
        }
    }

    private static class DeleteListTask extends AsyncTask<ListItemModel, Void, Void> {
        @Override
        protected Void doInBackground(ListItemModel... listItemModels) {
            if (listItemModels != null && listItemModels.length > 0) {
                listDatabase.listDao().deleteList(listItemModels[0]);
            }
            return null;
        }
    }

    private static class InsertListTask extends AsyncTask<ListItemModel, Void, Void> {
        @Override
        protected Void doInBackground(ListItemModel... listItemModels) {
            if (listItemModels != null && listItemModels.length > 0) {
                listDatabase.listDao().insertList(listItemModels[0]);
            }
            return null;
        }
    }

    private static class UpdateListTask extends AsyncTask<ListItemModel, Void, Void> {
        @Override
        protected Void doInBackground(ListItemModel... listItemModels) {
            if (listItemModels != null && listItemModels.length > 0) {
                listDatabase.listDao().updateList(listItemModels[0]);
            }
            return null;
        }
    }

    public LiveData<List<TaskItemModel>> getTasksByListId(int listId) {
        try {
            return new GetTasksByListIdTask().execute(listId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllTasks() {
        try {
            new DeleteAllListsTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(TaskItemModel taskItemModel) {
        new DeleteTaskTask().execute(taskItemModel);
    }

    public void updateTask(TaskItemModel taskItemModel) {
        try {
            new UpdateTaskTask().execute(taskItemModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertTask(TaskItemModel taskItemModel) {
        try {
            new InsertTaskTask().execute(taskItemModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class GetTasksByListIdTask extends AsyncTask<Integer, Void,
            LiveData<List<TaskItemModel>>> {
        @Override
        protected LiveData<List<TaskItemModel>> doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return taskDatabase.taskDao().getTasksByListId(integers[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTasksTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            taskDatabase.taskDao().deleteAllTasks();
            return null;
        }
    }

    private static class DeleteTaskTask extends AsyncTask<TaskItemModel, Void, Void> {
        @Override
        protected Void doInBackground(TaskItemModel... taskItemModels) {
            if(taskItemModels != null && taskItemModels.length > 0) {
                taskDatabase.taskDao().deleteTask(taskItemModels[0]);
            }
            return null;
        }
    }

    private static class UpdateTaskTask extends AsyncTask<TaskItemModel, Void, Void> {
        @Override
        protected Void doInBackground(TaskItemModel... taskItemModels) {
            if(taskItemModels != null && taskItemModels.length > 0) {
                taskDatabase.taskDao().updateTask(taskItemModels[0]);
            }
            return null;
        }
    }

    private static class InsertTaskTask extends AsyncTask<TaskItemModel, Void, Void> {
        @Override
        protected Void doInBackground(TaskItemModel... taskItemModels) {
            if(taskItemModels != null && taskItemModels.length > 0) {
                taskDatabase.taskDao().insertTask(taskItemModels[0]);
            }
            return null;
        }
    }
}
