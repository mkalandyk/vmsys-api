package com.mikiruki.vendingsystemapi.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_list")
public class OrderListByMachine implements Serializable {

    @Id
    @Column(name = "machine_id", nullable = false)
    private Integer machine_id;

    @Column(name = "products", nullable = false)
    private String products;

    @Column(name = "quantities", nullable = false)
    private String quantities;

    public OrderListByMachine() {
    }

    public OrderListByMachine(Integer machine_id, String products, String quantities) {
        this.machine_id = machine_id;
        this.products = products;
        this.quantities = quantities;
    }

    public Integer getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(Integer machine_id) {
        this.machine_id = machine_id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }
}
