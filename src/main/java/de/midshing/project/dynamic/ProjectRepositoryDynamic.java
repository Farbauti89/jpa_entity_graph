package de.midshing.project.dynamic;

import de.midshing.project.Project;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class ProjectRepositoryDynamic {

    public static final String JAVAX_PERSISTENCE_LOADGRAPH = "javax.persistence.loadgraph";

    @PersistenceContext
    EntityManager em;

    public List<Project> getAll(EntityGraph<Project> entityGraph) {

        Query query = em.createQuery(
                "SELECT distinct p from Project p");
        query.setHint(JAVAX_PERSISTENCE_LOADGRAPH, entityGraph);

        return query.getResultList();
    }

    public Project get(long projectId, EntityGraph<Project> entityGraph) {

        Map<String, Object> hints = new HashMap<>();
        hints.put(JAVAX_PERSISTENCE_LOADGRAPH, entityGraph);

        return em.find(Project.class, projectId, hints);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
