package ru.job4j.hibernate.hql.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.hql.model.Candidate;

import java.util.List;

public class CandidateHQLStore implements AutoCloseable {

    private final StandardServiceRegistry registry;
    private final Session session;

    public CandidateHQLStore() {
        registry = new StandardServiceRegistryBuilder()
                .configure().build();
        SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        session = sf.openSession();
    }

    public Candidate create(Candidate candidate) {
        session.beginTransaction();
        session.save(candidate);
        session.getTransaction().commit();
        return candidate;
    }

    public void update(Candidate candidate) {
        session.beginTransaction();
        session.update(candidate);
        session.getTransaction().commit();
    }

    public void delete(long id) {
        session.beginTransaction();
        session.createQuery("delete from Candidate where id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
    }

    public List<Candidate> findAll() {
        session.beginTransaction();
        List result = session.createQuery("from Candidate").list();
        session.getTransaction().commit();
        return result;
    }

    public Candidate findById(long id) {
        session.beginTransaction();
        Candidate result = session.createQuery(
                "select distinct c from Candidate c "
                        + "join fetch c.store s join fetch s.vacancies v "
                        + "where c.id = :id", Candidate.class)
                .setParameter("id", id)
                .uniqueResult();
        session.getTransaction().commit();
        return result;
    }

    public List<Candidate> findByName(String name) {
        session.beginTransaction();
        List result = session.createQuery(
                "select distinct c from Candidate c "
                        + "join fetch c.store s join fetch s.vacancies v "
                        + "where c.name = :name", Candidate.class)
                .setParameter("name", name)
                .list();
        session.getTransaction().commit();
        return result;
    }

    public void deleteAll() {
        session.beginTransaction();
        session.createQuery("delete from Candidate").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void close() {
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}