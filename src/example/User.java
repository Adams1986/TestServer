package example;

import java.sql.Date;


/**
 * Created by Oscar on 12-10-2015.
 */
//
public class User {

    //creating variables
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Date created;
    private String status;
    private String email;
    private String type;


    public User (int id, String firstName, String lastName, String email, String userName, String password, Date created, String status, String type ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = userName;
        this.password = password;
        this.created = created;
        this.status = status;
        this.type = type;
    }

    public User(int id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User () {}

    //creating get and set method for all the variables, so they can be used by other classes


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String toJson() {

        String s = String.format("{ \"id\" : %d, \"username\" : \"%s\", \"firstName\" : \"%s\", " +
                "\"lastName\" : \"%s\", \"password\" : \"%s\" }", id, username, firstName, lastName, password);

        Security security = new Security();

        String encryptedUser = security.encrypt(s, "1");

        return encryptedUser;
    }
}