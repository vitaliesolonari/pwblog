/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary.dto;

import java.util.List;

/**
 *
 * @author User
 */
public class ArticleUpdate {

    public String title;
    public String text;
    public List<String> tags;

    public ArticleUpdate() {
    }

    public ArticleUpdate(String title, String text, List<String> tags) {
        this.title = title;
        this.text = text;
        this.tags = tags;
    }
}
