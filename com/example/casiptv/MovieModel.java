package com.example.casiptv;

public class MovieModel {
    private String name;
    private String streamIcon;
    private String category;

    public MovieModel() {}

    public MovieModel(String name, String streamIcon, String category) {
        this.name = name;
        this.streamIcon = streamIcon;
        this.category = category;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStreamIcon() { return streamIcon; }
    public void setStreamIcon(String streamIcon) { this.streamIcon = streamIcon; }

    public String getCategory() {
        return (category == null || category.isEmpty()) ? "Popular Series" : category;
    }
    public void setCategory(String category) { this.category = category; }
}