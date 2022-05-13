package com.example.projectwork;

public class InformationModel {

    String Name;
    String Description;
    String image;

    public InformationModel() {
    }

    public InformationModel(String name, String description, String image) {
        Name = name;
        Description = description;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
