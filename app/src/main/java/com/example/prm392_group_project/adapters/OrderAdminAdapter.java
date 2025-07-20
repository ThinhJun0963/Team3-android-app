package com.example.prm392_group_project.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_group_project.R;
import com.example.prm392_group_project.activities.AdminOrderManagementActivity;
import com.example.prm392_group_project.models.OrderResponseDTO;

import java.util.List;

public class OrderAdminAdapter extends RecyclerView.Adapter<OrderAdminAdapter.OrderViewHolder> {

    private final List<OrderResponseDTO> orders;
    private final AdminOrderManagementActivity context;

    public OrderAdminAdapter(List<OrderResponseDTO> orders, AdminOrderManagementActivity context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_admin, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderResponseDTO order = orders.get(position);
        holder.tvOrderId.setText("Mã đơn: #" + order.getId());
        holder.tvOrderUser.setText("User ID: " + order.getUserId());
        holder.tvOrderStatus.setText("Trạng thái: " + order.getStatus().name());
        holder.tvOrderPrice.setText("Tổng tiền: " + order.getTotalPrice() + "đ");
        holder.tvOrderDate.setText("Ngày tạo: " + order.getCreatedAt().toString().replace("T", " "));

        holder.btnUpdateStatus.setOnClickListener(v -> showStatusDialog(order));
    }

    private void showStatusDialog(OrderResponseDTO order) {
        String[] statuses = {"PENDING", "PROCESSING", "COMPLETED", "CANCELLED"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cập nhật trạng thái đơn #" + order.getId());
        builder.setItems(statuses, (dialog, which) -> {
            String selectedStatus = statuses[which];
            context.updateOrderStatus(order.getId(), selectedStatus);
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderUser, tvOrderStatus, tvOrderPrice, tvOrderDate;
        Button btnUpdateStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderUser = itemView.findViewById(R.id.tvOrderUser);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderPrice = itemView.findViewById(R.id.tvOrderPrice);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            btnUpdateStatus = itemView.findViewById(R.id.btnUpdateStatus);
        }
    }
}


