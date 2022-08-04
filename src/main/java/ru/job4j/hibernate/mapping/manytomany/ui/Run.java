package ru.job4j.hibernate.mapping.manytomany.ui;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.mapping.manytomany.model.Author;
import ru.job4j.hibernate.mapping.manytomany.model.Book;

public class Run {

    public static void main(String[] args) {

        Author first = new Author("Herbert Shildt");
        Author second  = new Author("Kathy Sierra");
        Author third  = new Author("Bert Bates");
        Author fourth = new Author("Joshua Bloch");
        Author fifth = new Author("Brian Goetz");

        Book one = new Book("Herb Schildt’s Java Programming Cookbook");
        Book two = new Book("Herb Schildt’s C++ Programming Cookbook");
        Book three = new Book("Head First Design Patterns");
        Book four = new Book("Effective Java");
        Book five = new Book("Java Concurrency in Practice");

        first.add(one);
        first.add(two);
        second.add(three);
        third.add(three);
        fourth.add(four);
        fourth.add(five);
        fifth.add(five);

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

            session.persist(first);
            session.persist(second);
            session.persist(third);
            session.persist(fourth);
            session.persist(fifth);

            print(session, "=== Before deleting ===");

            session.remove(fourth);

            print(session, "=== After deleting ===");

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void print(Session session, String message) {
        System.out.println(message
                + System.lineSeparator()
                + session.createQuery("from Author").list());
    }
}