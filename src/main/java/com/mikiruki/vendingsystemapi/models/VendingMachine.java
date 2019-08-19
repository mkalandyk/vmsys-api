package com.mikiruki.vendingsystemapi.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vending_machine")
public class VendingMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    Integer machineId;

    @Column(name = "address")
    String address;

    @OneToMany(mappedBy = "pk.vendingMachine", cascade=CascadeType.ALL, orphanRemoval = true)
    Set<MachineContent> machineContent = new HashSet<>();

    @Column(name = "billon_10")
    Integer billon_10;

    @Column(name = "billon_20")
    Integer billon_20;

    @Column(name = "billon_50")
    Integer billon_50;

    @Column(name = "billon_1")
    Integer billon_1;

    @Column(name = "billon_2")
    Integer billon_2;

    @Column(name = "billon_5")
    Integer billon_5;

    public VendingMachine() {
    }

    public VendingMachine(Integer machineId, String address, Set<MachineContent> machineContent, Integer billon_10, Integer billon_20, Integer billon_50, Integer billon_1, Integer billon_2, Integer billon_5) {
        this.machineId = machineId;
        this.address = address;
        this.machineContent = machineContent;
        this.billon_10 = billon_10;
        this.billon_20 = billon_20;
        this.billon_50 = billon_50;
        this.billon_1 = billon_1;
        this.billon_2 = billon_2;
        this.billon_5 = billon_5;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<MachineContent> getMachineContent() {
        return machineContent;
    }

    public void setMachineContent(Set<MachineContent> machineContent) {
        this.machineContent = machineContent;
    }

    public Integer getBillon_10() {
        return billon_10;
    }

    public void setBillon_10(Integer billon_10) {
        this.billon_10 = billon_10;
    }

    public Integer getBillon_20() {
        return billon_20;
    }

    public void setBillon_20(Integer billon_20) {
        this.billon_20 = billon_20;
    }

    public Integer getBillon_50() {
        return billon_50;
    }

    public void setBillon_50(Integer billon_50) {
        this.billon_50 = billon_50;
    }

    public Integer getBillon_1() {
        return billon_1;
    }

    public void setBillon_1(Integer billon_1) {
        this.billon_1 = billon_1;
    }

    public Integer getBillon_2() {
        return billon_2;
    }

    public void setBillon_2(Integer billon_2) {
        this.billon_2 = billon_2;
    }

    public Integer getBillon_5() {
        return billon_5;
    }

    public void setBillon_5(Integer billon_5) {
        this.billon_5 = billon_5;
    }

}
