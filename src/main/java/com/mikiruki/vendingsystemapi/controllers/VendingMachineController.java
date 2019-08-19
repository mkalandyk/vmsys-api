package com.mikiruki.vendingsystemapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikiruki.vendingsystemapi.configs.ContextProvider;
import com.mikiruki.vendingsystemapi.dao.VendingMachineDAO;
import com.mikiruki.vendingsystemapi.models.VendingMachine;
import com.mikiruki.vendingsystemapi.services.VendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vendingmachines")
public class VendingMachineController {

    @Autowired
    private VendingMachineDAO vendingMachineDAO;

    @Autowired
    private VendingMachineService vendingMachineService;

    @GetMapping("")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public List<VendingMachine> getMachinesState() {
        List<VendingMachine> allMachines = this.vendingMachineDAO.list();
        return allMachines;
    }

    @GetMapping("/last")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public Integer getLastMachineId() {
        Integer lastId = this.vendingMachineDAO.getLastMachineId();
        return lastId;
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public VendingMachine getMachineState(@PathVariable Integer id) {
        return this.vendingMachineDAO.findById(id);
    }

    @PostMapping(path = "/{id}", consumes = "application/json")
    @CrossOrigin(origins = "https://vm-sys.herokuapp.com")
    public void updateMachineState(@RequestBody String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        VendingMachine machine = mapper.readValue(json, VendingMachine.class);
        if(getMachineById(machine.getMachineId()) != null) {
            vendingMachineService.updateMachineState(machine);
        } else {
            machine.setMachineId(null);
            machine.getMachineContent().forEach(mc -> mc.getPk().setVendingMachine(machine));
            VendingMachine addedMachine = vendingMachineService.addMachine(machine);
            if(addedMachine != null) {
                vendingMachineService.updateMachineState(machine);
            }
        }
    }

    public VendingMachine getMachineById(int id) {
        if(vendingMachineDAO == null) {
            vendingMachineDAO = ContextProvider.getInstance().getContext().getBean(VendingMachineDAO.class);
        }
        return this.vendingMachineDAO.findById(id);
    }
}