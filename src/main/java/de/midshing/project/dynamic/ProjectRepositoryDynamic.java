package de.midshing.project.dynamic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import de.midshing.project.Project;

@Stateless
public class ProjectRepositoryDynamic {

    public static final String JAVAX_PERSISTENCE_LOADGRAPH = "javax.persistence.loadgraph";

    @PersistenceContext
    EntityManager em;

    public List<Project> getAllPreloaded(EntityGraph<Project> entityGraph) {

        Query query = em.createQuery(
                "SELECT p from Project p LEFT join p.tickets as t left join t.worklogs as w ORDER BY p.id asc");
        query.setHint(JAVAX_PERSISTENCE_LOADGRAPH, entityGraph);

        return query.getResultList();
    }

    public Project getPreloaded(long projectId, EntityGraph<Project> entityGraph) {

        Map<String, Object> hints = new HashMap<>();
        hints.put(JAVAX_PERSISTENCE_LOADGRAPH, entityGraph);

        return em.find(Project.class, projectId, hints);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
