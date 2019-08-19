package com.mikiruki.vendingsystemapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    Integer productId;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    Float price;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.product", cascade=CascadeType.ALL, orphanRemoval = true)
    List<MachineContent> machineContent = new ArrayList<>();

    public Product() {
    }

    public Product(Integer productId, String name, Float price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<MachineContent> getMachineContent() {
        return machineContent;
    }

    public void setMachineContent(List<MachineContent> machineContent) {
        this.machineContent = machineContent;
    }
}
