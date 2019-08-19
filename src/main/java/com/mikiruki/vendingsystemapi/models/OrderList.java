package com.mikiruki.vendingsystemapi.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_list")
public class OrderList implements Serializable {

    @Id
    @Column(name = "machine_id", nullable = false)
    private Integer machineId;

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public OrderList() {
    }

    public OrderList(Integer machineId, Integer productId, Integer quantity) {
        this.machineId = machineId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
