package com.mikiruki.vendingsystemapi.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mikiruki.vendingsystemapi.utils.VendingMachineSerializer;

import java.util.HashMap;
import java.util.Map;

public class OrderListByProductsWithContent {

    private Product product;

    @JsonSerialize(keyUsing = VendingMachineSerializer.class)
    private Map<VendingMachine, Integer> machines;
    private int totalQuantity;

    public OrderListByProductsWithContent() {
        this.machines = new HashMap<>();
    }

    public static OrderListByProductsWithContent build() {
        return new OrderListByProductsWithContent();
    }

    public OrderListByProductsWithContent setProduct(Product product) {
        this.product = product;
        return this;
    }

    public OrderListByProductsWithContent setMachines(Map<VendingMachine, Integer> map) {
        this.machines = map;
        recalulateTotalQuantity();
        return this;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public Map<VendingMachine, Integer> getMachines() {
        return machines;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    private void recalulateTotalQuantity() {
        this.totalQuantity = 0;
        for(Integer quantity : machines.values())
            this.totalQuantity += quantity;
    }
}
