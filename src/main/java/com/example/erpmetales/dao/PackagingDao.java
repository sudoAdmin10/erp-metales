package com.example.erpmetales.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.erpmetales.mapper.OrderDetailMapper;
import com.example.erpmetales.model.OrderDetail;

@Repository
public class PackagingDao {

    @Autowired
    private JdbcTemplate PostgresTemplate;

    // Mostrar Ordenes Aceptadas
    public List<OrderDetail> getAllOrdersAcepted() {
        String query = "SELECT o.id, o.customer_id, o.product_id, o.order_date, o.amount, o.total, o.status, o.defective_parts, o.description, "
                +
                "p.first_name, pr.name " +
                "FROM orders o " +
                "INNER JOIN customer c ON o.customer_id = c.id " +
                "INNER JOIN person p ON c.person_id = p.id " +
                "INNER JOIN product pr ON o.product_id = pr.id " +
                "WHERE o.status = 'Accepted' " +
                "ORDER BY o.order_date DESC";
        return PostgresTemplate.query(query, new OrderDetailMapper());
    }

    // Enviar Orden como envio
    public int updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        return PostgresTemplate.update(query, status, orderId);
    }
}
