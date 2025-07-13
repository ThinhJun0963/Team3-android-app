package com.example.prm392_group_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_group_project.R;
import com.example.prm392_group_project.models.ProductCategoryDTO;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<ProductCategoryDTO> categories;
    private final OnCategoryEditListener editListener;
    private final OnCategoryDeleteListener deleteListener;

    public interface OnCategoryEditListener {
        void onEdit(ProductCategoryDTO category);
    }

    public interface OnCategoryDeleteListener {
        void onDelete(ProductCategoryDTO category);
    }

    public CategoryAdapter(List<ProductCategoryDTO> categories,
                           OnCategoryEditListener editListener,
                           OnCategoryDeleteListener deleteListener) {
        this.categories = categories;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        ProductCategoryDTO category = categories.get(position);
        holder.tvName.setText(category.getName());
        holder.btnEdit.setOnClickListener(v -> editListener.onEdit(category));
        holder.btnDelete.setOnClickListener(v -> deleteListener.onDelete(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnEdit, btnDelete;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCategoryName);
            btnEdit = itemView.findViewById(R.id.btnEditCategory);
            btnDelete = itemView.findViewById(R.id.btnDeleteCategory);
        }
    }
}
