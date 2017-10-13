package de.midshing.project.error;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import de.midshing.project.Project;

@Stateless
public class ProjectRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Project> getAll() {
        Query query = em.createQuery("SELECT p from Project p");
        return query.getResultList();
    }

    public Project get(long projectId) {
        return em.find(Project.class, projectId);
    }
}
