package ru.job4j.hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class OrdersStore {

    private final BasicDataSource pool;

    public OrdersStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Order save(Order order) {
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement(
                     "INSERT INTO orders(name, description, created) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, order.getName());
            pr.setString(2, order.getDescription());
            pr.setTimestamp(3, order.getCreated());
            pr.execute();
            ResultSet id = pr.getGeneratedKeys();
            if (id.next()) {
                order.setId(id.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public Collection<Order> findAll() {
        List<Order> result = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM orders")) {
            result = fetchOrders(pr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Optional<Order> findById(int id) {
        Optional<Order> result = Optional.empty();
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM orders WHERE id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
                pr.setInt(1, id);
            result = fetchSingleOrder(pr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Order> findByName(String name) {
        List<Order> result = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE name = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            result = fetchOrders(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Order> fetchOrders(PreparedStatement ps) throws SQLException {
        List<Order> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new Order(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp(4)));
            }
        }
        return result;
    }

    private Optional<Order> fetchSingleOrder(PreparedStatement ps) throws SQLException {
        Optional<Order> result = Optional.empty();
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                result = Optional.of(new Order(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp(4)
                ));
            }
        }
        return result;
    }

    public boolean update(int id, Order order) {
        boolean result = false;
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement(
                     "UPDATE orders SET name = ?, description = ?, created = ? WHERE id = ?")) {
            pr.setString(1, order.getName());
            pr.setString(2, order.getDescription());
            pr.setTimestamp(3, order.getCreated());
            pr.setInt(4, id);
            result = pr.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
 }