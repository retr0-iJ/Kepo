package com.example.mobile_immanueljoseph_2301852215.util;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil.Callback;

import com.example.mobile_immanueljoseph_2301852215.model.TodoModel;

import java.util.ArrayList;

public class MyTodoDiffUtilCallback extends Callback {
    ArrayList<TodoModel> oldList;
    ArrayList<TodoModel> newList;

    public MyTodoDiffUtilCallback(ArrayList<TodoModel> oldList, ArrayList<TodoModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getTodo_id() == newList.get(newItemPosition).getTodo_id();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
