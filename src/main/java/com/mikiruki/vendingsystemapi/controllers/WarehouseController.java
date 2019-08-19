package com.mikiruki.vendingsystemapi.controllers;

import com.mikiruki.vendingsystemapi.dao.WarehouseDAO;
import com.mikiruki.vendingsystemapi.models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseDAO warehouseDAO;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public Warehouse getWarehouse() {
        Warehouse warehouse = this.warehouseDAO.findById(0);
        return warehouse;
    }

    @PostMapping(path = "/update", consumes = "text/plain")
    @CrossOrigin(origins = "http://localhost:4200")
    public boolean updateAddress(@RequestBody String json) {
        return this.warehouseDAO.updateAddress(json);
    }

}
