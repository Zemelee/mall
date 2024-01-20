package com.example.mall.service;

import com.example.mall.entity.Attribution;
import com.example.mall.entity.Product;
import com.example.mall.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Transactional
    public void insertProductWithAttribute(Product product, Attribution attribute) {
        try {
            // 插入商品信息
            productMapper.addProduct(product);
            // 获取插入商品后的ID
            int productId = product.getId();
            // 设置商品规格的商品ID
            attribute.setProduct_id(productId);
            // 插入商品规格信息
            productMapper.addProductAttribution(attribute);
        } catch (Exception e) {
            // 发生异常时回滚事务
            throw new RuntimeException("Failed to insert product with attribute", e);
        }
    }

    public Product getProductById(int id) {
        return productMapper.getProductById(id);
    }

    public List<Product> getProductsByCategory(int category) {
        return productMapper.getProductByCategory(category);
    }

    public Product getProductWithAttributes(int productId) {
        return productMapper.getProductWithAttributes(productId);
    }

    @Transactional
    public void addProductWithAttributes(Product product) {
        // 先插入商品信息
        productMapper.addProduct(product);
        // 获取插入商品后的ID
        int productId = product.getId();
        try {
            // 再设置商品规格：attribution 表的外键约束依赖于 product 表的存在
            if (product.getAttributions() != null) {
                for (Attribution attribution : product.getAttributions()) {
                    attribution.setProduct_id(productId);
                    attribution.setProduct_id(productId);
                    // 插入商品规格信息
                    productMapper.addProductAttribution(attribution);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // 或者使用日志库记录错误信息
            throw e; // 重新抛出异常
        }
    }

}
