package ru.job4j.hibernate.hql;

import ru.job4j.hibernate.hql.model.Candidate;
import ru.job4j.hibernate.hql.store.CandidateHQLStore;

import java.util.List;
import java.util.stream.Collectors;

public class StartUI {

    public static void main(String[] args) {

        CandidateHQLStore store = new CandidateHQLStore();
        List<Candidate> candidates = List.of(
                new Candidate("Ivan", 2, 150000),
                new Candidate("Cyrill", 3, 200000),
                new Candidate("Olga", 3, 200000),
                new Candidate("Ivan", 4, 250000),
                new Candidate("Irina", 2, 150000)
        );

        candidates = candidates
                .stream()
                .map(store::create)
                .collect(Collectors.toList());
        System.out.println("All candidates:" + System.lineSeparator()
                + candidates + System.lineSeparator());

        Candidate candidate = candidates.get(1);
        System.out.println("Before updating: " + candidate);
        candidate.setSalary(220000);
        store.update(candidate);
        System.out.println("After updating: " + store.findById(candidate.getId()));

        store.delete(candidate.getId());
        candidates = store.findAll();
        System.out.println("After deleting:" + candidates);

        candidate = candidates.get(1);
        long id = candidate.getId();
        System.out.println("Candidate with id " + id + " is:"
                + System.lineSeparator() + store.findById(id));

        System.out.println("Candidates with name Ivan:"
                + System.lineSeparator() + store.findByName("Ivan"));

        store.deleteAll();
        System.out.println("All candidates deleted." + store.findAll());
    }
}
