package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.OrderList;
import com.mikiruki.vendingsystemapi.models.OrderListByMachine;
import com.mikiruki.vendingsystemapi.models.OrderListByProducts;
import com.mikiruki.vendingsystemapi.models.VendingMachine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderListDAOImpl implements OrderListDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public boolean addToList(int machineId, int productId, int quantity) {
        boolean bRet = false;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            OrderList orderList = new OrderList(machineId, productId, quantity);
            session.persist(orderList);
            transaction.commit();
            bRet = true;
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
        }
        return bRet;
    }

    @Override
    public boolean updateList(int machineId, int productId, int quantity) {
        boolean bRet = false;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            OrderList orderList = new OrderList(machineId, productId, quantity);
            session.update(orderList);
            transaction.commit();
            bRet = true;
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
        }
        return bRet;
    }

    @Override
    public boolean removeFromList(int machineId, int productId) {
        boolean bRet = false;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from OrderList where machineId = :machineId and productId = :productId");
            query.setParameter("machineId", machineId);
            query.setParameter("productId", productId);
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
    public boolean removeOrphans(VendingMachine machine) {
        boolean bRet = false;

        List<Integer> prodIds = machine.getMachineContent().stream().map(c -> c.getProduct().getProductId()).collect(Collectors.toList());

        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from OrderList where machineId = :machineId and productId not in (:prodIds)");
            query.setParameter("machineId", machine.getMachineId());
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
    public boolean checkOccurenceByIds(int machineId, int productId) {
        boolean bRet = false;
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from OrderList where machineId = :machineId and productId = :productId");
            query.setParameter("machineId", machineId);
            query.setParameter("productId", productId);
            bRet = query.uniqueResult() != null;
        }
        return bRet;
    }

    @Override
    public boolean checkMachineOccurenceById(int machineId) {
        boolean bRet = false;
        try(Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("from OrderList where machineId = :machineId");
            query.setParameter("machineId", machineId);
            bRet = !query.list().isEmpty();
        }
        return bRet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrderList> getOrders() {
        List<OrderList> orderLists;
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from OrderList");
            orderLists = query.list();
        }
        return orderLists;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrderListByProducts> getOrdersByProduct() {
        List<OrderListByProducts> orderLists = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("SELECT ol.productId, string_agg(cast(ol.machineId as text), ',') as machines, string_agg(cast(ol.quantity as text), ',') as quantities FROM OrderList ol group by ol.productId order by ol.productId");
            for(Object[] o : (List<Object[]>)query.list()){
                orderLists.add(new OrderListByProducts(Integer.parseInt(String.valueOf(o[0])), String.valueOf(o[1]), String.valueOf(o[2])));
            }
        }
        return orderLists;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrderListByMachine> getOrdersByMachine() {
        List<OrderListByMachine> orderLists = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("SELECT ol.machineId, string_agg(cast(ol.productId as text), ',') as products, string_agg(cast(ol.quantity as text), ',') as quantities FROM OrderList ol group by ol.machineId order by ol.machineId");
            for(Object[] o : (List<Object[]>)query.list()){
                orderLists.add(new OrderListByMachine(Integer.parseInt(String.valueOf(o[0])), String.valueOf(o[1]), String.valueOf(o[2])));
            }
        }
        return orderLists;
    }
}
