package com.example.erpmetales.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;
import com.example.erpmetales.model.Order;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();

        order.setId(rs.getInt("ID"));
        order.setCustomerId(rs.getInt("CUSTOMER_ID"));
        order.setProductId(rs.getInt("PRODUCT_ID"));
        order.setOrderDate(rs.getTimestamp("ORDER_DATE").toLocalDateTime());

        Timestamp orderDateTimestamp = rs.getTimestamp("ORDER_DATE");
        if (orderDateTimestamp != null) {
            order.setOrderDate(orderDateTimestamp.toLocalDateTime());
        }

        order.setAmount(rs.getInt("AMOUNT"));
        order.setStatus(rs.getString("STATUS"));

        return order;
    }
}
