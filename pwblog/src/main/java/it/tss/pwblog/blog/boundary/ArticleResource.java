/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary;

import it.tss.pwblog.blog.boundary.dto.ArticleUpdate;
import it.tss.pwblog.blog.boundary.dto.CommentCreate;
import it.tss.pwblog.blog.control.ArticleStore;
import it.tss.pwblog.blog.control.CommentStore;
import it.tss.pwblog.blog.entity.Article;
import it.tss.pwblog.blog.entity.Comment;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author User
 */
public class ArticleResource {

    @Inject
    @Claim(standard = Claims.sub)
    Long userId;

    @Inject
    private ArticleStore store;

    @Inject
    private CommentStore commentstore;

    private Long articleId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Article searchArticleById() {
        return store.findArticleById(articleId).orElseThrow(() -> new NotFoundException());
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Article updateArticleById(ArticleUpdate a) {
        Article oldArticle = store.findArticleById(articleId).orElseThrow(() -> new NotFoundException());
        Article newArticle = store.update(oldArticle, a);
        return newArticle;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> searchCommentsInArticle() {
        return commentstore.findCommentsByArticle(articleId).orElseThrow(() -> new NotFoundException());
    }

    /**
     *
     * @param c
     * @param userId settato tramite PathParam
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(@Valid CommentCreate c) {
        Comment created = new Comment(c, userId, articleId);
        commentstore.createComm(created);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    /*
    get/set
     */
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

}
