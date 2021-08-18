package com.faceopen.camerabenchmark.model;

public class ItemModel {
    private String name;
    private int image;
    private int id;

    public ItemModel(String name, int imageId, int id) {
        this.name = name;
        this.image = imageId;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
