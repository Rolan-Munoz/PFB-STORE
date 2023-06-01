package com.kreitek.store.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemSequence")
    private Long id;

    @Column(length = 100, nullable = false)
    @Size(min = 3, max = 100)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    @Positive //para decirle que el precio tiene que ser positivo este decorador viene de la libreria validation
    private Double price;

    @Lob
    private byte[] image;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false) //todos los articulos tienen que tener su categoria con su id
    private Category category;  // en este caso no queremos que category tenga una lista con todos los items

    public Item() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
