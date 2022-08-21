package ru.job4j.hibernate.hql.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int experience;
    private int salary;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VacancyStore store;

    public Candidate() {
    }

    public Candidate(long id) {
        this.id = id;
    }

    public Candidate(String name, int experience, int salary, VacancyStore store) {
        this.name = name;
        this.experience = experience;
        this.salary = salary;
        this.store = store;
    }

    public Candidate(long id, String name, int experience, int salary, VacancyStore store) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.salary = salary;
        this.store = store;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public VacancyStore getStore() {
        return store;
    }

    public void setStore(VacancyStore store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Candidate)) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        String n = System.lineSeparator();
        return String.format(" id: %d%s name: %s%s experience: "
                        + "%d%s salary: %d%s vacancies: %s%s%s",
                id, n, name, n, experience, n, salary, n, n, store.toString(), n);
    }
}
