package com.example.erpmetales.mapper;

import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
import com.example.erpmetales.model.Person;
import com.example.erpmetales.model.User;
import java.sql.ResultSet;

public class UserMapper implements RowMapper<Person> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User usuario = new User();

        usuario.setId(rs.getInt("ID"));
        usuario.setRole(rs.getInt("ROLE"));

        return usuario;
    }
}
