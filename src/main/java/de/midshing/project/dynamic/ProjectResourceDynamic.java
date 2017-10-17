package de.midshing.project.dynamic;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import de.midshing.project.Project;
import de.midshing.ticket.Ticket;

@Path("projects/dynamic")
public class ProjectResourceDynamic {

    @Inject
    private ProjectRepositoryDynamic projectRepositoryDynamic;

    @GET
    @Produces("application/json")
    public Response getProjects() {

        EntityGraph<Project> graph = getProjectEntityGraph();
        List<Project> projects = projectRepositoryDynamic.getAll(graph);

        return Response.ok(projects).build();
    }

    private EntityGraph<Project> getProjectEntityGraph() {
        EntityGraph<Project> graph = projectRepositoryDynamic.getEntityManager().createEntityGraph(Project.class);
        Subgraph<Ticket> ticketSubgraph = graph.addSubgraph("tickets");
        ticketSubgraph.addAttributeNodes("worklogs");

        return graph;
    }

    @GET
    @Path("/{projectId}")
    @Produces("application/json")
    public Response getProject(@PathParam("projectId") long projectId) {
        EntityGraph<Project> graph = getProjectEntityGraph();
        Project project = projectRepositoryDynamic.get(projectId, graph);
        return Response.ok(project).build();
    }
}
