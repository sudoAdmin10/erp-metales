package com.example.erpmetales.mapper;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.erpmetales.model.Provider;
import com.example.erpmetales.model.Person;

public class ProviderMapper implements RowMapper<Person> {
    @Override
    public Provider mapRow(ResultSet rs, int rowNum) throws SQLException {
        Provider proovedor = new Provider();

        proovedor.setId(rs.getInt("ID"));
        proovedor.setCompany(rs.getString("Company"));
        proovedor.setCity(rs.getString("City"));
        proovedor.setZip_code(rs.getString("Zip_code"));

        return proovedor;
    }

}
