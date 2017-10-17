package de.midshing.ticket;

import de.midshing.worklog.Worklog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
@XmlRootElement
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @XmlElementWrapper(name = "worklogs")
    @OneToMany
    private Set<Worklog> worklogs = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Worklog> getWorklogs() {
        return worklogs;
    }

    public void setWorklogs(Set<Worklog> worklogs) {
        this.worklogs = worklogs;
    }
}
