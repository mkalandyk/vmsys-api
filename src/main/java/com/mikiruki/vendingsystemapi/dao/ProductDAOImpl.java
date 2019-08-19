package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;
import java.util.Locale;

public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Product findById(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Product where id = :id");
            query.setParameter("id", id);
            Product product = (Product) query.uniqueResult();
            session.close();
            return product;
        }
    }

    @Override
    public Product findByName(String name) {
        try(Session session = this.sessionFactory.openSession()) {
            Query query = session.createQuery("from Product where name = :name");
            query.setParameter("name", name);
            Product product = (Product) query.uniqueResult();
            session.close();
            return product;
        }
    }

    @Override
    public List<Product> list() {
        try(Session session = this.sessionFactory.openSession()) {
            return session.createQuery("from Product p order by p.productId asc").list();
        }
    }

    @Override
    @CacheEvict(value = "productCache", key = "#product.name")
    public boolean updateProduct(Product product) {
        boolean bRet = false;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            product.setMachineContent(null);
            session.update(product);
            transaction.commit();
            bRet = true;
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
        }
        if(!bRet) {
            transaction = null;
            try(Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                NativeQuery query = session.createSQLQuery(String.format(Locale.US, "insert into product values (%d, '%s', %f)", product.getProductId(), product.getName(), product.getPrice()));
                query.executeUpdate();
                transaction.commit();
                bRet = true;
            } catch (Exception ex) {
                if(transaction != null)
                    transaction.rollback();
            }
        }
        return bRet;
    }
}
