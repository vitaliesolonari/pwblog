/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author User
 */
public class ArticleCreate {

    @NotEmpty
    public String title;
    @NotEmpty
    public String text;
    @NotEmpty
    public List<String> tags = new ArrayList<>();
}
