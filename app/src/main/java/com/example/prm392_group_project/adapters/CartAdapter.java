package com.example.prm392_group_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_group_project.R;
import com.example.prm392_group_project.models.CartItemDTO;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItemDTO> items;

    public CartAdapter(List<CartItemDTO> items) {
        this.items = items;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQuantity, tvPrice;

        public CartViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            tvPrice = view.findViewById(R.id.tvTotalPrice);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItemDTO item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvQuantity.setText("Số lượng: " + item.getQuantity());
        holder.tvPrice.setText("Thành tiền: " + item.getTotalPrice() + " VND");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
