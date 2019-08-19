package com.mikiruki.vendingsystemapi.models;

import javax.persistence.*;

@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    Integer warehouseId;

    @Column(name = "address")
    String address;

    public Warehouse() {
    }

    public Warehouse(Integer warehouseId, String address) {
        this.warehouseId = warehouseId;
        this.address = address;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
