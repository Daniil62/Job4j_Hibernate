package ru.job4j.hibernate.mapping.tomany.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mark")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Model> models = new ArrayList<>();

    public Mark() {
    }

    public Mark(String name) {
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

    public void addModel(Model model) {
        models.add(model);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Mark)) {
            return false;
        }
        Mark mark = (Mark) o;
        return id == mark.id && name.equals(mark.name);
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
        return String.format(
                "id: %d%s mark: %s%s models: %s%s%s",
                id, n, name, n, n, models.toString(), n);
    }
}
