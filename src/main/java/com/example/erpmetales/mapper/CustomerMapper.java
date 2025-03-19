package com.example.erpmetales.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.Person;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer cliente = new Customer();

        cliente.setId(rs.getInt("ID"));
        cliente.setPerson_id(rs.getInt("PERSON_ID"));
        cliente.setFirst_name(rs.getString("FIRST_NAME"));
        cliente.setLast_name(rs.getString("LAST_NAME"));
        cliente.setPhone(rs.getString("PHONE"));
        cliente.setAddress(rs.getString("ADDRESS"));
        cliente.setEmail(rs.getString("EMAIL"));

        return cliente;
    }
}
