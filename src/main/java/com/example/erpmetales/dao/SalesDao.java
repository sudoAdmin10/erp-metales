package com.example.erpmetales.dao;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.erpmetales.mapper.*;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.Provider;

import ch.qos.logback.core.net.SyslogOutputStream;

import com.example.erpmetales.model.Order;
import com.example.erpmetales.model.OrderDetail;
import com.example.erpmetales.model.Product;

import java.time.LocalDateTime;

@Repository
public class SalesDao {

    @Autowired
    private JdbcTemplate PostgresTemplate;

    @Configuration
    public class DateTimeConfig {

        @Bean
        public Formatter<LocalDateTime> localDateTimeFormatter() {
            return new Formatter<LocalDateTime>() {
                @Override
                public LocalDateTime parse(String text, Locale locale) {
                    return LocalDate.parse(text).atStartOfDay();
                }

                @Override
                public String print(LocalDateTime object, Locale locale) {
                    return object.toLocalDate().toString();
                }
            };
        }
    }

    // CLIENTES -----------------------------------------
    // Mostrar Clientes
    public List<Customer> getAllCustomers() {
        String query = "SELECT c.id, c.person_id, p.first_name, p.last_name, c.email, c.address, c.phone " +
                "FROM customer c " +
                "INNER JOIN person p ON c.person_id = p.id";
        return PostgresTemplate.query(query, new CustomerMapper());
    }

    // Seleccionar Cliente Por Id
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

    // Guardar Cliente
    public int addCustomer(Customer customer) {
        String personQuery = "INSERT INTO person (first_name, last_name) VALUES (?, ?) RETURNING id";
        int personId = PostgresTemplate.queryForObject(personQuery, new Object[] {
                customer.getFirst_name(),
                customer.getLast_name()
        }, Integer.class);

        String customerQuery = "INSERT INTO customer (person_id, address, phone, email) VALUES (?, ?, ?, ?)";
        return PostgresTemplate.update(customerQuery,
                personId,
                customer.getAddress(),
                customer.getPhone(),
                customer.getEmail());
    }

    // Eliminar Cliente
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

    // Actualizar Cliente
    public int updateCustomer(Customer customer) {
        String personQuery = "UPDATE person SET first_name = ?, last_name = ? WHERE id = (SELECT person_id FROM customer WHERE id = ?)";
        PostgresTemplate.update(personQuery, customer.getFirst_name(), customer.getLast_name(), customer.getId());

        String customerQuery = "UPDATE customer SET email = ?, address = ?, phone = ? WHERE id = ?";
        return PostgresTemplate.update(customerQuery, customer.getEmail(), customer.getAddress(),
                customer.getPhone(), customer.getId());
    }

    // Buscar Clientes
    public List<Customer> searchCustomers(String query) {
        String sql = "SELECT c.id, c.person_id, p.first_name, p.last_name, c.phone, c.address, c.email " +
                "FROM customer c " +
                "INNER JOIN person p ON c.person_id = p.id " +
                "WHERE LOWER(p.first_name) LIKE LOWER(?) OR LOWER(p.last_name) LIKE LOWER(?)";

        return PostgresTemplate.query(sql, new Object[] { "%" + query + "%", "%" + query + "%" }, new CustomerMapper());
    }

    // SUPPLIERS -----------------------------------------

    // Mostrar Proovedores
    public List<Provider> getAllSuppliers() {
        String query = "SELECT c.id, c.person_id, p.first_name, p.last_name, c.email, c.address, c.phone, c.company, c.city, c.zip_code FROM provider c INNER JOIN person p ON c.person_id = p.id";
        return PostgresTemplate.query(query, new ProviderMapper());
    }

    // Guardar Proovedor
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

    // Seleccionar Proovedor Por Id
    @SuppressWarnings("deprecation")
    public Provider getProviderById(int id) {
        String query = "SELECT c.id, c.person_id, p.last_name, c.address, c.phone, p.first_name, c.email, c.company, c.city, c.zip_code "
                +
                "FROM provider c INNER JOIN person p ON c.person_id = p.id " +
                "WHERE c.id = ?";

        return PostgresTemplate.queryForObject(query, new Object[] { id }, (rs, rowNum) -> {
            Provider provider = new Provider();
            provider.setId(rs.getInt("id"));
            provider.setPerson_id(rs.getInt("person_id"));
            provider.setLast_name(rs.getString("last_name"));
            provider.setAddress(rs.getString("address"));
            provider.setPhone(rs.getString("phone"));
            provider.setFirst_name(rs.getString("first_name"));
            provider.setEmail(rs.getString("email"));
            provider.setCompany(rs.getString("company"));
            provider.setCity(rs.getString("city"));
            provider.setZip_code(rs.getString("zip_code"));
            return provider;
        });
    }

    // Eliminar Proovedor
    public int deleteProvider(int id) {
        // Obtener el person_id antes de eliminar el cliente
        String getPersonIdQuery = "SELECT person_id FROM provider WHERE id = ?";
        Integer personId = PostgresTemplate.queryForObject(getPersonIdQuery, new Object[] { id }, Integer.class);

        if (personId != null) {
            String customerQuery = "DELETE FROM provider WHERE id = ?";
            PostgresTemplate.update(customerQuery, id);

            String personQuery = "DELETE FROM person WHERE id = ?";
            return PostgresTemplate.update(personQuery, personId);
        }

        return 0;
    }

