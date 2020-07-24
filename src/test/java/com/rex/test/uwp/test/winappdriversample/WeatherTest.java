package com.rex.test.uwp.test.winappdriversample;

import io.appium.java_client.windows.WindowsDriver;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WeatherTest {

    private static WindowsDriver weaththerSession = null;
    private static WebElement currentWeatherResult = null;

    @BeforeClass
    public static void setup() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            //capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            capabilities.setCapability("app", "Microsoft.BingWeather_8wekyb3d8bbwe!App");
            weaththerSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            weaththerSession.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            currentWeatherResult = weaththerSession.findElementByAccessibilityId("ChromeContent");
            Assert.assertNotNull(currentWeatherResult);

        }catch(Exception e){
            e.printStackTrace();
        } finally {
        }
    }

    @AfterClass
    public static void TearDown() {
        currentWeatherResult = null;
        if (weaththerSession != null) {
            weaththerSession.quit();
        }
        weaththerSession = null;
    }

    @Test
    public void verifyTemperatureIsDisplay() {
        WebDriverWait wait = new WebDriverWait(weaththerSession, 20);
        wait.until(ExpectedConditions.visibilityOf(weaththerSession.findElementByAccessibilityId("NameAndConditions")));
        String xml = weaththerSession.getPageSource();
        System.out.println(xml);
    }

    @Test
    public void verifyDailyListExistsAndCountIsSeven() {
        WebElement list = weaththerSession.findElementByAccessibilityId("DailyList");
        WebDriverWait wait = new WebDriverWait(weaththerSession, 40);
        wait.until(ExpectedConditions.visibilityOf(list));
        List<WebElement> items = list.findElements(By.className("ListBoxItem"));
        Assert.assertEquals(7, items.size());
    }


}
