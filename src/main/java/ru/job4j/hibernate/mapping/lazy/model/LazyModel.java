package ru.job4j.hibernate.mapping.lazy.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lazy_model")
public class LazyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "mark_id")
    private LazyMark mark;

    public LazyModel() {
    }

    public LazyModel(String name) {
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

    public LazyMark getMark() {
        return mark;
    }

    public void setMark(LazyMark mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LazyModel)) {
            return false;
        }
        LazyModel model = (LazyModel) o;
        return id == model.id && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) * (31 + name.hashCode());
    }

    @Override
    public String toString() {
        String n = System.lineSeparator();
        return String.format("model: %s%s id: %d%s", name, n, id, n);
    }
}
