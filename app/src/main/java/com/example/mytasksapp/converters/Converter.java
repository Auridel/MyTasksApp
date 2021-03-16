package com.example.mytasksapp.converters;

import androidx.room.TypeConverter;

import com.example.mytasksapp.pojo.Todo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public String listOfTodosToString(List<Todo> todos) {
        return new Gson().toJson(todos);
    }

    @TypeConverter
    public List<Todo> stringToListTodos(String todosAsString) {
        Gson gson = new Gson();
        ArrayList objects = gson.fromJson(todosAsString, ArrayList.class);
        ArrayList<Todo> todos = new ArrayList<>();
        for (Object obj : objects) {
            todos.add(gson.fromJson(obj.toString(), Todo.class));
        }
        return todos;
    }
}
