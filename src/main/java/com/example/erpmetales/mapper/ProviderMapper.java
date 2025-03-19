package com.example.erpmetales.mapper;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.erpmetales.model.Provider;

public class ProviderMapper implements RowMapper<Provider> {
    @Override
    public Provider mapRow(ResultSet rs, int rowNum) throws SQLException {
        Provider proovedor = new Provider();

        proovedor.setId(rs.getInt("ID"));
        proovedor.setPerson_id(rs.getInt("PERSON_ID"));
        proovedor.setFirst_name(rs.getString("FIRST_NAME"));
        proovedor.setLast_name(rs.getString("LAST_NAME"));
        proovedor.setAddress(rs.getString("ADDRESS"));
        proovedor.setEmail(rs.getString("EMAIL"));
        proovedor.setCompany(rs.getString("COMPANY"));
        proovedor.setCity(rs.getString("CITY"));
        proovedor.setZip_code(rs.getString("ZIP_CODE"));
        proovedor.setPhone(rs.getString("PHONE"));

        return proovedor;
    }

}
