/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.control;

import it.tss.pwblog.blog.boundary.dto.ArticleUpdate;
import it.tss.pwblog.blog.entity.Article;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author User
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class ArticleStore {

    @PersistenceContext
    private EntityManager em;

    public Article createArt(Article u) {
        return em.merge(u);
    }

    private List<Article> searchQuery(Long id, String title, LocalDate ffrom, LocalDate tto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> query = cb.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);
        query.select(root).where(searchPredicate(cb, root, id, title, ffrom, tto));
        return em.createQuery(query)
                .getResultList();
    }

    public Predicate searchPredicate(CriteriaBuilder cb, Root<Article> root, Long id, String title, LocalDate ffrom, LocalDate tto) {
        Predicate result = cb.conjunction();
        if (id != null) {
            result = cb.and(result, cb.equal(root.get("id"), id));
        }
        if (title != null) {
            result = cb.and(result, cb.equal(root.get("title"), title));
        }
        if (ffrom != null && tto != null) {
            result = cb.and(result, cb.greaterThanOrEqualTo(root.get("createdOn"), ffrom), cb.lessThanOrEqualTo(root.get("createdOn"), tto));
        }
        return result;
    }

    public Optional<List<Article>> findAllArticles() {
        List found = searchQuery(null, null, null, null);
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<Article> findArticleById(Long id) {
        Article found = searchQuery(id, null, null, null).get(0);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public Article update(Article article, ArticleUpdate u) {
        article.setTitle(u);
        article.setText(u);
        article.setTags(u);
        return em.merge(article);
    }

    public Optional<List<Article>> findArticlesByPeriod(LocalDate from, LocalDate to) {
        List found = searchQuery(null, null, from, to);
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<List<Article>> findArticlesByTitle(String title) {
        List found = searchQuery(null, title, null, null);
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }
}
