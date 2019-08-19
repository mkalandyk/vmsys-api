package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.VendingMachine;

import java.util.List;

public interface VendingMachineDAO {

    VendingMachine save(VendingMachine vendingMachine);
    boolean update(VendingMachine vendingMachine);
    boolean cleanEmptyContent(VendingMachine vendingMachine);
    boolean removeOldContent(VendingMachine vendingMachine);
    VendingMachine findById(Integer id);
    List<VendingMachine> list();
    Integer getLastMachineId();

}
