/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildit;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 * st
 *
 * @author c0656308
 */
@ApplicationScoped
public class Users {

    private List<User> users = new ArrayList<>();
    private static Users instance;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static Users getInstance() {
        return instance;
    }

    public static void setInstance(Users instance) {
        Users.instance = instance;
    }
    
    public Users() {
        getUsersFromDb();
    }

    private void getUsersFromDb() {
        try (Connection conn = (Connection) DBUtils.getConnection()) {
            users = new ArrayList<>();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("passhash"));

                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            users = new ArrayList<>();
        }
    }
    public int GetIdByUsername(String username){
    for (User u : users){
        if (u.getUsername().equals(username)){
            return u.getId();
        }
    }
    return -1;
}

    public String getUsernameById(int id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u.getUsername();
            }
        }
        return null;
    }
}
