package com.mikiruki.vendingsystemapi.services;

import com.mikiruki.vendingsystemapi.dao.ProductDAO;
import com.mikiruki.vendingsystemapi.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Cacheable(value = "productCache", key = "#name")
    public Product getProductByName(String name) {
        return productDAO.findByName(name);
    }

}
