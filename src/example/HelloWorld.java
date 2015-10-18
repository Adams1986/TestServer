package example;
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
    private ArrayList<User> users;

    @Path("/user1")
    @GET
    @Produces("application/json")
    public String getUser(){

        user = new User(1, "Simon", "Adams", "Adi", "12345");

        users = new ArrayList<>();
        users.add(user);

        System.out.println(user.toJson());

        return user.toJson();
    }

    @Path("/loginauthorization/{username}")
    @GET
    @Produces("application/json")
    public String authorizeLogin(@PathParam("username") String username){

        user = new User(1, "Simon", "Adams", "Adi", "12345");

        users = new ArrayList<>();
        users.add(user);

       User userAsJson = null;

        for (int i = 0; i < users.size(); i++) {

            if(username.equals(users.get(i).getUsername())){

                userAsJson = users.get(i);
            }

        }
        System.out.println(userAsJson);
        return userAsJson.toJson();
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
