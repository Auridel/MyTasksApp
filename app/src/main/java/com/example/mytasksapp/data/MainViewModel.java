package com.example.mytasksapp.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mytasksapp.api.ApiFactory;
import com.example.mytasksapp.api.ApiService;
import com.example.mytasksapp.pojo.MyList;
import com.example.mytasksapp.pojo.Todo;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private static MyTasksDatabase myTasksDatabase;
    private LiveData<List<MyList>> listLiveData;
    private LiveData<List<Todo>> todoLiveData;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        myTasksDatabase = MyTasksDatabase.getInstance(getApplication());
        listLiveData = myTasksDatabase.listDao().getLists();
        todoLiveData = myTasksDatabase.todoDao().getAllTodos();
    }

    public LiveData<List<MyList>> getAllLists() {
        try {
            return new GetAllLists().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<MyList>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<List<Todo>> getTodoLiveData() {
        return todoLiveData;
    }

    public void postTodo(int id, TodoPost todoPost) {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        Call<Todo> call = apiService.postTodo(id, todoPost);
        call.enqueue(new Callback<Todo>() {
                    @Override
                    public void onResponse(Call<Todo> call, Response<Todo> response) {
                        Log.i("testrun", response.toString());
                        insertTodo(response.body());
                    }

                    @Override
                    public void onFailure(Call<Todo> call, Throwable t) {
                        Log.i("testrun", t.getMessage());
                    }
                });
    }

    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        Disposable disposable = apiService.getLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MyList>>() {
                    @Override
                    public void accept(List<MyList> myLists) throws Exception {
                        for(int i = 0; i< myLists.size(); i++) {
                            insertList(myLists.get(i));
                            boolean isChecked = (i == 0);
                            CategoryItem categoryItem = new CategoryItem(myLists.get(i).getId(), myLists.get(i).getTitle(),
                                    isChecked);
                            insertCategory(categoryItem);
                            List<Todo> todos = myLists.get(i).getTodos();
                            if(todos != null && todos.size() > 0) {
                                for (Todo todo : todos) {
                                    insertTodo(todo);
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(throwable != null) {
                            Toast.makeText(getApplication(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("testrun", throwable.getMessage());

                        }
                    }
                });
        compositeDisposable.add(disposable);
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

    public void insertAllLists(List<MyList> myLists) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.listDao().insertAllLists(myLists);
            }
        });
    }

    public void deleteList(MyList myList) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.listDao().deleteList(myList);
            }
        });
    }


    public void insertList(MyList myList) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.listDao().insertList(myList);
            }
        });
    }

    public void updateList(MyList myList) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.listDao().updateList(myList);
            }
        });
    }

    private static class GetAllLists extends AsyncTask<Void, Void, LiveData<List<MyList>>> {
        @Override
        protected LiveData<List<MyList>> doInBackground(Void... voids) {
            LiveData<List<MyList>> mylists;
            mylists = myTasksDatabase.listDao().getLists();
            return mylists;
        }
    }

    public LiveData<List<Todo>> getTodoByListId(int listId) {
        try {
            return new GetTodosByListIdTask().execute(listId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
//
    public void deleteAllTodos() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.todoDao().deleteAllTodos();
            }
        });
    }
//
    public void deleteTodo(Todo todo) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.todoDao().deleteTodo(todo);
            }
        });
    }
//
    public void updateTodo(Todo todo) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.todoDao().updateTodo(todo);
            }
        });
    }
//
    public void insertTodo(Todo todo) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myTasksDatabase.todoDao().insertTodo(todo);
            }
        });
    }
//
    public LiveData<List<Todo>> getAllTodos() {
        try {
            return new GetAllTodos().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
//
    private static class GetTodosByListIdTask extends AsyncTask<Integer, Void,
            LiveData<List<Todo>>> {
        @Override
        protected LiveData<List<Todo>> doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return myTasksDatabase.todoDao().getTasksByListId(integers[0]);
            }
            return null;
        }
    }
//
    private static class GetAllTodos extends AsyncTask<Void, Void,
            LiveData<List<Todo>>> {
        @Override
        protected LiveData<List<Todo>> doInBackground(Void... voids) {
            LiveData<List<Todo>> tasks;
            tasks = myTasksDatabase.todoDao().getAllTodos();
            return tasks;
        }
    }
//
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

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
