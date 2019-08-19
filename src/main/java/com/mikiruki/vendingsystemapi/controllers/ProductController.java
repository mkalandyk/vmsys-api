package com.mikiruki.vendingsystemapi.controllers;

import com.mikiruki.vendingsystemapi.dao.ProductDAO;
import com.mikiruki.vendingsystemapi.models.Product;
import com.mikiruki.vendingsystemapi.services.ProductService;
import com.mikiruki.vendingsystemapi.utils.JSONParserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductService productService;

    @Autowired
    private JSONParserHelper<Product> productParserHelper;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Product> getProducts() {
        List<Product> allProducts = this.productDAO.list();
        return allProducts;
    }

    @GetMapping(
            path = {"/get"},
            params = {"name"})
    public Product getProductByName(@RequestParam String name) {
        return this.productService.getProductByName(name);
    }

    @PostMapping(path = "/update", consumes = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public boolean updateProduct(@RequestBody String json) {
        Product product = productParserHelper.parseJSONToObject(json);
        return this.productDAO.updateProduct(product);
    }
}
