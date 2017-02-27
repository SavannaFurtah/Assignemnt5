/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildit;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c0656308
 */
public class DBUtils {
    
    public final static String SALT = "SaltHash";
    
    public static String hash(String password){
        try {
            String salted = password+SALT;
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] hash = md.digest (salted.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b: hash){
                String hex = Integer.toHexString(b & 0xff).toUpperCase();
                if (hex.length() == 1) sb.append("0");
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex){
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }

    public static Connection getConnection() throws SQLException{
       try {
           Class.forName("com.mysql.jdbc.Driver");
       } catch (ClassNotFoundException ex){
           Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex); 
       }

       String jdbc = "jdbc:mysql://IPRO/Feb132017";
       String username = "Feb132017";
       String password = "Feb132017";
       
       return DriverManager.getConnection(jdbc, username, password);
    }
}
