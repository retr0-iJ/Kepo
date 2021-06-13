package com.example.mobile_immanueljoseph_2301852215.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.model.SearchTodoModel;
import com.example.mobile_immanueljoseph_2301852215.util.DateUtils;
import com.example.mobile_immanueljoseph_2301852215.util.SearchTodoDiffUtilCallback;

import java.util.ArrayList;

public class SearchTodosAdapter extends RecyclerView.Adapter<SearchTodosAdapter.SearchTodosHolder> {
    private ArrayList<SearchTodoModel> todos;
    private OnItemClickListener itemClickListener;

    public SearchTodosAdapter() {
        this.todos = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchTodosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_todo_layout, parent, false);

        return new SearchTodosHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTodosHolder holder, int position) {
        SearchTodoModel todo = getTodo(position);
        if(todo != null){
            holder.tvTodoTitle.setText(todo.getTitle());
            holder.tvLastEdited.setText("Last Edited : " + DateUtils.dateFromServer(todo.getLastEdited()));
            holder.tvUsername.setText(todo.getUsername());
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(ArrayList<SearchTodoModel> newTodos){
        SearchTodoDiffUtilCallback diffCallback = new SearchTodoDiffUtilCallback(todos, newTodos);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        todos.clear();
        todos.addAll(newTodos);
        diffResult.dispatchUpdatesTo(this);
    }

    public SearchTodoModel getTodo(int position){
        return this.todos.get(position);
    }

    class SearchTodosHolder extends RecyclerView.ViewHolder{
        TextView tvTodoTitle, tvLastEdited, tvUsername;

        public SearchTodosHolder(@NonNull View itemView) {
            super(itemView);
            tvTodoTitle = itemView.findViewById(R.id.tv_todo_title);
            tvLastEdited = itemView.findViewById(R.id.tv_last_edited);
            tvUsername = itemView.findViewById(R.id.tv_username);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(itemClickListener != null && position != RecyclerView.NO_POSITION)
                        itemClickListener.onItemClick(todos.get(position));
                }
            });
        }

    }

    public void setOnItemClickListener(SearchTodosAdapter.OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(SearchTodoModel todos);
    }
}
