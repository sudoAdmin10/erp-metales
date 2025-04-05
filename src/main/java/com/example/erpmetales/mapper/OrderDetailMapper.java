package com.example.erpmetales.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

import com.example.erpmetales.model.Order;
import com.example.erpmetales.model.OrderDetail;
import com.example.erpmetales.model.Person;

public class OrderDetailMapper implements RowMapper<OrderDetail> {
    @Override
    public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDetail order = new OrderDetail();

        order.setId(rs.getInt("ID"));

        // Información del cliente (desde las tablas relacionadas)
        order.setCustomerName(rs.getString("FIRST_NAME"));
        order.setProductName(rs.getString("NAME"));

        order.setCustomerId(rs.getInt("CUSTOMER_ID"));
        order.setProductId(rs.getInt("PRODUCT_ID"));

        Timestamp orderDate = rs.getTimestamp("ORDER_DATE");
        if (orderDate != null) {
            order.setOrderDate(orderDate.toLocalDateTime());
        }

        order.setTotal(rs.getDouble("TOTAL"));
        order.setAmount(rs.getInt("AMOUNT"));
        order.setStatus(rs.getString("STATUS"));

        order.setDefective_parts(rs.getString("DEFECTIVE_PARTS"));
        order.setDescription(rs.getString("DESCRIPTION"));

        return order;
    }
}
