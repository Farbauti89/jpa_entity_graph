package de.midshing.project.lazy;

import de.midshing.project.Project;
import de.midshing.ticket.Ticket;
import de.midshing.worklog.Worklog;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless
public class ProjectRepositoryLazy {

    @PersistenceContext
    private EntityManager em;

    public List<Project> getAllPreloaded() {

        List<Project> projects = em.createQuery("SELECT p from Project p").getResultList();
        for (Project project : projects) {
            preloadProject(project);
        }

        return projects;
    }

    public Project getPreloaded(long projectId) {
        Project project = em.find(Project.class, projectId);
        preloadProject(project);

        return project;
    }

    private void preloadProject(Project project) {
        Set<Ticket> tickets = project.getTickets();
        for (Ticket ticket : tickets) {
            Set<Worklog> worklogs = ticket.getWorklogs();
            worklogs.size();
        }
    }
}
