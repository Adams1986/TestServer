package example;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by ADI on 16-10-2015.
 */
// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld {

    private User user;
    private SQLWrapper sqlWrapper = new SQLWrapper();
    private ArrayList<User> users = sqlWrapper.getUsers();

    @Path("/user1")
    @GET
    @Produces("application/json")
    public String getUser(){

        user = users.get(0);

        return user.toJson();
    }

    //Case sensitive for the username!
    @Path("/loginauthorization/{username}")
    @GET
    @Produces("application/json")
    public String authorizeLogin(@PathParam("username") String username){

        User userAsJson = null;

        for (int i = 0; i < users.size(); i++) {

            if(username.equals(users.get(i).getUsername())){

                userAsJson = users.get(i);
            }

        }
        //System.out.println(userAsJson);
        return userAsJson.toJson();
    }

//    @Path("/update/{username}")
//    @PUT
//    @Produces("application/json")
//    public Response updateUser(@PathParam("username") String username){
//
//        User updatedUser = null;
//
//        for(User u : users) {
//            if (username.equals(u.getUsername())){
//
//                updatedUser = u;
//                updatedUser.setPassword("1234");
//                break;
//            }
//        }
//        sqlWrapper.updateUser(updatedUser);
//
//        return Response.status(200).entity("User updated").build();
//    }

    @Path("/updateuser/")
    @POST
    @Consumes("application/json")
    public Response updateUser(String jsonUser){

        String decryptedUser = new Security().decrypt(jsonUser, "1");
        User updatedUser = new Gson().fromJson(decryptedUser, User.class);

        for(User u : users) {
            if (updatedUser.getUsername().equals(u.getUsername())){

                updatedUser = u;
                break;
            }
        }
        sqlWrapper.updateUser(updatedUser);

        return Response.status(200).entity("User was updated").build();
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:998/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:998/helloworld/user1");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}
