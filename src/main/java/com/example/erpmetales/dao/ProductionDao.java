package com.example.erpmetales.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.erpmetales.mapper.OrderDetailMapper;
import com.example.erpmetales.model.Order;
import com.example.erpmetales.model.OrderDetail;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProductionDao {
    @Autowired
    private JdbcTemplate PostgresTemplate;

    // ORDENES
    // Mostrar Ordenes Pendientes y Rechazadas
    public List<OrderDetail> getAllOrdersPending() {
        String query = "SELECT o.id, o.customer_id, o.product_id, o.order_date, o.amount, o.total, o.status, o.defective_parts, o.description, "
                +
                "p.first_name, pr.name " +
                "FROM orders o " +
                "INNER JOIN customer c ON o.customer_id = c.id " +
                "INNER JOIN person p ON c.person_id = p.id " +
                "INNER JOIN product pr ON o.product_id = pr.id " +
                "WHERE o.status IN ('Sending', 'Rejected') " +
                "ORDER BY o.order_date DESC";
        return PostgresTemplate.query(query, new OrderDetailMapper());
    }

    public List<OrderDetail> getCustomerOrder(int customerId) {
        String query = "SELECT o.id, o.customer_id, o.product_id, o.order_date, o.amount, o.total, o.status, o.defective_parts, o.description, "
                +
                "p.first_name, pr.name " +
                "FROM orders o " +
                "INNER JOIN customer c ON o.customer_id = c.id " +
                "INNER JOIN person p ON c.person_id = p.id " +
                "INNER JOIN product pr ON o.product_id = pr.id " +
                "WHERE o.customer_id = ? AND o.status IN ('Rejected', 'Sending') " +
                "ORDER BY o.order_date DESC";

        return PostgresTemplate.query(query, new OrderDetailMapper(), customerId);
    }

    public OrderDetail getOrderRejected(int id) {
        String query = "SELECT description, defective_parts FROM orders WHERE id = ?";
        try {
            return PostgresTemplate.queryForObject(query, new RowMapper<OrderDetail>() {
                @Override
                public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OrderDetail order = new OrderDetail();
                    order.setDescription(rs.getString("DESCRIPTION"));
                    order.setDefective_parts(rs.getString("DEFECTIVE_PARTS"));
                    return order;
                }
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Enviar Orden a Calidad
    public int updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        return PostgresTemplate.update(query, status, orderId);
    }

}
