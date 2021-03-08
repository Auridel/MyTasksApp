package com.example.mytasksapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.R;
import com.example.mytasksapp.data.ListItemModel;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListViewHolder> {
    private List<ListItemModel> listItemModels;

    class ListViewHolder extends  RecyclerView.ViewHolder {
        private TextView textViewListTitle;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewListTitle = itemView.findViewById(R.id.textViewListTitle);
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
        ListItemModel listItemModel = listItemModels.get(position);
        holder.textViewListTitle.setText(listItemModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return listItemModels.size();
    }

    public void setListItemModels(List<ListItemModel> listItemModels) {
        this.listItemModels.addAll(listItemModels);
        notifyDataSetChanged();
    }

    public void clear() {
        this.listItemModels.clear();
        notifyDataSetChanged();
    }
}
