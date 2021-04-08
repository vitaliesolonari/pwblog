/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.entity;

import it.tss.pwblog.blog.boundary.dto.BlogUserCreate;
import it.tss.pwblog.blog.boundary.dto.BlogUserUpdate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@NamedQueries({
    @NamedQuery(name = BlogUser.LOGIN, query = "select e from BlogUser e where e.email= :email and e.pwd= :pwd and e.banned=false")
})
@Entity
@Table(name = "users")
public class BlogUser extends AbstractEntity {

    public static final String LOGIN = "User.login";
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "user_sequence")
    protected Long id;

    @Column(nullable = false)
    private String fname;
    @Column(nullable = false)
    private String lname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String pwd;

    public enum Role {
        ADMIN, USER
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false)
    private boolean banned = false;

    public BlogUser() {
    }

    public BlogUser(BlogUserCreate u) {
        this.fname = u.fname;
        this.lname = u.lname;
        this.email = u.email;
        this.pwd = u.pwd;
        this.role = u.role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFname(BlogUserUpdate u) {
        setFname(u.fname == null ? this.fname : u.fname);
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setLname(BlogUserUpdate u) {
        setLname(u.lname == null ? this.lname : u.lname);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail(BlogUserUpdate u) {
        setEmail(u.email == null ? this.email : u.email);
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPwd(BlogUserUpdate u) {
        setPwd(u.pwd == null ? this.pwd : u.pwd);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(BlogUserUpdate u) {
        setRole(u.role == null ? this.role : u.role);
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public void setBanned(BlogUserUpdate u) {
        setBanned(u.banned);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final BlogUser other = (BlogUser) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
