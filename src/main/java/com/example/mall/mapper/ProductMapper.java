package com.example.mall.mapper;

import com.example.mall.entity.Attribution;
import com.example.mall.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    // 根据商品ID查询商品信息
    Product getProductById(int productId);

    // 根据种类ID查询商品信息
    List<Product> getProductByCategory(int category);

    // 新增商品
    void addProduct(Product product);

    // 新增商品属性
    void addProductAttribution(Attribution attr);

    Product getProductWithAttributes(int productId);


}
