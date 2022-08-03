package ru.job4j.hibernate.mapping.tomany.ui;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.mapping.tomany.model.Mark;
import ru.job4j.hibernate.mapping.tomany.model.Model;

import java.util.List;

public class Run {

    public static void main(String[] args) {
        List<Model> models = List.of(
                new Model("Countach"),
                new Model("Diablo"),
                new Model("Murcielago"),
                new Model("Gallardo"),
                new Model("Aventador"));

        Mark mark = new Mark("Lamborghini");

        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        try (SessionFactory factory =
                     new MetadataSources(registry)
                             .buildMetadata()
                             .buildSessionFactory();
             Session session = factory.openSession()
        ) {
            session.beginTransaction();
            for (Model model : models) {
                mark.addModel(session.load(Model.class, session.save(model)));
            }
            session.save(mark);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(mark);
    }
}