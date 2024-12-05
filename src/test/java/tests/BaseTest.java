package tests;

// This sample code supports Appium Java client >=9
// https://github.com/appium/java-client
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    AndroidDriver<MobileElement> driver;
    static int passCount = 0;
    static int failCount = 0;

    @BeforeEach
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.0");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "android_5554");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        // caps.setCapability(MobileCapabilityType.APP, "C:\\Users\\xbran\\Downloads\\google-lens-1-17-240515009.apk");

        /*
        var options = new BaseOptions()
                .amend("appium:platformName", "Android")
                .amend("appium:platformVersion", "14")
                .amend("appium:deviceName", "android_5554")
                .amend("appium:app", "C:\\Users\\xbran\\Downloads\\google-lens-1-17-240515009.apk")
                .amend("appium:automationName", "UiAutomator2")
                .amend("appium:appPackage", "com.google.ar.lens")
                .amend("appium:appActivity", "com.google.vr.apps.ornament.app.lens.LensLauncherActivity")
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);

        private URL getUrl() {
            try {
                return new URL("http://127.0.0.1:4723");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
         */

        URL url = new URL("http://127.0.0.1:4723");

        driver = new AndroidDriver<MobileElement>(url, caps);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @AfterAll
    public static void analysis() {
        System.out.println("TOTAL NUMBER OF TESTS: " + (passCount + failCount));
        System.out.println("SCORE: " + passCount + " / " + (passCount + failCount) + " = " + (double)((passCount / (passCount + failCount)) * 100) + "%");
    }
}
