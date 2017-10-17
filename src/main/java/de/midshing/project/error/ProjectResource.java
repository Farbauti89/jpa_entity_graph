package de.midshing.project.error;

import de.midshing.project.Project;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("projects/error")
public class ProjectResource {

    @Inject
    private ProjectRepository projectRepository;

    @GET
    @Produces("application/json")
    public Response getProjectsWithError() {
        List<Project> projects = projectRepository.getAll();
        return Response.ok(projects).build();
    }

    @GET
    @Path("/{projectId}")
    @Produces("application/json")
    public Response getProject(@PathParam("projectId") long projectId) {
        Project project = projectRepository.get(projectId);
        return Response.ok(project).build();
    }
}
