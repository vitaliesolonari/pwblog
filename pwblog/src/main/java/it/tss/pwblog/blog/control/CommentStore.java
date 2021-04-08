/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.control;

import it.tss.pwblog.blog.entity.Comment;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/**
 *
 * @author User
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class CommentStore {

    @PersistenceContext
    private EntityManager em;

    private List<Comment> searchQuery(boolean deleted, Long createdById, Long articleId, Long commentId, Long answersTo, LocalDate ffrom, LocalDate tto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);
        query.select(root).where(searchPredicate(cb, root, deleted, createdById, articleId, commentId, answersTo, ffrom, tto));
        return em.createQuery(query)
                .getResultList();

    }

    public Predicate searchPredicate(CriteriaBuilder cb, Root<Comment> root, boolean deleted, Long createdById, Long articleId, Long commentId, Long answersTo, LocalDate ffrom, LocalDate tto) {
        Predicate result = cb.conjunction();
        result = cb.and(cb.equal(root.get("deleted"), deleted));
        if (createdById != null) {

            result = cb.and(result, cb.equal(root.get("createdById"), createdById));
        }
        if (articleId != null) {
            result = cb.and(result, cb.equal(root.get("articleId"), articleId));
        }
        if (commentId != null) {
            result = cb.and(result, cb.equal(root.get("id"), commentId));
        }
        if (answersTo != null) {
            result = cb.and(result, cb.equal(root.get("answersTo"), answersTo));
        }
        if (ffrom != null && tto != null) {
            result = cb.and(result, cb.greaterThanOrEqualTo(root.get("createdOn"), ffrom), cb.lessThanOrEqualTo(root.get("createdOn"), tto));
        }
        return result;
    }

    public Optional<List<Comment>> findAllComments() {
        List found = searchQuery(false, null, null, null, null, null, null);
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<List<Comment>> findCommentsByUser(Long userId) {
        List found = searchQuery(false, userId, null, null, null, null, null);
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<List<Comment>> findCommentsByArticle(Long articleId) {
        List found = searchQuery(false, null, articleId, null, null, null, null);
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<Comment> findCommentById(Long commentId) {
        Comment found = searchQuery(false, null, null, commentId, null, null, null).get(0);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public Optional<List<Comment>> findResponsesToComment(Long answerTo) {
        List found = searchQuery(false, null, null, null, answerTo, null, null);
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

    public Optional<List<Comment>> findCommentsByPeriod(LocalDate from, LocalDate to) {
        List found = searchQuery(false, null, null, null, null, from, to);
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }
}
