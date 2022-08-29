package ru.job4j.hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrdersStoreTest {

    private final BasicDataSource pool = new BasicDataSource();
    private OrdersStore store;

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/integration_update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).execute();
        store = new OrdersStore(pool);
    }

    @After
    public void finish() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE orders").execute();
        pool.close();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenOrderFoundById() {
        store.save(Order.of("first", "description"));
        store.save(Order.of("second", "description"));
        assertThat(store.findById(1).get().getName(), is("first"));
        assertThat(store.findById(2).get().getName(), is("second"));
    }

    @Test
    public void whenNothingFoundById() {
        store.save(Order.of("some order", "description"));
        assertThat(store.findById(2), is(Optional.empty()));
    }

    @Test
    public void whenSaveOrdersWithDifferentNamesThenFoundByNames() {
        store.save(Order.of("first", "description"));
        store.save(Order.of("second", "description"));
        assertThat(store.findByName("first").get(0).getId(), is(1));
        assertThat(store.findByName("second").get(0).getId(), is(2));
    }

    @Test
    public void whenSaveOrdersWithSameNamesThenFoundByName() {
        store.save(Order.of("some order", "description"));
        store.save(Order.of("some order", "description"));
        List<Order> orders = store.findByName("some order");
        assertThat(orders.size(), is(2));
        assertThat(orders.get(0).getId(), is(1));
        assertThat(orders.get(1).getId(), is(2));
    }

    @Test
    public void whenNothingFoundByName() {
        store.save(Order.of("some order", "description"));
        assertThat(store.findByName("My order"), is(List.of()));
    }

    @Test
    public void whenSaveOrderThenUpdated() {
        store.save(Order.of("new order", "description"));
        assertTrue(store.update(1, Order.of("New Order", "Updated")));
        assertThat(store.findById(1).get().getDescription(), is("Updated"));
    }

    @Test
    public void whenTryToUpdateWithInvalidId() {
        store.save(Order.of("new order", "description"));
        assertFalse(store.update(2, Order.of("New Order", "Updated")));
        assertThat(store.findById(1).get().getDescription(), is("description"));
    }

}