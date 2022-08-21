package ru.job4j.hibernate.hql.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vacancy_store")
public class VacancyStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vacancy> vacancies = new HashSet<>();

    public VacancyStore() {
    }

    public VacancyStore(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public VacancyStore(int id, Set<Vacancy> vacancies) {
        this.id = id;
        this.vacancies = vacancies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VacancyStore)) {
            return false;
        }
        VacancyStore that = (VacancyStore) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String n = System.lineSeparator();
        return String.format(" id: %d%s vacancies:%s%s%s",
                id, n, n, vacancies.toString(), n);
    }
}
