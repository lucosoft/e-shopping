package com.hackerrank.eshopping.product.dashboard.service.impl;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductsRepository;
import com.hackerrank.eshopping.product.dashboard.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service("ProductsService")
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    @Override
    public ResponseEntity addProduct(Product product) {
        return productsRepository.addProduct(product);
    }

    @Override
    public ResponseEntity updateProductById(Product product, Long product_id) {
        return productsRepository.updateProductById(product, product_id);
    }

    @Override
    public ResponseEntity<Product> getProductById(Long product_id) {
        List<Product> products = productsRepository.getProductById(product_id);
        if(products.size()<1) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(products.get(0),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<List<Product>> getProductByCategory(String category) {
        List<Product> products = productsRepository.getProductByCategory(category);
        products.sort((p1, p2) -> {
            if (p1.getAvailability()==p2.getAvailability()){
                if(p1.getDiscounted_price()==p2.getDiscounted_price()){
                    return p1.getId().compareTo(p2.getId());
                }
                else{
                    return p1.getDiscounted_price().compareTo(p2.getDiscounted_price());
                }
            }
            else{
                return Boolean.compare(p2.getAvailability(),p1.getAvailability());
            }
        });
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProductByCategoryAndAvailability(String category, Boolean availability) {
        List<Product> products = productsRepository.getProductByCategoryAndAvailability(category.replaceAll("%20"," "), availability);
        if(products.size()>=1) {
            products.sort((p1, p2) -> {
                if ((((p1.getRetail_price() - p1.getDiscounted_price()) / p1.getRetail_price()) * 100) ==
                        (((p2.getRetail_price() - p2.getDiscounted_price()) / p2.getRetail_price()) * 100)) {
                    if (p1.getDiscounted_price() == p2.getDiscounted_price()) {
                        return p1.getId().compareTo(p2.getId());
                    } else {
                        return p1.getDiscounted_price().compareTo(p2.getDiscounted_price());
                    }
                } else {
                    return Integer.compare((int) Math.round((((p2.getRetail_price() - p2.getDiscounted_price()) / p2.getRetail_price()) * 100)),
                            ((int) Math.round((((p1.getRetail_price() - p1.getDiscounted_price()) / p1.getRetail_price()) * 100))));
                }
            });
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productsRepository.getProducts();
        if(products.size()>=1) {
            Comparator.comparing(Product::getId);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
