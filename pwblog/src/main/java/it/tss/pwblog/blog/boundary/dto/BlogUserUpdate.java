/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary.dto;

import it.tss.pwblog.blog.entity.BlogUser.Role;

/**
 *
 * @author User
 */
public class BlogUserUpdate {

    public String fname;
    public String lname;
    public String email;
    public String pwd;
    public Role role;
    public boolean banned;

    public BlogUserUpdate() {
    }

    public BlogUserUpdate(String fname, String lname, String email, String pwd, Role role, boolean banned) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
        this.banned = banned;
    }

}
