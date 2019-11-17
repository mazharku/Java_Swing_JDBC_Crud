/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.scrapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author mazhar
 */
public class DriverConfig {
    static DriverConfig driverconfig;
    private WebDriver driver;
    public WebDriver getDriverInit() {
        
        if(driver==null)
        {
            initDriver();              
        }
        return driver;

    }
    public void initDriver()
    {
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\mazhar\\Documents\\FacebookScrapper\\");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
    }
    public static DriverConfig  WebDriverConfig(){
        if(driverconfig==null){
            driverconfig= new DriverConfig();
        }
        return driverconfig;
    }
}
