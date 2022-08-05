package ru.job4j.hibernate.mapping.lazy.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "lazy_mark")
public class LazyMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "mark")
    private final List<LazyModel> models = new ArrayList<>();

    public LazyMark() {
    }

    public LazyMark(String name) {
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

    public void addModel(LazyModel model) {
        models.add(model);
    }

    public List<LazyModel> getModels() {
        return List.copyOf(models);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LazyMark)) {
            return false;
        }
        LazyMark mark = (LazyMark) o;
        return id == mark.id && name.equals(mark.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) * (31 + name.hashCode());
    }

    @Override
    public String toString() {
        String n = System.lineSeparator();
        return String.format(
                "id: %d%s mark: %s%s models: %s%s%s",
                id, n, name, n, n, models.toString(), n);
    }
}
