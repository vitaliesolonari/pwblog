/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary;

import it.tss.pwblog.blog.control.CommentStore;
import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author indra
 */
@DenyAll
@Path("/comments")
public class CommentsResource {

    @Context
    private ResourceContext resource;

    @Context
    private UriInfo uriInfo;

    @Context
    SecurityContext securityCtx;

    @Inject
    private CommentStore store;

    @PostConstruct
    public void init() {
        System.out.println(uriInfo.getPath());
        System.out.println(uriInfo.getBaseUri());
        System.out.println(uriInfo.getAbsolutePath());
    }

    @RolesAllowed({"ADMIN", "USER"})
    @Path("{commentId}")
    public CommentResource search(@PathParam("commentId") Long id) {
        CommentResource sub = resource.getResource(CommentResource.class);
        sub.setCommentId(id);
        return sub;
    }

}
