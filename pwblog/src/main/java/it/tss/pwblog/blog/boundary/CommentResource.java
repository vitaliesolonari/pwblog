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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

/**
 *
 * @author User
 */
@RolesAllowed({"ADMIN", "USER"})
public class CommentResource {

    @Inject
    private CommentStore store;

    Long upperCommentId;

    @Inject
    @Claim(standard = Claims.sub)
    Long userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> searchComment() {
        Comment comment = store.findCommentById(upperCommentId).orElseThrow(() -> new NotFoundException());
        List<Comment> responses = new ArrayList<>();
        responses.add(comment);
        store.findResponsesToComment(upperCommentId).get().stream().forEach(v -> responses.add(v));
        return responses;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment() {
        Comment comment = store.findCommentById(upperCommentId).orElseThrow(() -> new NotFoundException());
        comment.setDeleted(true);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAnswerComment(@Valid CommentCreate c) {
        Comment comment = store.findCommentById(upperCommentId).orElseThrow(() -> new NotFoundException());
        Comment created = new Comment(c, userId, comment.getArticleId());
        created.setAnswersTo(upperCommentId);
        created = store.createComm(created);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    /*
    get/set
     */
    public Long getUpperCommentId() {
        return upperCommentId;
    }

    public void setUpperCommentId(Long upperCommentId) {
        this.upperCommentId = upperCommentId;
    }

}
