/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildit;

import com.mysql.jdbc.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author c0656308
 */
@Named
@ApplicationScoped
public class Posts {

    private List<Post> posts = new ArrayList<>();
    private Post currentPost;

    public Posts() {
        getPostsFromDb();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    private int findPostId() {
        int h = 0;
        for (Post p : posts) {
            if (p.getId() > h) {
                h = p.getId();
            }
        }
        return h+1;
    }

    public Post getCurrentPost() {
        return currentPost;
    }

    public void setCurrentPost(Post currentPost) {
        this.currentPost = currentPost;
    }

    public Posts(Post currentPost) {
        this.currentPost = currentPost;
    }

    private void getPostsFromDb() {
        try (Connection conn = (Connection) DBUtils.getConnection()) {
            posts = new ArrayList<>();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM posts");
            while (rs.next()) {
                Post p = new Post(rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getDate("created_time"),
                        rs.getString("contents"));
                posts.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Post getPostById(int id) {
        for (Post p : posts) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public String viewPost(Post post) {
        currentPost = post;
        return "viewPost";
    }

    public String savePost() {
        try (Connection conn = (Connection) DBUtils.getConnection()) {
            String sql = "UPDATE posts SET TITLE =?, contents=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, currentPost.getTitle());
            pstmt.setString(2, currentPost.getContents());
            pstmt.setInt(3, currentPost.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
        }
        getPostsFromDb();
        return "viewPost";
    }

    public String cancelPost() {
        int id = currentPost.getId();
        getPostsFromDb();
        currentPost = getPostById(id);
        return "viewPost";
    }

    public String addPost(String username) {
        int num = findPostId();
        try (Connection conn = (Connection) DBUtils.getConnection()) {
            String sql = "INSERT INTO posts VALUES (" + num + ","
                    + new Users().GetIdByUsername(username) + ",'"
                    + currentPost.getTitle() + "',"
                    + "NOW()" + ",'"
                    + currentPost.getContents() + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            getPostsFromDb();
        currentPost.setId(num);
         return "viewPost";
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return "index";
    }
    
    public String deletePost(Post postDelete){
        try (Connection conn = (Connection) DBUtils.getConnection()) {
            String sql = "DELETE FROM posts WHERE id = " + postDelete.getId(); 
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            getPostsFromDb();
            return "index";
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    public String editPost() {
        return "editPost";
    }
    
    public String newPost(){
        currentPost = new Post();
        
        return "addPost";
    }

}
