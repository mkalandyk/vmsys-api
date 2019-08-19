package com.mikiruki.vendingsystemapi.services;

import com.mikiruki.vendingsystemapi.models.MachineContent;
import com.mikiruki.vendingsystemapi.models.MachineContentID;
import com.mikiruki.vendingsystemapi.models.Product;
import com.mikiruki.vendingsystemapi.models.VendingMachine;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceTest {

    @Test
    void updateOrderListTest() {
        VendingMachine vendingMachine = new VendingMachine(0, "", new HashSet<MachineContent>(), 0, 0, 0, 0, 0, 0);
        Product product = new Product(0, "", 0f);
        MachineContent machineContent = new MachineContent();
        MachineContentID pk = new MachineContentID();
        pk.setVendingMachine(vendingMachine);
        machineContent.setPk(pk);
        vendingMachine.setMachineContent(Set.of(machineContent));
    }


}