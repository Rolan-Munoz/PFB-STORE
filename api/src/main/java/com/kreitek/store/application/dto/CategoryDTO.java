package com.kreitek.store.application.dto;



import java.io.Serializable;

public class CategoryDTO implements Serializable {

    private Long id;
    private String name;
    private String description;
    private byte[] image;


    // si queremos que entre un DTO como un request body en una peticion de endpoint hay que crear un constructor vacio


    public CategoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
