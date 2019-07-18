package com.es.selenium;

import com.es.selenium.entity.Concert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @program: es-demo
 * @description: web手机端
 * @author:
 * @create: 2019-04-12 09:49
 **/

public class DaiMaiPhone extends Concert{
    public static String loginUrl="https://m.damai.cn/damai/minilogin/index.html?returnUrl=https%3A%2F%2Fm.damai.cn%2Fdamai%2Fmine%2Fmy%2Findex.html%3Fspm%3Da2o71.home.top.duserinfo%26anchor%3Dhome-mine&isNext=false&spm=a2o71.mydamai.0.0";
   public static String buyUrl="https://m.damai.cn/damai/detail/item.html?itemId=592432015785&spm=a2o71.search.list.ditem_0";
   private static Dimension drSize = new Dimension(850,1280);
    private ChromeDriver driver = null;


    public DaiMaiPhone(Integer position, Integer count, Integer buyCount) {
        super(position, count, buyCount);
        driver=new ChromeDriver();
        //driver.manage().window().maximize();
    }

    public static void main(String[] args) throws InterruptedException {
      /* ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i=0;i<3;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {*/
                    DaiMaiPhone daiMai =new DaiMaiPhone(7,2,1);
                    daiMai.setLoginUrl(loginUrl);
                    daiMai.setBuyUrl(buyUrl);
                    try {
                        daiMai.login(daiMai);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
               /* }
            });
        }*/

    }

    public  void login(DaiMaiPhone daiMai) throws InterruptedException {

            driver.get(daiMai.getLoginUrl());
            driver.switchTo().frame("alibaba-login-box");
            WebElement choose = choose("//a[@class='password-login-link']");
            choose.click();
            driver.findElementById("fm-login-id").sendKeys("手机");
            driver.findElementById("fm-login-password").sendKeys("密码");
            //需要手动登录
            Thread.sleep(7000);
            buy(daiMai);

    }


    public void buy(DaiMaiPhone daiMai) throws InterruptedException {
        driver.get(daiMai.getBuyUrl());
        WebElement position=null;
        WebElement count=null;
        WebElement buy=null;
        WebElement carBuy=null;
        WebElement submit=null;
        WebElement date=null;
        WebElement time=null;
        WebElement order=null;
        try{
            Thread.sleep(200);
            System.out.println(driver.findElementByXPath("//p[@class='buy__button__text']").getText());
            while("即将开抢".equals(driver.findElementByXPath("//p[@class='buy__button__text']").getText())){

            }
            long start=System.currentTimeMillis();
            buy = choose("//div[@class='buy__button']");
            buy.click();
            Thread.sleep(200);

            /*date=choose("//div[@class='sku-prop'][contains(.//text(),'选择场次')]/div[2]/div[3]");
            date.click();

            time=choose("//div[@class='sku-prop'][contains(.//text(),'选择时间')]/div[2]/div[1]");
            if(time!=null) {
                time.click();
                Thread.sleep(100);
            }*/
            position=choose("//div[@class='sku-prop'][contains(.//text(),'选择票')]/div[2]/div["+daiMai.getPosition()+"]");
            position.click();


            //数量
            if(daiMai.getCount()>1){
                  count = choose("//div[@class='number-edit-bg enable']");
                  count.click();
            }
            while (submit==null) {
                submit = choose("//div[@class='sku-default background-enable']");
            }
            driver.executeScript("arguments[0].click();",submit);
            Thread.sleep(200);
            for(int i=1;i<=daiMai.getBuyCount();i++){
                carBuy=choose("//div[@class='buyer-list']/div["+i+"]/i");
                //选择购票人
                driver.executeScript("arguments[0].click();",carBuy);
            }
            order=choose("//div[@class='dm-submit-order']/div");
            driver.executeScript("arguments[0].click();",order);
            System.out.println("抢票成功");
            long end=System.currentTimeMillis();
            System.out.println((end-start)/1000+"s");
            //Thread.sleep(1000);
           // buy(daiMai);
            //
        }catch (Exception e){
            e.printStackTrace();
            buy(daiMai);
            /*e.printStackTrace();
            WebElement choose = choose("//*[@id=\"priceList\"]/div/ul/li["+daiMai.getPosition()+"]");
            choose.click();
            WebElement btnXuanzuo = driver.findElementById("btnXuanzuo");
            btnXuanzuo.click();*/
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
