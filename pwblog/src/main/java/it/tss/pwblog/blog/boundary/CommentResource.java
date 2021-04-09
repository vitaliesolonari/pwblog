/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary;

import it.tss.pwblog.blog.boundary.dto.CommentCreate;
import it.tss.pwblog.blog.control.CommentStore;
import it.tss.pwblog.blog.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author User
 */
@RolesAllowed({"ADMIN", "USER"})
public class CommentResource {

    @Inject
    private CommentStore store;

    Long commentId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> searchComment() {
        Comment comment = store.findCommentById(commentId).orElseThrow(() -> new NotFoundException());
        List<Comment> responses = new ArrayList<>();
        responses.add(comment);
        store.findResponsesToComment(commentId).get().stream().forEach(v -> responses.add(v));
        return responses;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment() {
        Comment comment = store.findCommentById(commentId).orElseThrow(() -> new NotFoundException());
        comment.setDeleted(true);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAnswerComment(@PathParam("commentId") Long upperCommentId, @Valid CommentCreate c) {
        Comment created = store.createComm(new Comment(c));
        created.setAnswersTo(upperCommentId);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

}
