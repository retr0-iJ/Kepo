package com.example.mobile_immanueljoseph_2301852215.model;

public class InsertUpdateModel {
    private String todo_id;
    private String title;
    private String description;

    public InsertUpdateModel(String todo_id, String title, String description) {
        this.todo_id = todo_id;
        this.title = title;
        this.description = description;
    }

    public String getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(String todo_id) {
        this.todo_id = todo_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUpdate(){
        return this.todo_id.equals("-1") ? false : true;
    }

    public int isDescValid(){
        if(this.description.length() > 100)
            return -1;
        return 1;
    }
}
