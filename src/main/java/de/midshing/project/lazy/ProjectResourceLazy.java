package de.midshing.project.lazy;

import de.midshing.project.Project;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("projects/lazy")
public class ProjectResourceLazy {

    @Inject
    private ProjectRepositoryLazy projectRepositoryLazy;

    @GET
    @Produces("application/json")
    public Response getProjects() {
        List<Project> projects = projectRepositoryLazy.getAllPreloaded();
        return Response.ok(projects).build();
    }

    @GET
    @Path("/{projectId}")
    @Produces("application/json")
    public Response getProject(@PathParam("projectId") long projectId) {
        Project project = projectRepositoryLazy.getPreloaded(projectId);
        return Response.ok(project).build();
    }
}
