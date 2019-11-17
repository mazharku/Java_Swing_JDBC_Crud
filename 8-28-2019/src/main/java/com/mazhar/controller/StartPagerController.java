/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.controller;

import com.mazhar.database.DatabaseController;
import com.mazhar.model.FriendListModel;
import com.mazhar.model.GroupListModel;
import com.mazhar.model.NotificationModel;
import com.mazhar.scrapper.ScrapperClass;
import com.mazhar.view.EditorFrame;
import com.mazhar.view.StartPager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mazhar
 */
public class StartPagerController {
    private DatabaseController datacontrol= new DatabaseController();
    private StartPager startpage;
    private EditorFrame editor= new EditorFrame();
    private EditorController editorcontroller= new EditorController(editor);
    private ScrapperClass facebookscrapper;
    public StartPagerController(StartPager startpage) {
        this.startpage = startpage;
    }
    public void initStartPage(){
        startpage.setVisible(true);
    }
    public void showEditor(){
        startpage.setVisible(false);
        editorcontroller.initEditor();
    }
    public void openBrowser()
    {
        new Thread(() -> {
            startpage.viewmonitor.setText("Browser Opening...");
            try {
                facebookscrapper = new ScrapperClass();
                facebookscrapper.openFacebook();
                startpage.viewmonitor.setText("Browser Open Successfully");
            } catch (Exception ex) {
                startpage.viewmonitor.setText("" + ex);
            }
        }).start();
        

        
    }
    
    public void closeBrowser(){
        
        new Thread(() -> {
            startpage.viewmonitor.setText("Browser closing...");
            try{
                facebookscrapper= new ScrapperClass();
                facebookscrapper.closeWindow();
                startpage.viewmonitor.setText("Browser close Successfully");
            }catch (Exception ex) {
                startpage.viewmonitor.setText(""+ex);
            }
        }).start();
        
    }
   public void  getNotification(){
       
       new Thread(()-> {
            startpage.viewmonitor.setText("Notification loading...");
            datacontrol.cleanNotificationlist();
        try{
            facebookscrapper= new ScrapperClass();
            String message="";
            for(NotificationModel notification:facebookscrapper.saveNotification() ){
               message += notification.getMessage()+"\n";
            }
             startpage.viewmonitor.setText(message);
        }catch (InterruptedException ex) {
            startpage.viewmonitor.setText(""+ex);
        }
       }).start();
       
    }
   public void getFriendList(){
      
       new Thread(()-> {
           startpage.viewmonitor.setText("FriendList loading...");
           datacontrol.cleanFriendlist();
        try{
            facebookscrapper= new ScrapperClass();
             String name="";
            for(FriendListModel friend:facebookscrapper.friendList() ){
               name += friend.getName()+"\n";
            }
             startpage.viewmonitor.setText(name);
        }catch (InterruptedException ex) {
            startpage.viewmonitor.setText(""+ex);
        }
       }).start();
       
   }
   public void getGroupList(){
       new Thread(() -> {
           startpage.viewmonitor.setText("GroupList loading...");
           datacontrol.cleanGrouplist();
           try{
               facebookscrapper= new ScrapperClass();
               //facebookscrapper.groupList();
              String name="";
            for(GroupListModel group:facebookscrapper.groupList() ){
               name += group.getName()+"\n";
            }
             startpage.viewmonitor.setText(name);
           }catch (InterruptedException ex) {
               startpage.viewmonitor.setText(""+ex);
           }
       }).start();
      
   }
   public void logoutFacebbok(){
       new Thread(() -> {
           startpage.viewmonitor.setText("Logging out....");
           try{
               facebookscrapper= new ScrapperClass();
               facebookscrapper.logout();
               startpage.viewmonitor.setText("Logout successfully");
               datacontrol.logoutFacebook();
                startpage.facebookloginBtn.setEnabled(true);
           }catch (Exception ex) {
               startpage.viewmonitor.setText(""+ex);
           }
       }).start();
       
   }
   public void loginFacebook(){
       new Thread(() -> {
           startpage.viewmonitor.setText("Log in....");
           try{
               facebookscrapper= new ScrapperClass();
               facebookscrapper.loginFaceBook();
               startpage.viewmonitor.setText("Log in successfully");
               datacontrol.loginFacebook();
               startpage.logoutBtn.setEnabled(true);
           }catch (InterruptedException ex) {
               startpage.viewmonitor.setText(""+ex);
           }
       }).start();
       
   }

    
}
