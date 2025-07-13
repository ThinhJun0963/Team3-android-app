package com.example.prm392_group_project.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392_group_project.R;
import com.example.prm392_group_project.activities.UserEditActivity;
import com.example.prm392_group_project.models.AccountResponseDTO;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<AccountResponseDTO> users;
    private final OnUserActionListener onAction;

    public interface OnUserActionListener {
        void onBanToggle(AccountResponseDTO user);
    }

    public UserAdapter(List<AccountResponseDTO> users, OnUserActionListener onAction) {
        this.users = users;
        this.onAction = onAction;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_admin, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        AccountResponseDTO user = users.get(position);

        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvRole.setText("Vai trò: " + user.getRole().name());
        holder.tvStatus.setText(user.isBanned() ? "Đã bị cấm" : "Đang hoạt động");

        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(user.getAvatar())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.imgAvatar);
        } else {
            holder.imgAvatar.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.btnBanUnban.setText(user.isBanned() ? "Mở cấm" : "Cấm");
        holder.btnBanUnban.setBackgroundColor(holder.itemView.getContext().getColor(
                user.isBanned() ? android.R.color.holo_green_dark : android.R.color.holo_red_dark
        ));

        holder.btnBanUnban.setOnClickListener(v -> onAction.onBanToggle(user));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UserEditActivity.class);
            intent.putExtra("user_id", user.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvEmail, tvRole, tvStatus;
        Button btnBanUnban;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvUserName);
            tvEmail = itemView.findViewById(R.id.tvUserEmail);
            tvRole = itemView.findViewById(R.id.tvUserRole);
            tvStatus = itemView.findViewById(R.id.tvUserStatus);
            btnBanUnban = itemView.findViewById(R.id.btnUserAction);
        }
    }
}
