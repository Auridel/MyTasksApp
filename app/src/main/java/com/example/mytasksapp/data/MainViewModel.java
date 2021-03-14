package com.example.mytasksapp.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {
    private static MyTasksDatabase myTasksDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        myTasksDatabase = MyTasksDatabase.getInstance(getApplication());
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
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.listDao().deleteAllLists();
            }
        });
    }

    public void deleteList(ListItemModel listItemModel) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.listDao().deleteList(listItemModel);
            }
        });
    }

    public void insertList(ListItemModel listItemModel) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.listDao().insertList(listItemModel);
            }
        });
    }

    public void updateList(ListItemModel listItemModel) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.listDao().updateList(listItemModel);
            }
        });
    }

    private static class GetAllLists extends AsyncTask<Void, Void, LiveData<List<ListItemModel>>> {
        @Override
        protected LiveData<List<ListItemModel>> doInBackground(Void... voids) {
            LiveData<List<ListItemModel>> lists;
            lists = myTasksDatabase.listDao().getLists();
            return lists;
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
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.taskDao().deleteAllTasks();
            }
        });
    }

    public void deleteTask(TaskItemModel taskItemModel) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.taskDao().deleteTask(taskItemModel);
            }
        });
    }

    public void updateTask(TaskItemModel taskItemModel) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.taskDao().updateTask(taskItemModel);
            }
        });
    }

    public void insertTask(TaskItemModel taskItemModel) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.taskDao().insertTask(taskItemModel);
            }
        });
    }

    public LiveData<List<TaskItemModel>> getAllTasks() {
        try {
            return new GetAllTasks().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetTasksByListIdTask extends AsyncTask<Integer, Void,
            LiveData<List<TaskItemModel>>> {
        @Override
        protected LiveData<List<TaskItemModel>> doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return myTasksDatabase.taskDao().getTasksByListId(integers[0]);
            }
            return null;
        }
    }

    private static class GetAllTasks extends AsyncTask<Void, Void,
            LiveData<List<TaskItemModel>>> {
        @Override
        protected LiveData<List<TaskItemModel>> doInBackground(Void... voids) {
            LiveData<List<TaskItemModel>> tasks;
            tasks = myTasksDatabase.taskDao().getAllTasks();
            return tasks;
        }
    }

    public static LiveData<List<CategoryItem>> getCategories() {
        try {
            return new GetCategories().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteAllCategories() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.categoryDao().deleteAllCategories();
            }
        });
    }

    public static void insertCategory(CategoryItem categoryItem) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.categoryDao().insertCategory(categoryItem);
            }
        });
    }

    public static void deleteCategory(CategoryItem categoryItem) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.categoryDao().deleteCategory(categoryItem);
            }
        });
    }

    public static void updateCategory(CategoryItem categoryItem) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.categoryDao().updateCategory(categoryItem);
            }
        });
    }

    public static void checkCategory(CategoryItem categoryItem) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.categoryDao().uncheckCategories();
                myTasksDatabase.categoryDao().checkCategory(categoryItem.getId());
            }
        });
    }

    public static LiveData<List<CategoryItem>> getCheckedCategory(){
        try {
            return new GetCheckedCategoryTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetCategories extends AsyncTask<Void, Void,
            LiveData<List<CategoryItem>>> {
        @Override
        protected LiveData<List<CategoryItem>> doInBackground(Void... voids) {
            LiveData<List<CategoryItem>> categories;
            categories = myTasksDatabase.categoryDao().getAllCategories();
            return categories;
        }
    }

    private static class GetCheckedCategoryTask extends AsyncTask<Void, Void,
            LiveData<List<CategoryItem>>>{
        @Override
        protected LiveData<List<CategoryItem>> doInBackground(Void... voids) {
            LiveData<List<CategoryItem>> liveData;
            liveData = myTasksDatabase.categoryDao().getCheckegCategory();
            return liveData;
        }
    }
}
