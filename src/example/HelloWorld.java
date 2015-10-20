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

    //Case sensitive for the username!
    @Path("/loginauthorization/")
    @POST
    @Produces("application/json")
    public Response authorizeLogin(String data){


        User temp = new Gson().fromJson(new Security().decrypt(data, "1"), User.class);
        User userToAuthenticate = sqlWrapper.authenticatedUser(temp.getUsername(), temp.getPassword());


        //System.out.println(userAsJson);
        //return Response.status(200).entity("\"success\" : \"true\"").build();
        return Response.status(200).entity(userToAuthenticate.toEncryptedJson()).build();
    }

    @Path("/createuser/")
    @POST
    @Produces("application/json")
    public Response createUser(String data){


        User userToCreate = new Gson().fromJson(new Security().decrypt(data, "1"), User.class);
        sqlWrapper.createUser(userToCreate);


        //System.out.println(userAsJson);
        //return Response.status(200).entity("\"success\" : \"true\"").build();
        return Response.status(200).entity("user was created").build();
    }

    @Path("/updateuser")
    @PUT
    @Consumes("application/json")
    public Response updateUser(String jsonUser){

        String decryptedUser = new Security().decrypt(jsonUser, "1");
        User updatedUser = new Gson().fromJson(decryptedUser, User.class);

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
