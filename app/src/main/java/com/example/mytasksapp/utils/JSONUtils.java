package com.example.mytasksapp.utils;

import com.example.mytasksapp.data.ListItemModel;
import com.example.mytasksapp.data.TaskItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LIST_ID = "list_id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TASKS = "todos";
    private static final String KEY_CHECKED = "checked";

    public static List<ListItemModel> getLists(JSONArray jsonArray) {
        List<ListItemModel> result = new ArrayList<>();
        if(jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObjectListData = jsonArray.getJSONObject(i);
                    int id = jsonObjectListData.getInt(KEY_ID);
                    String title = jsonObjectListData.getString(KEY_TITLE);
                    ListItemModel listItemModel = new ListItemModel(id, title);
                    result.add(listItemModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static ArrayList<TaskItemModel> getTasks(JSONArray jsonArray) {
        ArrayList<TaskItemModel> result = new ArrayList<>();
        if(jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObjectListData = jsonArray.getJSONObject(i);
                    JSONArray jsonArrayTasks = jsonObjectListData.getJSONArray(KEY_TASKS);
                    if(jsonArrayTasks != null && jsonArrayTasks.length() > 0) {
                        for (int j = 0; j < jsonArrayTasks.length(); j++) {
                            JSONObject jsonObjectTaskData = jsonArrayTasks.getJSONObject(j);
                            int id = jsonObjectTaskData.getInt(KEY_ID);
                            int listId = jsonObjectTaskData.getInt(KEY_LIST_ID);
                            String text = jsonObjectTaskData.getString(KEY_TEXT);
                            boolean checked = jsonObjectTaskData.getBoolean(KEY_CHECKED);
                            TaskItemModel taskItemModel = new TaskItemModel(id, listId, text, checked);
                            result.add(taskItemModel);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
