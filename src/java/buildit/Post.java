/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildit;

import java.util.Date;

/**
 *
 * @author c0656308
 */
public class Post {
    private int id;
    private int user_id;
    private String title;
    private Date createdAt;
    private String contents;

    public Post() {
    }

    public Post(int id, int user_id, String title, Date createdAt, String contents) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.createdAt = createdAt;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreated_at(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    
    
}
