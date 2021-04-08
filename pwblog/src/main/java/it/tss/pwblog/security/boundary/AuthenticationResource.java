/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.pwblog.security.boundary;

import it.tss.pwblog.blog.control.BlogUserStore;
import it.tss.pwblog.blog.entity.BlogUser;
import it.tss.pwblog.security.control.JWTManager;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author User
 */
@Path("/auth")
public class AuthenticationResource {

    @Inject
    BlogUserStore store;

    @Inject
    JWTManager jwtManager;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("email") String email, @FormParam("pwd") String pwd) {
        BlogUser found = store.findUserByEmailAndPwd(email, pwd).orElseThrow(() -> new NotAuthorizedException("invalid user or password", Response.status(Response.Status.UNAUTHORIZED).build()));
        return Response.ok().entity(jwtManager.generate(found)).build();
    }

}
