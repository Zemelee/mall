package com.example.mall.controller;

import com.example.mall.entity.Product;
import com.example.mall.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mall/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/id={productId}")
    public ResponseEntity<Product> getProductWithAttributes(@PathVariable int productId) {
        Product product = productService.getProductWithAttributes(productId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 接口路径为：/mall/product/category={categoryId}
    @GetMapping("/category={categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable int categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        // 打印前端发送的product参数
        System.out.println("Received Product: " + product);
        productService.addProductWithAttributes(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
