package de.midshing;

import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.apache.commons.lang.RandomStringUtils;
import de.midshing.project.Project;
import de.midshing.ticket.Ticket;
import de.midshing.worklog.Worklog;

@Singleton
@Startup
public class Initializer {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void initialize() {

        for (int i = 0; i < 2; i++) {
            createRandomProject();
        }

    }

    private Project createRandomProject() {
        Project randomProject = new Project();

        randomProject.setName(RandomStringUtils.random(32, true, true));
        randomProject.setDescription(RandomStringUtils.random(256, true, true));
        for (int i = 0; i < 10; i++) {
            Ticket randomTicket = createRandomTicket();
            randomProject.getTickets().add(randomTicket);
        }
        em.persist(randomProject);

        return randomProject;
    }

    private Ticket createRandomTicket() {
        Ticket randomTicket = new Ticket();

        randomTicket.setTitle(RandomStringUtils.random(32, true, true));
        randomTicket.setDescription(RandomStringUtils.random(256, true, true));
        for (int i = 0; i < 3; i++) {
            Worklog randomWorklog = createRandomWorklog();
            randomTicket.getWorklogs().add(randomWorklog);
        }
        em.persist(randomTicket);

        return randomTicket;
    }

    private Worklog createRandomWorklog() {
        Worklog randomWorklog = new Worklog();

        Random rng = new Random();
        randomWorklog.setDuration(rng.nextDouble());
        em.persist(randomWorklog);

        return randomWorklog;
    }

}
