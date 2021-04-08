/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.entity;

import it.tss.pwblog.blog.boundary.dto.ArticleCreate;
import it.tss.pwblog.blog.boundary.dto.ArticleUpdate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author User
 */
@Entity
@Table(name = "articles")
public class Article extends AbstractEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "article_sequence", sequenceName = "article_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "article_sequence")
    protected Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags = new ArrayList<>();

    public Article() {
    }

    public Article(ArticleCreate a, Long userId) {
        this.title = a.title;
        this.text = a.text;
        this.tags = (ArrayList<String>) a.tags;
        this.createdById = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle(ArticleUpdate u) {
        setTitle(u.title == null ? this.title : u.title);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setText(ArticleUpdate u) {
        setText(u.text == null ? this.text : u.text);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setTags(ArticleUpdate u) {
        setTags(u.tags == null ? this.tags : u.tags);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
