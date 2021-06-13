package com.example.mobile_immanueljoseph_2301852215.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.model.UserModel;
import com.example.mobile_immanueljoseph_2301852215.util.UserDiffCallback;

import java.util.ArrayList;

public class SearchUsersAdapter extends RecyclerView.Adapter<SearchUsersAdapter.SearchUsersHolder> {
    private ArrayList<UserModel> users;
    private OnItemClickListener itemClickListener;

    public SearchUsersAdapter(){
        this.users = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchUsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_layout, parent, false);

        return new SearchUsersHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUsersHolder holder, int position) {
        UserModel user = getUser(position);
        if(user != null){
            holder.tv_username.setText(user.getUsername());
            holder.tv_name.setText(user.getName());
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(ArrayList<UserModel> newUsers){
        UserDiffCallback diffCallback = new UserDiffCallback(users, newUsers);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        users.clear();
        users.addAll(newUsers);
        diffResult.dispatchUpdatesTo(this);
    }

    public UserModel getUser(int position){
        return this.users.get(position);
    }

    class SearchUsersHolder extends RecyclerView.ViewHolder{
        TextView tv_username, tv_name;

        public SearchUsersHolder(@NonNull View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_name = itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(itemClickListener != null && position != RecyclerView.NO_POSITION)
                        itemClickListener.onItemClick(users.get(position));
                }
            });
        }
    }

    public void setOnItemClickListener(SearchUsersAdapter.OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(UserModel user);
    }
}
