package com.example.mytasksapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.R;
import com.example.mytasksapp.pojo.MyList;

import java.util.ArrayList;
import java.util.List;

public class AddListAdapter extends RecyclerView.Adapter<AddListAdapter.AddListViewHolder>{
    private List<MyList> myListList;

    public AddListAdapter() {
        this.myListList = new ArrayList<>();
    }

    class AddListViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ConstraintLayout constraintLayoutDeleteList;

        public AddListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            constraintLayoutDeleteList = itemView.findViewById(R.id.deleteListItem);
        }
    }

    @NonNull
    @Override
    public AddListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_list_item, parent, false);
        return new AddListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddListViewHolder holder, int position) {
        MyList list = myListList.get(position);
        holder.textViewTitle.setText(list.getTitle());
        holder.constraintLayoutDeleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("testrun", "clicked delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public void setMyListList(List<MyList> myListList) {
        this.myListList = myListList;
        notifyDataSetChanged();
    }
}
