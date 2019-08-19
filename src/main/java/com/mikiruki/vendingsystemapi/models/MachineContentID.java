package com.mikiruki.vendingsystemapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MachineContentID implements java.io.Serializable {

    private VendingMachine vendingMachine;
    private Product product;

    @JsonIgnore
    @ManyToOne
    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @JsonIgnore
    @ManyToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        MachineContentID that = (MachineContentID) o;

        if (vendingMachine != null ? !vendingMachine.equals(that.vendingMachine) : that.vendingMachine != null)
            return false;
        if (product != null ? !product.equals(that.product) : that.product != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (vendingMachine != null ? vendingMachine.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
