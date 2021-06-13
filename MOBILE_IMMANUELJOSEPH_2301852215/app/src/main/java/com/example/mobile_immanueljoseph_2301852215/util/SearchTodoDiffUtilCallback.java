package com.example.mobile_immanueljoseph_2301852215.util;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.mobile_immanueljoseph_2301852215.model.SearchTodoModel;

import java.util.ArrayList;

public class SearchTodoDiffUtilCallback extends DiffUtil.Callback {
    ArrayList<SearchTodoModel> oldList;
    ArrayList<SearchTodoModel> newList;

    public SearchTodoDiffUtilCallback(ArrayList<SearchTodoModel> oldList, ArrayList<SearchTodoModel> newList) {
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
        return oldList.get(oldItemPosition).getTodoId() == newList.get(newItemPosition).getTodoId();
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
