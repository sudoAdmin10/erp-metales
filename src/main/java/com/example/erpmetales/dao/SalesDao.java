package com.example.erpmetales.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.erpmetales.mapper.*;
import com.example.erpmetales.model.Customer;

@Repository
public class SalesDao {

    @Autowired
    private JdbcTemplate PostgresTemplate;

    public List<Customer> getAllCustomers() {
        String query = "SELECT * FROM customer ORDER BY id ASC";
        return PostgresTemplate.query(query, new CustomerMapper());
    }

    public int addCustomer(Customer customer) {
        String query = "INSERT INTO CUSTOMER (person_id, address, phone) VALUES (?, ?, ?)";
        return PostgresTemplate.update(query, customer.getId(), customer.getAddress(), customer.getPhone());
    }

    public int deleteCustomer(int id) {
        String query = "DELETE FROM CUSTOMER WHERE id = ?";
        return PostgresTemplate.update(query, id);
    }

    public int updateCustomer(Customer customer) {
        String query = "UPDATE CUSTOMER SET address = ?, phone = ? WHERE id = ?";
        return PostgresTemplate.update(query, customer.getAddress(), customer.getPhone(), customer.getId());
    }
}
