package ru.job4j.hibernate.mapping.lazy.ui;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.mapping.lazy.model.LazyMark;
import ru.job4j.hibernate.mapping.lazy.model.LazyModel;

import java.util.List;

public class Run {

    public static void main(String[] args) {
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        try (SessionFactory factory = new MetadataSources(registry)
                             .buildMetadata()
                             .buildSessionFactory();
             Session session = factory.openSession()
        ) {
            session.beginTransaction();
            print(session.createQuery("from LazyMark ").list());
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void print(List<LazyMark> list) {
        for (LazyMark mark : list) {
            for (LazyModel model : mark.getModels()) {
                System.out.println(model);
            }
        }
    }
}