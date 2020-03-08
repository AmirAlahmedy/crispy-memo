package com.example.reminder;

public class Reminder {

    private int id;
    private String content;
    private boolean important;

    public Reminder(int id, String content, boolean important) {

        this.id = id;
        this.content = content;
        this.important = important;
    }

    public Reminder(String content, boolean important) {

        this.content = content;
        this.important = important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isImportant() {
        return important;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
