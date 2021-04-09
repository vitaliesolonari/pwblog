/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.blog.boundary.adapters;

import it.tss.pwblog.blog.entity.BlogUser;
import javax.json.bind.adapter.JsonbAdapter;

/**
 *
 * @author User
 */
public class RoleTypeAdapter implements JsonbAdapter<BlogUser.Role, String> {

    @Override
    public String adaptToJson(BlogUser.Role r) throws Exception {
        return r.name();
    }

    @Override
    public BlogUser.Role adaptFromJson(String json) throws Exception {
        return BlogUser.Role.valueOf(json);
    }

}
