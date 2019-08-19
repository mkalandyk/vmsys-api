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
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public List<OrderList> getOrderList() {
        return orderListDAO.getOrders();
    }

    @GetMapping("/product")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public List<OrderListByProducts> getOrderListByProduct() {
        return orderListDAO.getOrdersByProduct();
    }

    @GetMapping("/machine")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public List<OrderListByMachine> getOrderListByMachine() {
        return orderListDAO.getOrdersByMachine();
    }

    @GetMapping("/product_full")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public List<OrderListByProductsWithContent> getOrderListByProductWithContent() {
        return this.orderListService.getOrderForProductsListWithContent();
    }

    @GetMapping("/machine_full")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public List<OrderListByMachineWithContent> getOrderListByMachineWithContent() {
        return this.orderListService.getOrderForMachinesListWithContent();
    }

    @PostMapping(path = "/completeList", consumes = "application/json")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public boolean completeOrderList(@RequestBody String json) {
        return this.orderListService.completeOrderList(json);
    }
}
