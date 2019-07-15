package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addProduct(@RequestBody Product product) {
        return productsService.addProduct(product);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{product_id}")
    public ResponseEntity updateProductById(@RequestBody Product product, @PathVariable("product_id") Long product_id) {
        return productsService.updateProductById(product,product_id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{product_id}")
    public ResponseEntity<Product> getProductById(@PathVariable("product_id") Long product_id) {
        return productsService.getProductById(product_id);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"category"})
    public ResponseEntity<List<Product>> getProductByCategory(@RequestParam String category) {
        return productsService.getProductByCategory(category);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"category", "availability"})
    public ResponseEntity<List<Product>> getProductByCategoryAndAvailability(@RequestParam("category") String category,
                                                                             @RequestParam("availability")  Boolean availability) {
        return productsService.getProductByCategoryAndAvailability(category, availability);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getProducts() {
        return productsService.getProducts();
    }

}
