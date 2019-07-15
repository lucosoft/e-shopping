package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductsService {

    ResponseEntity addProduct(Product product);
    ResponseEntity updateProductById(Product product, Long product_id);
    ResponseEntity<Product> getProductById(Long product_id);
    ResponseEntity<List<Product>> getProductByCategory(String category);
    ResponseEntity<List<Product>> getProductByCategoryAndAvailability(String category, Boolean availability);
    ResponseEntity<List<Product>> getProducts();
}
