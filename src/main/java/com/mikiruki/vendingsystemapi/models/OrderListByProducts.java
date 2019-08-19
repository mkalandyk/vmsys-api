package com.mikiruki.vendingsystemapi.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_list")
public class OrderListByProducts implements Serializable {

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer product_id;

    @Column(name = "machines", nullable = false)
    private String machines;

    @Column(name = "quantities", nullable = false)
    private String quantities;

    public OrderListByProducts() {
    }

    public OrderListByProducts(Integer product_id, String machines, String quantities) {
        this.product_id = product_id;
        this.machines = machines;
        this.quantities = quantities;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getMachines() {
        return machines;
    }

    public void setMachines(String machines) {
        this.machines = machines;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }
}
