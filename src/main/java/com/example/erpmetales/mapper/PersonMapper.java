package com.example.erpmetales.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.erpmetales.model.Person;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setFirst_name(rs.getString("FIRST_NAME"));
        person.setLast_name(rs.getString("LAST_NAME"));
        return person;
    }
}
