/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.controller;

import com.mazhar.view.Display;

/**
 *
 * @author mazhar
 */
public class DisplayController {
    private Display display;

    public DisplayController(Display display) {
        this.display = display;
    }
    
    public void initDisplay(){
        display.setVisible(true);
    }
}
