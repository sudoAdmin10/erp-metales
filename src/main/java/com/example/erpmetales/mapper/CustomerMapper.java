package com.example.erpmetales.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.Person;

public class CustomerMapper implements RowMapper<Person> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer cliente = new Customer();

        cliente.setId(rs.getInt("ID"));
        cliente.setLast_name(rs.getString("LAST_NAME"));
        cliente.setPhone(rs.getString("PHONE"));
        cliente.setAdress(rs.getString("ADRESS"));

        return cliente;
    }
}
