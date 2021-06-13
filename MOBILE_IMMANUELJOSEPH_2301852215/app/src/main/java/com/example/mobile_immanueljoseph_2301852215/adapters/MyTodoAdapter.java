package com.example.mobile_immanueljoseph_2301852215.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.model.TodoModel;
import com.example.mobile_immanueljoseph_2301852215.util.DateUtils;
import com.example.mobile_immanueljoseph_2301852215.util.MyTodoDiffUtilCallback;

import java.util.ArrayList;

public class MyTodoAdapter extends RecyclerView.Adapter<MyTodoAdapter.MyTodoHolder> {

    private ArrayList<TodoModel> todos;
    private ArrayList<String> selectedTodos;
    private OnItemClickListener itemClickListener;
    private OnCheckBoxClickListener checkBoxClickListener;

    public MyTodoAdapter() {
        this.todos = new ArrayList<>();
        this.selectedTodos = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyTodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_todo_layout, parent,false);

        return new MyTodoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTodoHolder holder, int position) {
        TodoModel todo = getTodo(position);
        if(todo != null){
            holder.tvTodoTitle.setText(todo.getTitle());
            holder.tvLastEdited.setText("Last Edited : " + DateUtils.dateFromServer(todo.getLast_edited()));
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(ArrayList<TodoModel> newTodos){
        MyTodoDiffUtilCallback diffCallback = new MyTodoDiffUtilCallback(todos, newTodos);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        todos.clear();
        todos.addAll(newTodos);
        diffResult.dispatchUpdatesTo(this);
    }

    private TodoModel getTodo(int position){
        return todos.get(position);
    }

    class MyTodoHolder extends RecyclerView.ViewHolder{
        TextView tvTodoTitle, tvLastEdited;
        CheckBox cbTodo;

        public MyTodoHolder(@NonNull View itemView) {
            super(itemView);
            tvTodoTitle = itemView.findViewById(R.id.tv_todo_title);
            tvLastEdited = itemView.findViewById(R.id.tv_last_edited);
            cbTodo = itemView.findViewById(R.id.cb_todo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(itemClickListener != null && position != RecyclerView.NO_POSITION)
                        itemClickListener.onItemClick(todos.get(position).getTodo_id());
                }
            });

            cbTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(checkBoxClickListener != null && position != RecyclerView.NO_POSITION)
                        checkBoxClickListener.onCheckBoxClick(todos.get(position).getTodo_id(), cbTodo.isChecked());
                }
            });
        }
    }

    public void addSelectedTodos(String todo_id){
        this.selectedTodos.add(todo_id);
    }

    public void removeSelectedTodo(String todo_id){
        this.selectedTodos.remove(todo_id);
    }

    public void clearSelectedTodos(){
        this.selectedTodos.clear();
    }

    public int getSelectedTodosCount(){
        return selectedTodos.size();
    }

    public ArrayList<String> getSelectedTodos() {
        return (ArrayList<String>) selectedTodos.clone();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener listener){
        this.checkBoxClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(String todo_id);
    }

    public interface OnCheckBoxClickListener{
        void onCheckBoxClick(String todo_id, boolean isChecked);
    }
}
