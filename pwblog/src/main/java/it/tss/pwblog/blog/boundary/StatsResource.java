/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary;

import it.tss.pwblog.blog.control.ArticleStore;
import it.tss.pwblog.blog.control.CommentStore;
import it.tss.pwblog.blog.entity.Article;
import it.tss.pwblog.blog.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author indra
 */
@PermitAll
@Path("/stats")
public class StatsResource {

    @Inject
    private CommentStore commentStore;

    @Inject
    private ArticleStore articleStore;

    @GET
    @Path("/comments/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> commentsStats(@PathParam("from") LocalDateTime from, @PathParam("to") LocalDateTime to) {
        return commentStore.findCommentsByPeriod(from, to).orElseThrow(() -> new NotFoundException());
    }

    @GET
    @Path("articles/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> articlesStats(@PathParam("from") LocalDateTime from, @PathParam("to") LocalDateTime to) {
        return articleStore.findArticlesByPeriod(from, to).orElseThrow(() -> new NotFoundException());
    }

}
