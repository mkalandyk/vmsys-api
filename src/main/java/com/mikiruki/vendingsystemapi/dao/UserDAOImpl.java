package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean save(User user) {

        boolean bRet = false;

        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            bRet = true;
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
        }

        return bRet;
    }

    @Override
    public boolean update(User user) {
        boolean bRet = false;

        Transaction transaction = null;
        try(Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            bRet = true;
        } catch (Exception exc) {
            if(transaction != null)
                transaction.rollback();
        }

        return bRet;
    }

    @Override
    public User findById(Integer id) {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("from User where id = :id");
        query.setParameter("id", id);
        User user = (User)query.uniqueResult();
        session.close();
        return user;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("from User where username = :username and password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User)query.uniqueResult();
        session.close();
        return user;
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        Session session = this.sessionFactory.openSession();
        Query queryUsername = session.createQuery("from User where username = :username");
        Query queryEmail = session.createQuery("from User where email = :email");
        queryUsername.setParameter("username", username);
        queryEmail.setParameter("email", email);
        User user = (User)queryUsername.uniqueResult();
        if(user == null)
            user = (User)queryEmail.uniqueResult();
        session.close();
        return user;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> list() {
        Session session = this.sessionFactory.openSession();
        Query query = session.createQuery("from User");
        List<User> users = query.list();
        session.close();
        return users;
    }

}
