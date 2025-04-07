package com.example.erpmetales.mapper;

import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
import com.example.erpmetales.model.Person;
import com.example.erpmetales.model.Users;
import java.sql.ResultSet;

public class UserMapper implements RowMapper<Users> {
    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        Users usuario = new Users();

        usuario.setId(rs.getLong("ID"));
        usuario.setRole(rs.getString("ROLE"));
        usuario.setPassword(rs.getString("PASSWORD"));
        usuario.setEmail(rs.getString("EMAIL"));
        usuario.setEnabled(rs.getBoolean("ENABLE"));

        return usuario;
    }
}
