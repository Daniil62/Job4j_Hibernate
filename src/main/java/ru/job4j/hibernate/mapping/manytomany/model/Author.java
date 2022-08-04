package ru.job4j.hibernate.mapping.manytomany.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean add(Book book) {
        return books.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Author)) {
            return true;
        }
        Author author = (Author) o;
        return id == author.id && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) * (31 + name.hashCode());
    }

    @Override
    public String toString() {
        String n = System.lineSeparator();
        return String.format("   Author %s id: %d%s name: %s%s books: %s%s",
                n, id, n, name, n, books.toString(), n);
    }
}
