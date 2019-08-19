package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.OrderList;
import com.mikiruki.vendingsystemapi.models.OrderListByMachine;
import com.mikiruki.vendingsystemapi.models.OrderListByProducts;
import com.mikiruki.vendingsystemapi.models.VendingMachine;

import java.util.List;

public interface OrderListDAO {

    boolean addToList(int machineId, int productId, int quantity);
    boolean updateList(int machineId, int productId, int quantity);
    boolean removeFromList(int machineId, int productId);
    boolean removeOrphans(VendingMachine machine);
    boolean checkOccurenceByIds(int machineId, int productId);
    boolean checkMachineOccurenceById(int machineId);
    List<OrderList> getOrders();
    List<OrderListByProducts> getOrdersByProduct();
    List<OrderListByMachine> getOrdersByMachine();

}
