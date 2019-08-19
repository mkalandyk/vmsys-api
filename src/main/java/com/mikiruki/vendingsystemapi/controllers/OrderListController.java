package com.mikiruki.vendingsystemapi.controllers;

import com.mikiruki.vendingsystemapi.dao.OrderListDAO;
import com.mikiruki.vendingsystemapi.models.*;
import com.mikiruki.vendingsystemapi.services.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderlist")
public class OrderListController {

    @Autowired
    private OrderListDAO orderListDAO;

    @Autowired
    private OrderListService orderListService;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OrderList> getOrderList() {
        return orderListDAO.getOrders();
    }

    @GetMapping("/product")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OrderListByProducts> getOrderListByProduct() {
        return orderListDAO.getOrdersByProduct();
    }

    @GetMapping("/machine")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OrderListByMachine> getOrderListByMachine() {
        return orderListDAO.getOrdersByMachine();
    }

    @GetMapping("/product_full")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OrderListByProductsWithContent> getOrderListByProductWithContent() {
        return this.orderListService.getOrderForProductsListWithContent();
    }

    @GetMapping("/machine_full")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<OrderListByMachineWithContent> getOrderListByMachineWithContent() {
        return this.orderListService.getOrderForMachinesListWithContent();
    }

    @PostMapping(path = "/completeList", consumes = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public boolean completeOrderList(@RequestBody String json) {
        return this.orderListService.completeOrderList(json);
    }
}
