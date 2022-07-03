package com.example.zage.model;
import java.time.LocalDateTime;

public class Task {
    private int id;
    private String title, description;
    private String startDate, endDate;
    private String importance;

    public Task(int id, String title, String description, String startDate,
                String endDate, String importance){
        this.id=id;
        this.title=title;
        this.description=description;
        this.startDate=startDate;
        this.endDate=endDate;
        this.importance=importance;
    }

    public String getDescription() {
        return description;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getImportance() {
        return importance;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}