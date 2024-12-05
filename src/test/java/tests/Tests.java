package tests;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.bys.builder.AppiumByBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tests extends BaseTest {

    public void swipeAction(Point start, Point end) {
        // Create a PointerInput object for touch interaction
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create a sequence of actions for swiping
        Sequence swipe = new Sequence(finger, 0);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(), start.getY()))  // Move to the start point
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))  // Press down (finger down)
                .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), end.getX(), end.getY()))  // Swipe to the end point
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));  // Lift the finger

        // Perform the swipe action
        driver.perform(Arrays.asList(swipe));
    }

    @Test
    public void testApplePhone() {
        List<String> keywords = Arrays.asList("iphone", "16 pro");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el14 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el14.click();

        // Click on the iphone
        MobileElement el15 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[18]"));
        el15.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        MobileElement result = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc]"));
        String contentDesc = result.getAttribute("content-desc");
        if (contentDesc == null) {
            System.out.println("content-desc is null, performing other actions...");
        } else {
            contentDesc = contentDesc.toLowerCase();
            for (String keyword : keywords) {
                Assert.assertTrue(contentDesc.contains(keyword));
            }
            passCount++;
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
    }

    @Test
    public void SJSUShirt() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("san jose state", "short sleeve", "white", "target");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[17]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 925, 1926
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void UniqloFloral() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("modal", "short sleeve", "cotton");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[16]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1884
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void goodfellowShirts() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("goodfellow", "4", "short sleeve");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el14 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el14.click();

        // Click on the iphone
        MobileElement el15 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[15]"));
        el15.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        MobileElement result = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc]"));
        String contentDesc = result.getAttribute("content-desc");
        if (contentDesc == null) {
            System.out.println("content-desc is null, performing other actions...");
        } else {
            contentDesc = contentDesc.toLowerCase();
            for (String keyword : keywords) {
                Assert.assertTrue(contentDesc.contains(keyword));
            }
            passCount++;
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
    }

    @Test
    public void UniqloPajama() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("flannel", "pants", "uniqlo");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[14]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1884
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void iphone16Back() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("iphone", "16");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el14 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el14.click();

        // Click on the iphone
        MobileElement el15 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[13]"));
        el15.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        boolean allKeywordsPresent = true;
        MobileElement result = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc]"));
        String contentDesc = result.getAttribute("content-desc");
        contentDesc = contentDesc.toLowerCase();
        for (String keyword : keywords) {
            if (!contentDesc.contains(keyword)) {
                allKeywordsPresent = false;
                break;
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        if (!allKeywordsPresent) {
            failCount++;
            Assert.fail("None of the ViewGroup elements had all keywords");
        }
    }

    @Test
    public void MusicAlbum() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("ajr", "album", "the click");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[12]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1884
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void toyChair() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("toy", "chair", "mini");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[11]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1884
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void uniqloBack() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("floral", "short sleeve", "uniqlo");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[10]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1884
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void minisoDoll() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("miniso", "loopy");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[9]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1800
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void pusheenAnirollz() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("pusheen", "anirollz", "plush");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[8]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1884
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void playstationAlarm() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("playstation", "5", "controller", "alarm");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el14 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el14.click();

        // Click on the iphone
        MobileElement el15 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[7]"));
        el15.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        boolean allKeywordsPresent = true;
        MobileElement result = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc]"));
        String contentDesc = result.getAttribute("content-desc");
        contentDesc = contentDesc.toLowerCase();
        for (String keyword : keywords) {
            if (!contentDesc.contains(keyword)) {
                allKeywordsPresent = false;
                break;
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        if (!allKeywordsPresent) {
            failCount++;
            Assert.fail("None of the ViewGroup elements had all keywords");
        }
    }

    @Test
    public void uniqloDark() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("uniqlo", "modal", "cotton", "short sleeve");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[6]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1884
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void minisoLoopyDark() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("miniso", "loopy");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[5]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1500
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void hifitCouch() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("hifit", "couch");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[4]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1530
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void hifitCouchBlurry() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("hifit", "couch");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[3]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1530
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void iPhoneDarkBlur() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("iphone", "16");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el1 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el1.click();

        // Click on the SJSU shirt
        MobileElement el2 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[2]"));
        el2.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        for (int i = 1; i <= 4; i++) {
            MobileElement el3 = driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[" + i + "]"));
            el3.click();

            try {
                Thread.sleep(3000);  // 2 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Perform a tap at specific coordinates (925, 1926)
            PointerInput touchInput = new PointerInput(PointerInput.Kind.TOUCH, "touch");
            Sequence tapAtCoordinates = new Sequence(touchInput, 0);

            // Move to absolute coordinates and perform a tap
            tapAtCoordinates.addAction(touchInput.createPointerMove(
                    Duration.ofMillis(0), PointerInput.Origin.viewport(), 931, 1890
            ));
            tapAtCoordinates.addAction(touchInput.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAtCoordinates.addAction(touchInput.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the sequence
            driver.perform(Collections.singletonList(tapAtCoordinates));

            MobileElement textResult = driver.findElement(By.xpath("//android.webkit.WebView[@text]"));
            String text = textResult.getAttribute("text");

            if (text != null) {
                text = text.toLowerCase();
                boolean allKeywordsPresent = true;
                for (String keyword : keywords) {
                    if (!text.contains(keyword)) {
                        allKeywordsPresent = false;
                        break;
                    }
                }

                if (!allKeywordsPresent) {
                    // go back to try again
                    driver.navigate().back();
                    driver.navigate().back();
                    continue;
                }

                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();
                driver.navigate().back();

                passCount++;
                return; // passed
            } else {
                // go back to try again
                driver.navigate().back();
                driver.navigate().back();
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        failCount++;
        Assert.fail("None of the ViewGroup elements had all keywords");
    }

    @Test
    public void branchChair() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        List<String> keywords = Arrays.asList("branch", "guest", "chair");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // opening the app
        // Swiping Action 1
        Point start = new Point(498, 2193);
        Point end = new Point(530, 69);

        // Perform swipe action with Actions class
        swipeAction(start, end);

        // Click on Lens element
        MobileElement el14 = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Lens\"]"));
        el14.click();

        // Click on the iphone
        MobileElement el15 = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Image\"])[1]"));
        el15.click();

        try {
            Thread.sleep(3000);  // 3 second delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Swiping Action 2
        Point start2 = new Point(544, 1935);
        Point end2 = new Point(544, 78);

        // Perform the second swipe action
        swipeAction(start2, end2);

        // check if the text is iphone 16 pro
        boolean allKeywordsPresent = true;
        MobileElement result = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc]"));
        String contentDesc = result.getAttribute("content-desc");
        contentDesc = contentDesc.toLowerCase();
        for (String keyword : keywords) {
            if (!contentDesc.contains(keyword)) {
                allKeywordsPresent = false;
                break;
            }
        }

        // back to home screen
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();

        if (!allKeywordsPresent) {
            failCount++;
            Assert.fail("None of the ViewGroup elements had all keywords");
        }
    }
}
