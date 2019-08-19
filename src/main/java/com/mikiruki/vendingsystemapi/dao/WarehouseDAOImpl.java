package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.Warehouse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class WarehouseDAOImpl implements WarehouseDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Warehouse findById(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Warehouse where id = :id");
            query.setParameter("id", id);
            Warehouse warehouse = (Warehouse) query.uniqueResult();
            session.close();
            return warehouse;
        }
    }

    @Override
    public boolean updateAddress(String address) {
        boolean bRet = false;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Warehouse warehouse = new Warehouse(0, address);
            session.update(warehouse);
            transaction.commit();
            bRet = true;
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
        }
        return bRet;
    }
}
