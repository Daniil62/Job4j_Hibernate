package ru.job4j.hibernate.mapping.tomany.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Model() {
    }

    public Model(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Model)) {
            return false;
        }
        Model model = (Model) o;
        return id == model.id && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id);
        result += 31 + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String n = System.lineSeparator();
        return String.format("model: %s%s id: %d%s", name, n, id, n);
    }
}
