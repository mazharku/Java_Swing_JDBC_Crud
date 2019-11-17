/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.database;

import com.mazhar.model.FriendListModel;
import com.mazhar.model.PostStatusModel;
import com.mazhar.model.RoleSelectionModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author pk
 */
public class DatabaseController {

    private DatabaseInit initDB = new DatabaseInit();
    private Connection con = initDB.getInitialize();
    private ResultSet rs;
    private PreparedStatement pst;
    private Statement st;
    private ArrayList<String> friendlist = new ArrayList<>();

    public List<RoleSelectionModel> getRoleInfo(String rolename) {
        ArrayList<RoleSelectionModel> roles = new ArrayList<>();
        String query = "";
        try {
//            Connection con=DBConnection.getConnectorManager().getDBConnecton();
            if (rolename.equals("GROUP")) {
                query = "SELECT * FROM `grouptable";
            } else {
                query = "SELECT * FROM `friendtable`";
            }

            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                int index = rs.getInt("id");
                String name = rs.getString("Name");
                String link = rs.getString("Link");

                roles.add(new RoleSelectionModel(name, link));
            }
            return roles;
        } catch (Exception e) {
            return null;
        }

    }

    public void addFriendQuery(String Name, String link) {
        String sql = "INSERT INTO `friendtable`(`Name`, `Link`) VALUES ('" + Name + "','" + link + "')";
        friendlist.add(sql);

    }

    public void addGroupQuery(String Name, String link) {
        String sql = "INSERT INTO `grouptable`(`Name`, `Link`) VALUES ('" + Name + "','" + link + "')";
        friendlist.add(sql);
    }

    public void addStatusQuery(String postrole, String link, String message) {
        String sql = "INSERT INTO `posttable`(`postrole`, `postLink`, `message`, `postStatus`) VALUES ('" + postrole + "','" + link + "','" + message + "','0')";
        friendlist.add(sql);
    }

    public void addNotificationQuery(String message) {
        String sql = "INSERT INTO `notificationtable`(`id`, `notification`) VALUES (NULL,'" + message + "')";
        friendlist.add(sql);
    }

    public String getRoleLink(String name, String type) {
        String query = "";
        String groupname = name.trim();
        String link = "";
        try {
            if (type.equals("GROUP")) {
                query = "SELECT * FROM grouptable where Name='" + groupname + "'";
            } else {
                query = "SELECT * FROM friendtable where Name='" + groupname + "'";
            }

            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {

                link = rs.getString("Link");

            }
            return link;
        } catch (Exception e) {
            return null;
        }

    }

    public boolean executebatch() {
        try {
            Statement stmt = con.createStatement();
            for (String query : friendlist) {
                stmt.addBatch(query);
            }
            stmt.executeBatch();
            friendlist.clear();
            return true;

        } catch (SQLException ex) {
            friendlist.clear();
            return false;
        }
    }
    
    public void cleanFriendlist(){
        
        try {
            String sql="DELETE FROM friendtable";
            pst= con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cleanNotificationlist(){
        
        try {
            String sql="DELETE FROM notificationtable";
            pst= con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cleanGrouplist(){
        
        try {
            String sql="DELETE FROM grouptable";
            pst= con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<PostStatusModel> getAllStatus() {
        List<PostStatusModel> statuslist = new ArrayList<>();
        try {
            String query = "SELECT * FROM posttable where postStatus='0'";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                int index= rs.getInt("id");
                String postRole = rs.getString("postrole");
                String postLink = rs.getString("postLink");
                String message = rs.getString("message");
                boolean postStatus= rs.getBoolean("postStatus");
                statuslist.add(new PostStatusModel(index,postRole,postLink,message,postStatus));
            }
            return statuslist;
        } catch (Exception e) {
            return null;
        }
    }
    public void updatePostStatus(int index){
        try{
            String sql="UPDATE posttable SET postStatus=1 WHERE `id`='"+index+"'";
            pst=con.prepareStatement(sql);
            pst.executeUpdate();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Database update fail due to "+e);
        }
    }
   public int faceBookStatus()
   {
       int index=0;
        try {
            String sql="SELECT `id` FROM facebookstatus";
            pst=con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
              index = rs.getInt("id");
            }
            return index;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            return index;
        }
   }
   
   public void loginFacebook(){
        try {
            String sql="UPDATE facebookstatus SET id=1";
            pst=con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   public void logoutFacebook(){
        try {
            String sql="UPDATE facebookstatus SET id=0";
            pst=con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
