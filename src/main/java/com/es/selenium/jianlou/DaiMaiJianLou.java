package com.es.selenium.jianlou;

import com.es.selenium.DaiMai;
import com.es.selenium.entity.Concert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.Set;


/**
 * @program: es-demo
 * @description:  pc 端 没票捡漏
 * @author:
 * @create: 2019-04-12 09:49
 **/

public class DaiMaiJianLou extends Concert{


    private static Dimension drSize = new Dimension(850,1280);
    private ChromeDriver driver = null;


    public DaiMaiJianLou(Integer position, Integer count, Integer buyCount) {
        super(position, count, buyCount);
        driver=new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void main(String[] args) throws InterruptedException {
      /* ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i=0;i<3;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {*/
                    DaiMaiJianLou daiMai =new DaiMaiJianLou(1,1,1);
                    daiMai.setLoginUrl(DaiMai.loginUrl);
                    daiMai.setBuyUrl(DaiMai.buyUrl);
                    try {
                        daiMai.login(daiMai);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
               /* }
            });
        }*/

    }

    public  void login(DaiMaiJianLou daiMai) throws InterruptedException {
        driver.get(daiMai.getBuyUrl());
        if(!new File("dm_cookie.txt").exists()){
            driver.get(daiMai.getLoginUrl());
            while (driver.getTitle().equals("中文登录")){
                Thread.sleep(500);
            }
            //登录成功 保存cookie
            saveCooike();
        }
        getCookies();
        buy(daiMai);


    }

    public void saveCooike(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dm_cookie.txt"));
            out.writeObject(driver.manage().getCookies());    //写入customer对象
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getCookies(){
        BufferedReader bufferedReader;
        try {
            FileInputStream fis = new FileInputStream("dm_cookie.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Set<Cookie>  cookies= (Set<Cookie>) ois.readObject();
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void buy(DaiMaiJianLou daiMai) throws InterruptedException {
        driver.get(daiMai.getBuyUrl());
        WebElement position=null;
        WebElement count=null;
        WebElement buy=null;
        WebElement carBuy=null;
        WebElement submit=null;
        try{
            while("提交缺货登记".equals(driver.findElementByXPath("//div[@data-spm='dbuy']").getText())){
                    buy(daiMai);
            }
            //数量1

            //点击提交
            while (buy==null) {
                buy = choose("//div[@data-spm='dbuy']");
            }
            buy.click();
            for(int i=1;i<=daiMai.getBuyCount();i++){
                carBuy=choose("//div[@class='next-row next-row-no-padding buyer-list']/div["+(i+1)+"]/label");
                //选择购票人
                driver.executeScript("arguments[0].click();",carBuy);
            }

            while (submit==null) {
                submit = choose("//div[@class='submit-wrapper']/button");
            }
            driver.executeScript("arguments[0].click();",submit);
            System.out.println("抢票成功");
           Thread.sleep(3000);

            buy(daiMai);
            //
        }catch (Exception e){
           /* WebElement choose = choose("//*[@id=\"priceList\"]/div/ul/li["+daiMai.getPosition()+"]");
            choose.click();
            WebElement btnXuanzuo = driver.findElementById("btnXuanzuo");
            driver.executeScript("arguments[0].click();",btnXuanzuo);*/
           buy(daiMai);

        }
    }

    public WebElement choose(String select){
         try {
             return driver.findElementByXPath(select);
         }catch (Exception e){
             System.out.println(select+"   Not Find");
             return null;
         }

    }

}