    // Actualizar Proovedor
    public int updateProvider(Provider provider) {
        String personQuery = "UPDATE person SET first_name = ?, last_name = ? WHERE id = (SELECT person_id FROM provider WHERE id = ?)";
        PostgresTemplate.update(personQuery, provider.getFirst_name(), provider.getLast_name(), provider.getId());

        String providerQuery = "UPDATE provider SET email = ?, address = ?, phone = ?, city = ?  , company = ?, zip_code =? WHERE id = ?";
        return PostgresTemplate.update(providerQuery, provider.getEmail(), provider.getAddress(),
                provider.getPhone(), provider.getCity(), provider.getCompany(), provider.getZip_code(),
                provider.getId());
    }

    // ORDENES -----------------------------------------
    // Mostrar Ordenes
    public List<OrderDetail> getAllOrders() {
        String query = "SELECT o.id, o.customer_id, o.product_id, o.order_date, o.amount, o.total, o.status, " +
                "p.first_name, pr.name " +
                "FROM orders o " +
                "INNER JOIN customer c ON o.customer_id = c.id " +
                "INNER JOIN person p ON c.person_id = p.id " +
                "INNER JOIN product pr ON o.product_id = pr.id " +
                "ORDER BY o.order_date DESC";
        return PostgresTemplate.query(query, new OrderDetailMapper());
    }

    // Obtener Orden Por ID
    public OrderDetail getOrderById(int id) {
        String query = "SELECT o.id, o.customer_id, o.product_id, o.order_date, o.amount, o.total, o.status, " +
                "p.first_name, p.last_name, pr.name " +
                "FROM orders o " +
                "INNER JOIN customer c ON o.customer_id = c.id " +
                "INNER JOIN person p ON c.person_id = p.id " +
                "INNER JOIN product pr ON o.product_id = pr.id " +
                "WHERE o.id = ?";

        return PostgresTemplate.queryForObject(query, new Object[] { id }, new OrderDetailMapper());
    }

    // Crear Nueva Orden
    public int addOrder(Order order) {
        if (order.getCustomerId() == 0 || order.getCustomerId() <= 0) {
            System.out.println("El customer_id no puede ser nulo o 0.");
        }

        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }

        if (order.getAmount() <= 0) {
            System.out.println("Amount must be greater than 0");
        }

        if (order.getStatus() == null || order.getStatus().trim().isEmpty()) {
            order.setStatus("Pending");
        }
        String query = "INSERT INTO orders (customer_id, product_id, amount, total, order_date, status) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        return PostgresTemplate.queryForObject(query, new Object[] {
                order.getCustomerId(),
                order.getProductId(),
                order.getAmount(),
                order.getTotal(),
                order.getOrderDate(),
                order.getStatus()
        }, Integer.class);
    }

    // Actualizar Orden
    public int updateOrder(OrderDetail orderDetail) {
        // Primero verificar que el cliente y producto existen
        Integer customerExists = PostgresTemplate.queryForObject(
                "SELECT 1 FROM customer WHERE id = ?", Integer.class, orderDetail.getCustomerId());

        Integer productExists = PostgresTemplate.queryForObject(
                "SELECT 1 FROM product WHERE id = ?", Integer.class, orderDetail.getProductId());

        if (customerExists == null || productExists == null) {
            throw new IllegalArgumentException("Cliente o producto no existen");
        }

        java.util.Date orderDate = null;
        if (orderDetail.getOrderDate() != null) {
            orderDate = java.util.Date.from(
                    orderDetail.getOrderDate().atZone(java.time.ZoneId.systemDefault()).toInstant());
        }

        // Luego realizar la actualización
        String query = "UPDATE orders SET customer_id = ?, product_id = ?, order_date = ?, amount = ?, total = ? WHERE id = ?";
        return PostgresTemplate.update(query,
                orderDetail.getCustomerId(),
                orderDetail.getProductId(),
                orderDate,
                orderDetail.getAmount(),
                orderDetail.getTotal(),
                orderDetail.getId());
    }

    // Eliminar Orden
    public int deleteOrder(int id) {
        String query = "DELETE FROM orders WHERE id = ?";
        return PostgresTemplate.update(query, id);
    }

    // PRODUCTOS -----------------------------------------

    // Buscar Productos
    public List<Product> getAllProducts() {
        String query = "SELECT id, name FROM product ORDER BY name ASC";
        return PostgresTemplate.query(query, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            return product;
        });
    }

    // Buscar Datos Productos
    public Product getDetallesProductos(int id) {
        String query = "SELECT id, name, price, stock, description FROM product WHERE id = ?";
        return PostgresTemplate.queryForObject(query, new Object[] { id }, new ProductMapper());
    }

    // Actualizar Stock
    public int updateStock(int productId, int quantity) {
        String query = "UPDATE product SET stock = stock - ? WHERE id = ?";
        return PostgresTemplate.update(query, quantity, productId);
    }

}
