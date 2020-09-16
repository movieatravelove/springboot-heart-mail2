package com.example.mail.modules.warm_heart_mail.component;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhang
 * @Date: 2019/3/1 15:04
 * @Description: 使用PhantomJS把echart保存成图片
 * 参考：https://blog.csdn.net/aofavx/article/details/80395833
 */
@Component
public class SaveEchartsComponent2 {

    Logger logger = LoggerFactory.getLogger(SaveEchartsComponent2.class);
    @Value("${getRadarUrl}")
    private String getRadarUrl;

    /**
     * 返回base64格式图片
     *
     * @return
     * @throws InterruptedException
     */
    public String saveSurfModelUrlToImg(String consName) throws InterruptedException {
        logger.info("开始生成幸运指数雷达图……");
        List<String> imageBase64List = new ArrayList<String>();
        PhantomJSDriver driver = getPhantomJSDriver();
        // 让浏览器访问空间主页
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(MessageFormat.format(getRadarUrl,consName));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //加入一段休眠时间，防止js执行没完成就进行的 读取echart图片数据的功能。
        Thread.sleep(10000);
        //获取echart图片数据
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String temTxt = (String) js.executeScript("return returnEchartImg(temEcharts)");
        //imageBase64放到list中
//        imageBase64List.add(temTxt.replace("data:image/png;base64,", ""));
//        ImgFileUtils.GenerateImage(temTxt,"d://tem01.png");
        driver.close();
        driver.quit();
        return temTxt;
    }

    public PhantomJSDriver getPhantomJSDriver() {
        //设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        String osname = System.getProperties().getProperty("os.name");
        if (osname.equals("Linux")) {//判断系统的环境win or Linux
            dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/bin/phantomjs");
        } else {
            // 本地驱动
            dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        }
        //创建无界面浏览器对象
        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        return driver;
    }


}
