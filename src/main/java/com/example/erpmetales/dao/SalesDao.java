package com.example.erpmetales.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.erpmetales.mapper.*;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.Provider;

@Repository
public class SalesDao {

    @Autowired
    private JdbcTemplate PostgresTemplate;

    public List<Customer> getAllCustomers() {
        String query = "SELECT c.id, c.person_id, p.first_name, p.last_name, c.email, c.address, c.phone " +
                "FROM customer c " +
                "INNER JOIN person p ON c.person_id = p.id";
        return PostgresTemplate.query(query, new CustomerMapper());
    }

    @SuppressWarnings("deprecation")
    public Customer getCustomerById(int id) {
        String query = "SELECT c.id, c.person_id, p.last_name, c.address, c.phone, p.first_name, c.email " +
                "FROM customer c INNER JOIN person p ON c.person_id = p.id " +
                "WHERE c.id = ?";

        return PostgresTemplate.queryForObject(query, new Object[] { id }, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setPerson_id(rs.getInt("person_id"));
            customer.setLast_name(rs.getString("last_name"));
            customer.setAddress(rs.getString("address"));
            customer.setPhone(rs.getString("phone"));
            customer.setFirst_name(rs.getString("first_name"));
            customer.setEmail(rs.getString("email"));
            return customer;
        });
    }

    public int addCustomer(Customer customer) {
        // Insertar en la tabla Person
        String personQuery = "INSERT INTO person (first_name, last_name) VALUES (?, ?) RETURNING id";
        int personId = PostgresTemplate.queryForObject(personQuery, new Object[] {
                customer.getFirst_name(),
                customer.getLast_name()
        }, Integer.class);

        // Insertar en la tabla Customer con la referencia a Person
        String customerQuery = "INSERT INTO customer (person_id, address, phone, email) VALUES (?, ?, ?, ?)";
        return PostgresTemplate.update(customerQuery,
                personId,
                customer.getAddress(),
                customer.getPhone(),
                customer.getEmail());
    }

    // Eliminar cutomer
    public int deleteCustomer(int id) {
        String getPersonIdQuery = "SELECT person_id FROM customer WHERE id = ?";
        Integer personId = PostgresTemplate.queryForObject(getPersonIdQuery, new Object[] { id }, Integer.class);

        if (personId != null) {
            String customerQuery = "DELETE FROM customer WHERE id = ?";
            PostgresTemplate.update(customerQuery, id);

            String personQuery = "DELETE FROM person WHERE id = ?";
            return PostgresTemplate.update(personQuery, personId);
        }

        return 0;
    }

    // Actualizar cliente
    public int updateCustomer(Customer customer) {
        String personQuery = "UPDATE person SET first_name = ?, last_name = ? WHERE id = (SELECT person_id FROM customer WHERE id = ?)";
        PostgresTemplate.update(personQuery, customer.getFirst_name(), customer.getLast_name(), customer.getId());

        String customerQuery = "UPDATE customer SET email = ?, address = ?, phone = ? WHERE id = ?";
        return PostgresTemplate.update(customerQuery, customer.getEmail(), customer.getAddress(),
                customer.getPhone(), customer.getId());
    }

    // SUPPLIERS -----------------------------------------
    public List<Provider> getAllSuppliers() {
        String query = "SELECT c.id, c.person_id, p.first_name, p.last_name, c.email, c.address, c.phone, c.company, c.city, c.zip_code FROM provider c INNER JOIN person p ON c.person_id = p.id";
        return PostgresTemplate.query(query, new ProviderMapper());
    }

    public int addProvider(Provider provider) {
        String personQuery = "INSERT INTO person (first_name, last_name) VALUES (?, ?) RETURNING id";
        int personId = PostgresTemplate.queryForObject(personQuery, new Object[] {
                provider.getFirst_name(),
                provider.getLast_name()
        }, Integer.class);

        String customerQuery = "INSERT INTO provider (person_id, address, phone, email, company, city, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return PostgresTemplate.update(customerQuery,
                personId,
                provider.getAddress(),
                provider.getPhone(),
                provider.getEmail(),
                provider.getCompany(),
                provider.getCity(),
                provider.getZip_code());
    }

    // Eliminar proovedor
    public int deleteProvider(int id) {
        // Obtener el person_id antes de eliminar el cliente
        String getPersonIdQuery = "SELECT person_id FROM customer WHERE id = ?";
        Integer personId = PostgresTemplate.queryForObject(getPersonIdQuery, new Object[] { id }, Integer.class);

        if (personId != null) {
            String customerQuery = "DELETE FROM provider WHERE id = ?";
            PostgresTemplate.update(customerQuery, id);

            String personQuery = "DELETE FROM person WHERE id = ?";
            return PostgresTemplate.update(personQuery, personId);
        }

        return 0;
    }

    // Actualizar proovedor
    public int updateProvider(Provider provider) {
        String personQuery = "UPDATE person SET first_name = ?, last_name = ? WHERE id = (SELECT person_id FROM provider WHERE id = ?)";
        PostgresTemplate.update(personQuery, provider.getFirst_name(), provider.getLast_name(), provider.getId());

        String providerQuery = "UPDATE customer SET email = ?, address = ?, phone = ?, city = ?  , company = ?, zip_code =? WHERE id = ?";
        return PostgresTemplate.update(providerQuery, provider.getEmail(), provider.getAddress(),
                provider.getPhone(), provider.getCity(), provider.getCompany(), provider.getZip_code(),
                provider.getId());
    }
}
