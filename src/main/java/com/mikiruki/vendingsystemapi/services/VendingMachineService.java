package com.mikiruki.vendingsystemapi.services;

import com.mikiruki.vendingsystemapi.dao.OrderListDAO;
import com.mikiruki.vendingsystemapi.dao.VendingMachineDAO;
import com.mikiruki.vendingsystemapi.models.MachineContent;
import com.mikiruki.vendingsystemapi.models.VendingMachine;
import com.mikiruki.vendingsystemapi.utils.MailTemplates;
import org.springframework.beans.factory.annotation.Autowired;

public class VendingMachineService {

    @Autowired
    private VendingMachineDAO vendingMachineDAO;

    @Autowired
    private OrderListDAO orderListDAO;

    @Autowired
    private MailingService mailingService;

    public void updateMachineState(VendingMachine machine) {
        if(vendingMachineDAO.update(machine)) {
            updateContent(machine);
            updateOrderList(machine);
        }
    }

    public VendingMachine addMachine(VendingMachine machine) {
        return this.vendingMachineDAO.save(machine);
    }

    private void updateOrderList(VendingMachine machine) {
        for(MachineContent mContent : machine.getMachineContent()) {
            if(mContent.getQuantity() == 0) {
                if(!this.orderListDAO.checkMachineOccurenceById(machine.getMachineId())){
                    this.mailingService.notifyAdmin(MailTemplates.machineNeedsAttentionTemplate(machine));
                }
                if(orderListDAO.checkOccurenceByIds(machine.getMachineId(), mContent.getProduct().getProductId())){
                    orderListDAO.updateList(machine.getMachineId(), mContent.getProduct().getProductId(), 15);
                } else {
                    orderListDAO.addToList(machine.getMachineId(), mContent.getProduct().getProductId(), 15);
                }
            }
        }
        orderListDAO.removeOrphans(machine);
    }

    private void updateContent(VendingMachine machine) {
        this.vendingMachineDAO.removeOldContent(machine);
    }

}
