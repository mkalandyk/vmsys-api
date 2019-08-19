package com.mikiruki.vendingsystemapi.dao;

import com.mikiruki.vendingsystemapi.models.Product;

import java.util.List;

public interface ProductDAO {

    public Product findById(Integer id);
    public Product findByName(String name);
    public List<Product> list();
    public boolean updateProduct(Product product);

}
