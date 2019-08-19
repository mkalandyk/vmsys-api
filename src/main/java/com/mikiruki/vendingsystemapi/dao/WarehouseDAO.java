package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.Warehouse;

public interface WarehouseDAO {

    public Warehouse findById(Integer id);
    public boolean updateAddress(String address);

}
