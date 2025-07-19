package com.example.prm392_group_project.adapters;

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
import com.example.prm392_group_project.models.Product;
import com.example.prm392_group_project.utils.CartManager;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    // Constructor
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    // ViewHolder class
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice, tvStock, tvCategory;
        Button btnAddToCart; // ✅ thêm dòng này

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStock = itemView.findViewById(R.id.tvStock);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart); // ✅ thêm dòng này
        }
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = productList.get(position);

        holder.tvName.setText(p.getName());
        holder.tvPrice.setText("Giá: " + p.getPrice() + " VND");
        holder.tvStock.setText("Kho: " + p.getStock());
        holder.tvCategory.setText("Danh mục: " + (p.getCategory() != null ? p.getCategory().getName() : "Không có"));

        // Load hình ảnh bằng Glide
        Glide.with(holder.itemView.getContext())
                .load(p.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery) // ảnh mặc định
                .error(android.R.drawable.ic_delete)             // ảnh khi lỗi
                .into(holder.imgProduct);
        holder.btnAddToCart.setOnClickListener(v -> {
            // Tạo CartItemDTO từ sản phẩm đang hiển thị
            CartItemDTO item = new CartItemDTO(
                    p.getId(),
                    p.getName(),
                    1,
                    p.getPrice().doubleValue(),
                    p.getImageUrl()

            );

            // Thêm vào giỏ
            CartManager.getInstance().addToCart(item);

            // Thông báo
            Toast.makeText(v.getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
