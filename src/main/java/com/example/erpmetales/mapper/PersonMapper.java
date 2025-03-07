package com.example.erpmetales.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.erpmetales.model.Person;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setName(rs.getString("NAME"));
        person.setEmail(rs.getString("EMAIL"));

        return person;
    }
}
