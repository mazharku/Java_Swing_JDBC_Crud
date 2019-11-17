/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.scrapper;

import com.mazhar.database.DatabaseController;
import com.mazhar.model.FriendListModel;
import com.mazhar.model.GroupListModel;
import com.mazhar.model.NotificationModel;
import com.mazhar.model.PostStatusModel;
import com.mazhar.view.Display;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author mazhar
 */
public class ScrapperClass {

    private DatabaseController datacontrol = new DatabaseController();
    private PostStatusModel postmodel;
    private FriendListModel friendmodel;
    private Display display = new Display();
    private WebDriver driver = DriverConfig.WebDriverConfig().getDriverInit();

    public void openFacebook() throws InterruptedException {
        
        Thread.sleep(5000);
        driver.get("http://www.facebook.com");
        Thread.sleep(5000);
        System.out.println(getUserName());
    }

    public void closeWindow() {
        
        driver.quit();
        
    }

    public void loginFaceBook() throws InterruptedException {
        WebElement name = driver.findElement(By.id("email"));
        name.sendKeys("mazharshapnil@yandex.com");
        WebElement pass = driver.findElement(By.id("pass"));
        pass.sendKeys("01723318377");
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@data-testid='royal_login_button']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    public void commentAPost() throws InterruptedException {
        //driver.navigate().to(groupPostLink());
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement comment = driver.findElement(By.xpath("//a[@testid='UFI2CommentLink']"));
        comment.click();
        Thread.sleep(5000);
        WebElement commentbox = driver.findElement(By.xpath("//div[@role='textbox']"));
        commentbox.sendKeys(":D");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        commentbox.sendKeys(Keys.RETURN);
        Thread.sleep(3000);
    }

    public void replyAPost() throws InterruptedException {

        driver.navigate().to("https://www.facebook.com/groups/498685624039941/permalink/498750327366804/?comment_id=499241180651052&comment_tracking=%7B%22tn%22%3A%22R%22%7D");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement replybox = driver.findElement(By.xpath("//div[@role='textbox']"));
        replybox.sendKeys(":P");
        Thread.sleep(5000);
        replybox.sendKeys(Keys.RETURN);
        Thread.sleep(3000);
    }

    public List<NotificationModel> saveNotification() throws InterruptedException {
        List<NotificationModel> notificationlist= new ArrayList<>();
        driver.findElement(By.id("fbNotificationsJewel")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> notiList = driver.findElements(By.className("_4l_v"));
        notiList.stream().map((element) -> {
            System.out.println(element.getText());
            return element;
        }).forEachOrdered((element) -> {
            notificationlist.add(new NotificationModel(element.getText()));
            datacontrol.addNotificationQuery(element.getText());
        });
        Thread.sleep(15000);

        datacontrol.executebatch();
        System.out.println(notiList.size());
        Thread.sleep(5000);
        return notificationlist;
    }

    public void createAPost() throws InterruptedException {

        WebElement status = driver.findElement(By.xpath("//textarea[@name='xhpc_message_text']"));
        status.click();
        status.sendKeys("Hello!! everyone!!!");
        Thread.sleep(5000);
        WebElement submit = driver.findElement(By.xpath("//button[@data-testid='react-composer-post-button']"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        submit.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String value = driver.findElement(By.xpath("//a[@class='_5pcq']")).getAttribute("href");
        //datacontrol.insertPostLink(value);
    }

    public List<FriendListModel> friendList() throws InterruptedException {
        List<FriendListModel> friendlists = new ArrayList<>();

        String navigatelink = "https://www.facebook.com/" + getUserName() + "/friends";
        driver.navigate().to(navigatelink);
        List<WebElement> friendlist = driver.findElements(By.className("_698"));

        List<WebElement> friendlistss = driver.findElements(By.xpath("//div[@class='fsl fwb fcb']/a"));
        for (WebElement name : friendlistss) {
            String Fname = name.getText();
            String link = name.getAttribute("href");
            datacontrol.addFriendQuery(Fname, link);
            friendlists.add(new FriendListModel(Fname, link));
            Thread.sleep(100);
        }
        System.out.println("" + friendlistss.size());
        Thread.sleep(5000);
        datacontrol.executebatch();
        Thread.sleep(2000);
        return friendlists;
    }

    public List<GroupListModel> groupList() throws InterruptedException {
        List<GroupListModel> grouplist= new ArrayList<>();
        String navigatelink = "https://www.facebook.com/" + getUserName() + "/groups";
        driver.navigate().to(navigatelink);

        List<WebElement> grouplists = driver.findElements(By.xpath("//div[@class='mbs fwb']/a"));
        for (WebElement group : grouplists) {
            String name = group.getText();
            String link = group.getAttribute("href");
            datacontrol.addGroupQuery(name, link);
            grouplist.add(new GroupListModel(name, link));
            Thread.sleep(100);
        }
        System.out.println("" + grouplists.size());
        Thread.sleep(5000);
        datacontrol.executebatch();
        Thread.sleep(2000);
        return grouplist;
    }

    public void logout() throws InterruptedException {
        WebElement logoutbtn = driver.findElement(By.id("userNavigationLabel"));
        logoutbtn.click();
        Thread.sleep(5000);
        WebElement logout = driver.findElement(By.xpath("//*[text()='Log Out']"));
        logout.click();
    }

    public String getUserName() {
        String username = driver.findElement(By.className("_2s25")).getAttribute("href");
        String name = username.replace("https://www.facebook.com/", "");
        String returnusername = name.trim();
        return returnusername;
    }

    public List<String> postAWall() throws InterruptedException {
        List<String> messages= new ArrayList<>();
        String message="null";
        Thread.sleep(2000);
        if (datacontrol.getAllStatus().isEmpty()) {
            //JOptionPane.showMessageDialog(null, "There is no pending status!!!");
                //display.statusDisplayActionbox.setText("call from thread...");
           message = "There ara no Status to post";
           messages.add(message);
        } else {
            for (PostStatusModel status : datacontrol.getAllStatus()) {
                String role = status.getPostLink();
                if (role.equals("GROUP")) {
                    Thread.sleep(5000);
                    driver.navigate().to(status.getPostRole());
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    WebElement statusbar = driver.findElement(By.xpath("//textarea[@name='xhpc_message_text']"));
                    statusbar.click();
                    statusbar.sendKeys(status.getMessage());
                    Thread.sleep(5000);
                    WebElement submit = driver.findElement(By.xpath("//button[@data-testid='react-composer-post-button']"));
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    submit.click();
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    datacontrol.updatePostStatus(status.getIndex());
                    message= "Group Post Success!";
                    messages.add(message);
                } else if (role.equals("FRIEND")) {
                    Thread.sleep(5000);
                    driver.navigate().to(status.getPostRole());
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    WebElement statusbar = driver.findElement(By.className("_3nd0"));
                    statusbar.click();
                    Thread.sleep(2000);
                    WebElement post = driver.findElement(By.xpath("//div[@role='textbox']"));

                    post.sendKeys(status.getMessage());
                    Thread.sleep(5000);
                    WebElement submit = driver.findElement(By.xpath("//span[text()='Post']"));
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    submit.click();
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    datacontrol.updatePostStatus(status.getIndex());
                    message= "Friend's timeline Post Success!";
                    messages.add(message);
                    
                }
                Thread.sleep(3000);

            }
        }
        return messages;
    }
}
