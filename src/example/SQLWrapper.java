package example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ADI on 20-10-2015.
 */
public class SQLWrapper {

    private Connection connection = null;
    private ResultSet resultSet = null;
    private SQLBuilder sqlBuilder;

    public SQLWrapper(){

        sqlBuilder = new SQLBuilder();

        connection = sqlBuilder.getConnection();

    }

    public User authenticatedUser(String username, String password){

        User user = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.authenticateSqlUser());

            ps.setString(1, username);

            resultSet = ps.executeQuery();

            if(resultSet.first()){

                if(password.equals(resultSet.getString("password"))) {

                    user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                            resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                            resultSet.getDate(7), resultSet.getString(8), resultSet.getString(9)
                    );
                }            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUser(int id){

        User user = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.getSqlUser());

            ps.setInt(1, id);

            resultSet = ps.executeQuery();

            if(resultSet.first()){

                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getDate(7), resultSet.getString(8), resultSet.getString(9)
                );
            }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        return user;
        }

    public ArrayList <User> getUsers(){

        ArrayList <User> data = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sqlBuilder.getSqlUsers());
            resultSet = ps.executeQuery();

            while (resultSet.next()){

                data.add(
                        new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                                resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                                resultSet.getDate(7), resultSet.getString(8), resultSet.getString(9))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return data;
    }

    public int updateUser(User user){

        int result = -1;

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sqlBuilder.updateSqlUser());

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getStatus());
            ps.setString(6, user.getType());
            ps.setInt(7, user.getId());

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int createUser(User user) {

        int result = -1;

        try {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.createSqlUser());

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());


            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void softDeleteUser(User user) {

        int result = -1;

        try {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.softDeleteSqlUser());

            ps.setString(1, user.getUsername());

            result = ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}


