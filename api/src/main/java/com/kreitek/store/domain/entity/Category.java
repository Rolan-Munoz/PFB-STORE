package com.kreitek.store.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySequence")
    private Long id;

    @Column(length = 100, nullable = false)
    @Size(min = 3, max = 100) //Para decir el tamaño minimo y maximo que permite nuestra base de datos, asi evitamos que se creen vacios
    private String name;

    @Column(length = 2000)
    private String description;

    @Lob      //esto es necesario para gestionar binarios
    private byte[] image;

    public Category() {    // lo dejamos vacio para que se pueda autogenerar

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
