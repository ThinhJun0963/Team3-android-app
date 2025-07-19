package com.example.prm392_group_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_group_project.R;
import com.example.prm392_group_project.models.CartItemDTO;
import com.example.prm392_group_project.utils.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItemDTO> items;
    private final Context context;
    private final Runnable onCartUpdated;

    public CartAdapter(Context context, List<CartItemDTO> items, Runnable onCartUpdated) {
        this.context = context;
        this.items = items;
        this.onCartUpdated = onCartUpdated;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvQuantity, tvPrice;
        Button btnRemove;

        public CartViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.imgProduct);
            tvName = view.findViewById(R.id.tvName);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            tvPrice = view.findViewById(R.id.tvTotalPrice);
            btnRemove = view.findViewById(R.id.btnRemove);
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

        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(holder.imgProduct);

        holder.btnRemove.setOnClickListener(v -> {
            CartManager.getInstance().removeFromCart(item.getProductId());

            int index = holder.getAdapterPosition();
            items.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, items.size());

            Toast.makeText(context, "Đã xóa " + item.getName(), Toast.LENGTH_SHORT).show();

            // Gọi callback để cập nhật tổng tiền
            onCartUpdated.run();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ✅ Cập nhật lại danh sách sản phẩm khi cần
    public void updateItems(List<CartItemDTO> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }
}
