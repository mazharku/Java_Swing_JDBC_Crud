/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.controller;

import com.mazhar.database.DatabaseController;
import com.mazhar.model.PostStatusModel;
import com.mazhar.model.PostType;
import com.mazhar.model.RoleSelectionModel;
import com.mazhar.scrapper.ScrapperClass;
import com.mazhar.view.Display;
import com.mazhar.view.EditorFrame;
import com.mazhar.view.StartPager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author mazhar
 */
public class EditorController {
  
    private Display display= new Display();
    private DisplayController displaycontroller= new DisplayController(display);
    private DatabaseController datacontrol= new DatabaseController();
     private DefaultListModel listmodel= new DefaultListModel();
     private List<String> postList= new ArrayList<>();
     private List<PostStatusModel> statuslist= new ArrayList<>();
     private PostStatusModel statusmodel;
    private EditorFrame editor;
    private int postTypeSelectedIndex=0;
    private String postTypeName="";
    private String link="link";
     private ScrapperClass facebookscrapper;
   

    public EditorController(EditorFrame editor) {
        this.editor = editor;
    }

    public void initEditor(){
        editor.setVisible(true);
        editor.slectionField.setText("link");
        editor.postTargetBox.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                postTypeSelectedIndex= editor.postTargetBox.getSelectedIndex();
                
                switch(postTypeSelectedIndex){
                    case 0:
                        postTypeName = "FRIEND";
                        break;
                    case 1:
                        postTypeName = "GROUP";
                        break;
                    case 2:
                        postTypeName="PAGE";
                        break;
                }
                editor.targetnameField.setText(postTypeName);
                getRoleSlection();
            }
        });
        editor.roleSlectionBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String name=(String) editor.roleSlectionBox.getSelectedItem();
                link= datacontrol.getRoleLink(name, postTypeName);
                editor.slectionField.setText(link);
                
            }
            
        });
        
    }
    public void backStack(){
        editor.setVisible(false);
         StartPager startpage= new StartPager();
        StartPagerController startcontroller= new StartPagerController(startpage);
        startcontroller.initStartPage();
    }
    
    public void getPostType(){
        for(PostType post: PostType.values())
        {
            editor.postTargetBox.addItem(post.name());
        }
    }
    public void getRoleSlection(){
        editor.roleSlectionBox.removeAllItems();
        for(RoleSelectionModel role: datacontrol.getRoleInfo(postTypeName)){
            editor.roleSlectionBox.addItem(role.getName());
        }
    }
    public void getList(){
        for(String post: postList)
        {
            listmodel.addElement(post);
        }
        
        editor.postStack.setModel(listmodel);
    }
    public void savePostToStack(){
        clearStack();
        
        String data= editor.textEditor.getText();
        String role= editor.slectionField.getText();
        String selection= editor.targetnameField.getText();
        //statuslist.add(new PostStatusModel(role,selection,data,false));
        if(data.isEmpty() || role.isEmpty() || selection.isEmpty()){
            JOptionPane.showMessageDialog(editor, "please select and update all fields");
        }
        else
        {
           datacontrol.addStatusQuery(role, selection, data);
           postList.add(data);
           getList();
           editor.textEditor.setText(""); 
        }
        
       
        
    }
    public void clearStack(){
        listmodel.removeAllElements();
    }
    
    public void statusPostActionPerform() throws InterruptedException{
        
        new Thread(() -> {
            displaycontroller.initDisplay();
             editor.dispose();
            if (datacontrol.executebatch()) {
                display.statusDisplayActionbox.setText("Status are save in database\n Status updating start...");
            } else {
                display.statusDisplayActionbox.setText("fail to save in database");
            }
            
            try {
                facebookscrapper = new ScrapperClass();
                for(String encmessage:facebookscrapper.postAWall()){
                    display.statusDisplayActionbox.setText(encmessage);
                }
                //display.statusDisplayActionbox.setText("task complete ");
                editor.dispose();
            } catch (Exception ex) {
                display.statusDisplayActionbox.setText("" + ex);
            }

        }).start();

        
       
    }
    
}
