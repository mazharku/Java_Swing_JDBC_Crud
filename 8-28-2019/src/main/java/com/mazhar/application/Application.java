/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.application;

import com.mazhar.database.DatabaseController;
import com.mazhar.view.StartPager;

/**
 *
 * @author mazhar
 */
public class Application {
      //private DatabaseController datacontrol= new DatabaseController();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DatabaseController datacontrol= new DatabaseController();
        StartPager startpage= new StartPager();
        startpage.setVisible(true);
        startpage.viewmonitor.setText("FaceBook Bot Started..");
        int value=datacontrol.faceBookStatus();
        if(value==1)
        {
            startpage.viewmonitor.setText("FaceBook logged in..");
            startpage.facebookloginBtn.setEnabled(false);
        }
        else
        {
            startpage.viewmonitor.setText("FaceBook is not logged in..");
            startpage.logoutBtn.setEnabled(false);
        }
    }
    
}
