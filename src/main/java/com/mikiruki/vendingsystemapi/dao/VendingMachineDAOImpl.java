package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.VendingMachine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class VendingMachineDAOImpl implements VendingMachineDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public VendingMachine save(VendingMachine vendingMachine) {

        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(vendingMachine);
            transaction.commit();
        } catch (Exception exc) {
            if(transaction != null)
                transaction.rollback();
        }

        return vendingMachine;
    }

    @Override
    public boolean update(VendingMachine vendingMachine) {
        boolean bRet = false;

        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(vendingMachine);
            transaction.commit();
            bRet = true;
        } catch (Exception exc) {
            if(transaction != null)
                transaction.rollback();
        }

        return bRet;
    }

    @Override
    public boolean cleanEmptyContent(VendingMachine vendingMachine) {
        boolean bRet = false;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from MachineContent where machine_id = :machineId and quantity = 0");
            query.setParameter("machineId", vendingMachine.getMachineId());
            query.executeUpdate();
            transaction.commit();
            bRet = true;
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
        }
        return bRet;
    }

    @Override
    public boolean removeOldContent(VendingMachine vendingMachine) {
        boolean bRet = false;

        List<Integer> prodIds = vendingMachine.getMachineContent().stream().map(c -> c.getProduct().getProductId()).collect(Collectors.toList());

        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from MachineContent where machine_id = :machineId and product_id not in (:prodIds)");
            query.setParameter("machineId", vendingMachine.getMachineId());
            query.setParameterList("prodIds", prodIds);
            query.executeUpdate();
            transaction.commit();
            bRet = true;
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
        }
        return bRet;
    }

    @Override
    public VendingMachine findById(Integer id) {
        try(Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("from VendingMachine where id = :id");
            query.setParameter("id", id);
            VendingMachine vendingMachine = (VendingMachine) query.uniqueResult();
            return vendingMachine;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VendingMachine> list() {
        try(Session session = this.sessionFactory.openSession()) {
            return session.createQuery("from VendingMachine v order by v.machineId asc").list();
        }
    }

    @Override
    public Integer getLastMachineId() {
        try(Session session = this.sessionFactory.openSession()) {
            // TODO: change this!!! use optional or so.
            return ((VendingMachine)session.createQuery("from VendingMachine v order by v.machineId desc").list().get(0)).getMachineId();
        }
    }
}
