/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.control;

import it.tss.pwblog.blog.boundary.dto.ArticleUpdate;
import it.tss.pwblog.blog.entity.Article;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author indra
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class ArticleStore {

    @PersistenceContext
    private EntityManager em;

    @Inject
    @ConfigProperty(name = "maxResult", defaultValue = "10")
    int maxResult;

    public Article createArt(Article u) {
        return em.merge(u);
    }

    private TypedQuery<Article> searchQuery(Long id, String title, LocalDateTime ffrom, LocalDateTime tto) {
        return em.createQuery("SELECT E FROM Article E WHERE E.id :id AND E.title LIKE :title AND E.createdOn >= ffrom AND E.createdOn <= tto ORDER BY E.id ", Article.class)
                .setParameter("title", title == null ? "%" : "%" + title + "%")
                .setParameter("id", id == null ? "%" : id)
                .setParameter("from", ffrom == null ? "%" : ffrom)
                .setParameter("to", tto == null ? "%" : tto);
    }

    public Optional<List<Article>> findAllArticles(int start, int maxResult) {
        List found = searchQuery(null, null, null, null)
                .setFirstResult(start)
                .setMaxResults(maxResult == 0 ? this.maxResult : maxResult)
                .getResultList();
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

    public Optional<Article> findArticleById(Long id) {
        Article found = em.find(Article.class, id);
        return found == null ? Optional.empty() : Optional.of(found);

    }

    public Article update(Article article, ArticleUpdate u) {
        article.setTitle(u);
        article.setText(u);
        article.setTags(u);
        return em.merge(article);
    }

    public Optional<List<Article>> findArticlesByPeriod(LocalDateTime from, LocalDateTime to) {
        List found = searchQuery(null, null, from, to).getResultList();
        return found.isEmpty() ? Optional.empty() : Optional.of(found);
    }

}