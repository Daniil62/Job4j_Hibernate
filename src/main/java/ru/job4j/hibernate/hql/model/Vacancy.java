package ru.job4j.hibernate.hql.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String header = "";
    private String description = "";

    public Vacancy() {
    }

    public Vacancy(String header, String description) {
        this.header = header;
        this.description = description;
    }

    public Vacancy(int id, String header, String description) {
        this.id = id;
        this.header = header;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vacancy)) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id && header.equals(vacancy.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) * (31 + header.hashCode());
    }

    @Override
    public String toString() {
        String n = System.lineSeparator();
        return String.format("vacancy: %s%s id: %d%s description: %s%s%s",
                header, n, id, n, n, description, n);
    }
}
