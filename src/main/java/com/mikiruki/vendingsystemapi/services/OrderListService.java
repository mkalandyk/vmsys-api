package com.mikiruki.vendingsystemapi.services;

import com.mikiruki.vendingsystemapi.dao.OrderListDAO;
import com.mikiruki.vendingsystemapi.dao.ProductDAO;
import com.mikiruki.vendingsystemapi.dao.VendingMachineDAO;
import com.mikiruki.vendingsystemapi.models.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class OrderListService {

    @Autowired
    private OrderListDAO orderListDAO;

    @Autowired
    private VendingMachineDAO vendingMachineDAO;

    @Autowired
    private ProductDAO productDAO;

    public List<OrderListByProductsWithContent> getOrderForProductsListWithContent() {
        List<OrderListByProductsWithContent> list = new ArrayList<>();
        List<OrderListByProducts> productsList = this.orderListDAO.getOrdersByProduct();

        for(OrderListByProducts p : productsList) {
            list.add(OrderListByProductsWithContent
                    .build()
                    .setProduct(productDAO.findById(p.getProduct_id()))
                    .setMachines(this.buildMachineQuantityMap(p.getMachines(), p.getQuantities())));
        }

        return list;
    }

    public List<OrderListByMachineWithContent> getOrderForMachinesListWithContent() {
        List<OrderListByMachineWithContent> list = new ArrayList<>();
        List<OrderListByMachine> machinesList = this.orderListDAO.getOrdersByMachine();

        for(OrderListByMachine p : machinesList) {
            list.add(OrderListByMachineWithContent
                    .build()
                    .setMachine(vendingMachineDAO.findById(p.getMachine_id()))
                    .setProducts(this.buildProductQuantityMap(p.getProducts(), p.getQuantities())));
        }

        return list;
    }

    public boolean completeOrderList(String body) {
        boolean bRet = false;
        if(body.contains("complete")) {
            for(OrderListByMachineWithContent oe : getOrderForMachinesListWithContent()) {
                VendingMachine machine = oe.getMachine();
                machine.setBillon_10(10);
                machine.setBillon_20(10);
                machine.setBillon_50(10);
                machine.setBillon_1(10);
                machine.setBillon_2(10);
                machine.setBillon_5(10);
                Set<MachineContent> machineContents = machine.getMachineContent();
                for(Map.Entry<Product, Integer> entry : oe.getProducts().entrySet()) {
                    MachineContent mcEntry = new MachineContent();
                    mcEntry.setProduct(entry.getKey());
                    mcEntry.setVendingMachineId(oe.getMachine().getMachineId());
                    mcEntry.setQuantity(entry.getValue());
                    MachineContentID mcID = new MachineContentID();
                    mcID.setVendingMachine(oe.getMachine());
                    mcID.setProduct(entry.getKey());
                    mcEntry.setPk(mcID);
                    machineContents.add(mcEntry);
                    this.orderListDAO.removeFromList(oe.getMachine().getMachineId(), entry.getKey().getProductId());
                }
                machine.setMachineContent(machineContents.stream().filter(mc -> mc.getQuantity() > 0).collect(Collectors.toSet()));
                bRet = this.vendingMachineDAO.update(machine);
                this.vendingMachineDAO.cleanEmptyContent(machine);
            }
        }
        return bRet;
    }

    private Map<VendingMachine, Integer> buildMachineQuantityMap(String machines, String quantities) {
        Map<VendingMachine, Integer> map = new HashMap<>();
        String[] machinesIds = machines.split(",");
        String[] quantitiesArray = quantities.split(",");

        for(int i=0; i<machinesIds.length; i++){
            map.put(vendingMachineDAO.findById(Integer.parseInt(machinesIds[i])), Integer.parseInt(quantitiesArray[i]));
        }

        return map;
    }

    private Map<Product, Integer> buildProductQuantityMap(String products, String quantities) {
        Map<Product, Integer> map = new HashMap<>();
        String[] productsIds = products.split(",");
        String[] quantitiesArray = quantities.split(",");

        for(int i=0; i<productsIds.length; i++){
            map.put(productDAO.findById(Integer.parseInt(productsIds[i])), Integer.parseInt(quantitiesArray[i]));
        }

        return map;
    }

}
