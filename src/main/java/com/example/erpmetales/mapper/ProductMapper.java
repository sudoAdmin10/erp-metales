package com.example.erpmetales.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.erpmetales.model.OrderDetail;
import com.example.erpmetales.model.Product;

public class ProductMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();

        product.setId(rs.getInt("ID"));
        product.setdescription(rs.getString("DESCRIPTION"));
        product.setName(rs.getString("NAME"));
        product.setPrice(rs.getDouble("PRICE"));
        product.setStock(rs.getInt("STOCK"));

        return product;
    }
}
