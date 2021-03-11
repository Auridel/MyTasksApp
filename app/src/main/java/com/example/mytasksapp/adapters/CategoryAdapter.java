package com.example.mytasksapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasksapp.R;
import com.example.mytasksapp.data.CategoryItem;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<CategoryItem> categoryItems;
    private boolean isChecked = false;

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCategoryTitle;
        private ImageView imageViewCategoryRadio;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItems = new ArrayList<>();
            textViewCategoryTitle = itemView.findViewById(R.id.textViewCategoryTitle);
            imageViewCategoryRadio = itemView.findViewById(R.id.imageViewCategoryRadio);
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
}
