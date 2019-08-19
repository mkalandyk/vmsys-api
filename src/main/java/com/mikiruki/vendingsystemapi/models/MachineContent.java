package com.mikiruki.vendingsystemapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mikiruki.vendingsystemapi.controllers.VendingMachineController;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "machine_content")
@AssociationOverrides({
        @AssociationOverride(name = "pk.product",
                joinColumns = @JoinColumn(name = "product_id")),
        @AssociationOverride(name = "pk.vendingMachine",
                joinColumns = @JoinColumn(name = "machine_id")) })
public class MachineContent implements Serializable {

    private MachineContentID pk = new MachineContentID();
    private Integer quantity;

    @JsonIgnore
    @EmbeddedId
    public MachineContentID getPk() {
        return pk;
    }

    public void setPk(MachineContentID pk) {
        this.pk = pk;
    }

    @Transient
    public Product getProduct() {
        return this.getPk().getProduct();
    }

    public void setProduct(Product product) {
        this.getPk().setProduct(product);
    }

    @Transient
    public int getVendingMachineId() {
        return this.getPk().getVendingMachine().getMachineId();
    }

    public void setVendingMachineId(int vendingMachineId) {
        VendingMachineController VMCtrl = new VendingMachineController();
        this.getPk().setVendingMachine(VMCtrl.getMachineById(vendingMachineId));
    }

    @Column(name = "quantity", nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        MachineContent that = (MachineContent)o;

        if (this.getPk() != null ? !this.getPk().equals(that.getPk()) : that.getPk() != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (this.getPk() != null ? getPk().hashCode() : 0);
    }
}
