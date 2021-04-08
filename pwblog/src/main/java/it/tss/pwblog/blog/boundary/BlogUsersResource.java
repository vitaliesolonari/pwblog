/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary;

import it.tss.pwblog.blog.boundary.dto.BlogUserCreate;
import it.tss.pwblog.blog.control.BlogUserStore;
import it.tss.pwblog.blog.control.CommentStore;
import it.tss.pwblog.blog.entity.BlogUser;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author User
 */
@DenyAll
@Path("/users")
public class BlogUsersResource {

    @Context
    private ResourceContext resource;

    @Context
    private UriInfo uriInfo;

    @Inject
    private BlogUserStore store;

    @Context
    SecurityContext securityCtx;

    @Inject
    JsonWebToken jwt;

    @PostConstruct
    public void init() {
        System.out.println(uriInfo.getPath());
        System.out.println(uriInfo.getBaseUri());
        System.out.println(uriInfo.getAbsolutePath());
    }

    @RolesAllowed({"ADMIN"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BlogUser> searchAllUsers() {
        return store.findAllUsers();
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid BlogUserCreate u) {
        BlogUser created = store.createUsr(new BlogUser(u));
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @RolesAllowed({"ADMIN", "USER"})
    @Path("{userId}")
    public BlogUserResource search(@PathParam("userId") Long id) {
        boolean isUserRole = securityCtx.isUserInRole(BlogUser.Role.USER.name());
        if (isUserRole && (jwt == null || jwt.getSubject() == null || Long.parseLong(jwt.getSubject()) != id)) {
            throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity("Access forbidden: role not allowed").build());
        }
        BlogUserResource sub = resource.getResource(BlogUserResource.class);
        sub.setUserId(id);
        return sub;
    }

}
