/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ejb.UserBeanLocal;
import entity.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

/**
 *
 * @author Acer
 *
 */
@RolesAllowed("chief")
@Path("/user")
public class UserService {
    @EJB UserBeanLocal local;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(@RequestBody User user){
        local.addUser(user);
        return Response.ok().build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        List<User> users = local.getAllUsers();
        return Response.ok(users).build();
    }
}
