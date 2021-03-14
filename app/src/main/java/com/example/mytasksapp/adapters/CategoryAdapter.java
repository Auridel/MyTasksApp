package com.example.mytasksapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.R;
import com.example.mytasksapp.data.CategoryItem;
import com.example.mytasksapp.data.ListItemModel;
import com.example.mytasksapp.data.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CategoryItem> categoryItems = new ArrayList<>();
    private boolean isChecked = false;
    private MainViewModel viewModel;

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCategoryTitle;
        private ImageView imageViewCategoryRadio;
        private ConstraintLayout constraintLayout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoryTitle = itemView.findViewById(R.id.textViewCategoryTitle);
            imageViewCategoryRadio = itemView.findViewById(R.id.imageViewCategoryRadio);
            constraintLayout = itemView.findViewById(R.id.constraintCategory);
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem categoryItem = categoryItems.get(position);
        holder.textViewCategoryTitle.setText(categoryItem.getTitle());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.checkCategory(categoryItem);
            }
        });
        isChecked = categoryItem.isChecked();
        if(isChecked) {
            holder.imageViewCategoryRadio.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
        } else {
            holder.imageViewCategoryRadio.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
        }
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public void setCategoryItems(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
        notifyDataSetChanged();
    }

    public void setViewModel(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
