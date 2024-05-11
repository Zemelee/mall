package com.example.mall.controller;

import com.example.mall.entity.Product;
import com.example.mall.entity.IQ;
import com.example.mall.service.ProductService;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/search={keyword}")
    public ResponseEntity<List<Product>> searchProduct(@PathVariable String keyword) {
        List<Product> products = productService.searchProduct(keyword);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        // 调用 productService 方法,直接添加新product
        boolean ok = productService.addProductWithAttributes(product, null);
        if (ok) {
            // 返回 ResponseEntity 对象，包含状态码和 JSON 信息
            return new ResponseEntity<>("ok", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);

        }
    }

    @Getter
    @ToString
    static class PageInfo {
        int page;
        int size;
    }

    @PostMapping("/get") //偏移量+显示数据
    public ResponseEntity<List<Product>> getProductByPage(@RequestBody PageInfo pageInfo) {
        List<Product> products = productService.getProduct(pageInfo.getPage(), pageInfo.getSize());
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/del")
    public ResponseEntity<String> delByIds(@RequestBody List<Integer> ids) {
        List<Integer> failedIds = new ArrayList<>();
        for (Integer id : ids) {
            boolean res = productService.delById(id);
            if (!res) {
                failedIds.add(id);
            }
        }
        if (!failedIds.isEmpty()) {
            String message = "Deletion failed for IDs: " + failedIds;
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity<String> confirmModify(@RequestBody Product product) {
        //删除商品规格和本体
        int pid = product.getId();
        productService.delById(pid);
        //添加商品
        boolean ok = productService.addProductWithAttributes(product, pid);
        if (ok) {
            return new ResponseEntity<>("ok", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recommend/uid={uid}")
    public ResponseEntity<List<Product>> getRecommend(@PathVariable int uid) {
        return new ResponseEntity<>(productService.getRecommend(uid), HttpStatus.CREATED);
    }


    @PostMapping("/inventory")
    public ResponseEntity<Map> isInventoryFull(@RequestBody List<IQ> IQS) {
        return new ResponseEntity<>(productService.isInventoryFull(IQS), HttpStatus.CREATED);
    }
}
