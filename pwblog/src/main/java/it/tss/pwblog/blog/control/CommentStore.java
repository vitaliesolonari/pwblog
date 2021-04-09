/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.control;

import it.tss.pwblog.blog.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author indra
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class CommentStore {

    @PersistenceContext
    private EntityManager em;

    private TypedQuery<Comment> searchQuery(boolean deleted, Long userId, Long articleId, Long commentId, Long answersTo, LocalDateTime ffrom, LocalDateTime tto) {
        return em.createQuery("SELECT E FROM Comment E WHERE E.deleted :deleted AND E.userId :userId AND E.articleId :articleId AND E.id :commentId AND E.answersTo :answersTo AND E.createdOn >= ffrom AND E.createdOn <= tto ORDER BY E.id ", Comment.class)
                .setParameter("deleted", deleted)
                .setParameter("userId", userId == null ? "%" : userId)
                .setParameter("articleId", articleId == null ? "%" : articleId)
                .setParameter("id", articleId == null ? "%" : commentId)
                .setParameter("answersTo", answersTo == null ? "%" : answersTo)
                .setParameter("from", ffrom == null ? "%" : ffrom)
                .setParameter("to", tto == null ? "%" : tto);
    }

    public Optional<List<Comment>> findAllComments() {
        List found = searchQuery(false, null, null, null, null, null, null).getResultList();
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<List<Comment>> findCommentsByUser(Long userId) {
        List found = searchQuery(false, userId, null, null, null, null, null)
                .getResultList();
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<List<Comment>> findCommentsByArticle(Long articleId) {
        List found = searchQuery(false, null, articleId, null, null, null, null)
                .getResultList();
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<Comment> findCommentById(Long commentId) {
        Comment found = searchQuery(false, null, null, commentId, null, null, null).getSingleResult();
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public Optional<List<Comment>> findResponsesToComment(Long answerTo) {
        List found = searchQuery(false, null, null, null, answerTo, null, null).getResultList();
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public void deleteComm(Long id) {
        Comment found = em.find(Comment.class, id);
        found.setDeleted(true);
        em.merge(found);
    }

    public Comment createComm(Comment comment) {
        return em.merge(comment);
    }

    public Optional<List<Comment>> findCommentsByPeriod(LocalDateTime from, LocalDateTime to) {
        List found = searchQuery(false, null, null, null, null, from, to).getResultList();
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

}