package com.example.erpmetales.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.erpmetales.model.Product;

public class ProductMapper {
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();

        product.setId(rs.getInt("ID"));
        product.setDescripcion(rs.getString("DESCRPTION"));
        product.setName(rs.getString("NAME"));
        product.setPrecio(rs.getInt("PRICE"));
        product.setStock(rs.getInt("STOCK"));

        return product;
    }
}
