package com.es.selenium.weibo;

import com.es.selenium.entity.Concert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @program: es-demo
 * @description:
 * @author: 陈家乐
 * @create: 2019-04-12 09:49
 **/

public class WeiBo extends Concert{
    public static String loginUrl="https://weibo.com/comment/inbox?topnav=1&wvr=6&mod=message&f=1&need_filter=1";
    public static String buyUrl="https://detail.damai.cn/item.htm?spm=a2oeg.search_category.0.0.42651ebdpFPOq1&id=592432015785&clicktitle=2019%E9%BA%A6%E7%94%B0%E9%9F%B3%E4%B9%90%E8%8A%82-%E5%8C%97%E4%BA%AC";
    private static Dimension drSize = new Dimension(850,1280);

    private ChromeDriver driver = null;


    public WeiBo() {
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public static void main(String[] args) throws InterruptedException {

                WeiBo weiBo =new WeiBo();
                weiBo.setLoginUrl(loginUrl);
                try {
                    weiBo.login(weiBo);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }


    }

    public  void login(WeiBo weiBo) throws InterruptedException {
        driver.get(weiBo.getLoginUrl());
        if(!new File("weibo_cookie.txt").exists()){
           System.out.println("未登录");
           driver.findElementById("loginname").sendKeys("17621913813");
           driver.findElementByXPath("//*[@id='pl_login_form']/div/div[@class='W_login_form']/div[2]/div/input").sendKeys("493095906");

           while (choose("//*[@id='v6_pl_leftnav_msgbox']")==null){
               Thread.sleep(5000);
           }
           //登录成功 保存cookie
            saveCooike();
        }

        Thread.sleep(10000);
        getCookies();
        driver.navigate().refresh();
        int i=200;
        Boolean flag=true;
        while (true) {
            if (flag){
                WebElement replay = null;
                replay = choose("//*[@id='v6_pl_content_commentlist']/div[2]/div[1]/div[2]/div/ul/li[2]/a/span/span");
                replay.click();
                driver.findElementByXPath("//*[@id='v6_pl_content_commentlist']/div[2]/div[1]/div[3]/div/div/div[2]/div[1]/textarea").sendKeys("胡春杨加油✖" + i++);
            }
            WebElement submit = choose("//*[@id='v6_pl_content_commentlist']/div[2]/div[1]/div[3]/div/div/div[2]/div[2]/div[1]/a");
            driver.executeScript("arguments[0].click();",submit);
            Thread.sleep(1000);
            WebElement choose = choose("//*[@class='W_ficon ficon_close S_ficon']");
            if(choose!=null){
                Thread.sleep(600000);
                driver.executeScript("arguments[0].click();",choose);
                flag=false;
            }else {
                flag=true;
            }
            Thread.sleep(20000);
        }
        /*if(!new File("dm_cookie.txt").exists()){
            driver.get(daiMai.getLoginUrl());
            while (driver.getTitle().equals("中文登录")){
                Thread.sleep(500);
            }
            //登录成功 保存cookie
            saveCooike();
        }
        getCookies();*/
        //buy(daiMai);


    }

    public void saveCooike(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("weibo_cookie.txt"));
            out.writeObject(driver.manage().getCookies());    //写入customer对象
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getCookies(){
        BufferedReader bufferedReader;
        try {
            FileInputStream fis = new FileInputStream("weibo_cookie.txt");
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

   /* public void buy(DaiMai daiMai) throws InterruptedException {
        driver.get(daiMai.getBuyUrl());

        WebElement count=null;
        WebElement buy=null;
        WebElement carBuy=null;
        WebElement submit=null;
        try{
            while("即将开抢".equals(driver.findElementByXPath("//div[@data-spm='dbuy']").getText())){
            }
            //位置
            while (position==null) {
                position = choose("//div[@class='perform__order__select'][contains(.//text(),'选择票价')]//div[@class='select_right_list']/div[" + daiMai.getPosition() + "]");
            }
            position.click();
            //数量
            if(daiMai.getCount()>1){
                  count = choose("//a[@class='cafe-c-input-number-handler cafe-c-input-number-handler-up']");
                  if(count!=null) {
                      count.click();
                  }
            }
            //点击提交
            while (buy==null) {
                buy = choose("//div[@data-spm='dbuy']");
            }
            buy.click();
            for(int i=1;i<=daiMai.getBuyCount();i++){
                carBuy=choose("//div[@class='next-row next-row-no-padding buyer-list']/div["+i+"]/label");
                //选择购票人
                driver.executeScript("arguments[0].click();",carBuy);
            }

            while (submit==null) {
                submit = choose("//div[@class='submit-wrapper']/button");
            }
            driver.executeScript("arguments[0].click();",submit);
            System.out.println("抢票成功");
           // Thread.sleep(1000);
          //  buy(daiMai);
            //
        }catch (Exception e){
           *//* WebElement choose = choose("//*[@id=\"priceList\"]/div/ul/li["+daiMai.getPosition()+"]");
            choose.click();
            WebElement btnXuanzuo = driver.findElementById("btnXuanzuo");
            driver.executeScript("arguments[0].click();",btnXuanzuo);*//*
           buy(daiMai);

        }
    }
    */

    public WebElement choose(String select){
         try {
             return driver.findElementByXPath(select);
         }catch (Exception e){
             System.out.println(select+"   Not Find");
             return null;
         }

    }

}
