/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.model;

/**
 *
 * @author mazhar
 */
public class PostStatusModel {
    private int index;
    private String postRole;
    private String postLink;
    private String message;
    private boolean postStatus;

    public PostStatusModel(int index,String postRole, String postLink, String message, boolean postStatus) {
        this.index= index;
        this.postRole = postRole;
        this.postLink = postLink;
        this.message = message;
        this.postStatus = postStatus;
    }

    public String getPostRole() {
        return postRole;
    }

    public void setPostRole(String postRole) {
        this.postRole = postRole;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPostStatus() {
        return postStatus;
    }

    public void setPostStatus(boolean postStatus) {
        this.postStatus = postStatus;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    
}
