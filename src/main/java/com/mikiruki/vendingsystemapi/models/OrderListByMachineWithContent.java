package com.mikiruki.vendingsystemapi.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mikiruki.vendingsystemapi.utils.ProductSerializer;

import java.util.HashMap;
import java.util.Map;

public class OrderListByMachineWithContent {

    private VendingMachine machine;

    @JsonSerialize(keyUsing = ProductSerializer.class)
    private Map<Product, Integer> products;
    private int totalQuantity;

    public OrderListByMachineWithContent() {
        this.products = new HashMap<>();
    }

    public static OrderListByMachineWithContent build() {
        return new OrderListByMachineWithContent();
    }

    public OrderListByMachineWithContent setMachine(VendingMachine machine) {
        this.machine = machine;
        return this;
    }

    public OrderListByMachineWithContent setProducts(Map<Product, Integer> map) {
        this.products = map;
        recalulateTotalQuantity();
        return this;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public VendingMachine getMachine() {
        return this.machine;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    private void recalulateTotalQuantity() {
        this.totalQuantity = 0;
        for(Integer quantity : products.values())
            this.totalQuantity += quantity;
    }
}
