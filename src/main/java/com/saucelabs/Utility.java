package com.saucelabs;

import java.awt.Point;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableList;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class Utility {

	private static AndroidDriver driver;
	private static Dimension windowSize;
	private static Duration SCROLL_DUR = Duration.ofMillis(1000);
	private static double SCROLL_RATIO = 0.8;
	private static int ANDROID_SCROLL_DIVISOR = 3;

	 public static void scrollNClick(By listItems, String Text){
	        boolean flag = false;

	        while(true){
	            for(WebElement el: driver.findElements(listItems)){
	                if(el.getAttribute("text").equals(Text)){
	                    el.click();
	                    flag=true;
	                    break;
	                }
	            }
	            if(flag)
	                break;
	            else
	            scroll(ScrollDirection.DOWN, SCROLL_RATIO);
	        }
	    }

	    public static void scrollNClick(WebElement el){
	        int retry = 0;
	        while(retry <= 5){
	            try{
	                el.click();
	                break;
	            }catch (org.openqa.selenium.NoSuchElementException e){
	                //scrollDown();
	                scroll(ScrollDirection.DOWN, SCROLL_RATIO);
	                retry++;
	            }
	        }
	    }

	    public static void scrollIntoView(String Text){
	    	
	        String mySelector = "new UiSelector().text(\"" + Text + "\").instance(0)";
	        String command = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" + mySelector + ");";
	        driver.findElement(AppiumBy.androidUIAutomator(command));
	    }

	    public static void scrollTo(String Text){
	        //https://appium.io/docs/en/writing-running-appium/ios/ios-xctest-mobile-gestures/

	        if(driver instanceof AndroidDriver){
	            scrollIntoView(Text);
        }
//	            else if(driver instanceof IOSDriver){
//	                final HashMap<String, String> scrollObject = new HashMap<String, String>();
//	                scrollObject.put("predicateString", "value == '" + Text + "'");
//	                scrollObject.put("toVisible", "true");
//	                driver.executeScript("mobile: scroll", scrollObject);
//	            }
	    }

	public static void scroll(ScrollDirection dir, double scrollRatio) {
		if (scrollRatio < 0 || scrollRatio > 1) {
			throw new Error("Scroll distance must be between 0 and 1");
		}
		Dimension size = driver.manage().window().getSize();
		Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
		int top = midPoint.y - (int) ((size.height * scrollRatio) * 0.5);
		int bottom = midPoint.y + (int) ((size.height * scrollRatio) * 0.5);
		int left = midPoint.x - (int) ((size.width * scrollRatio) * 0.5);
		int right = midPoint.x + (int) ((size.width * scrollRatio) * 0.5);
		if (dir == ScrollDirection.UP) {
			swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
		} else if (dir == ScrollDirection.DOWN) {
			swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
		} else if (dir == ScrollDirection.LEFT) {
			swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
		} else {
			swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
		}
	}

	private static Dimension getWindowSize() {
		if (windowSize == null) {
			windowSize = driver.manage().window().getSize();
		}
		return null;
	}

	protected static void swipe(Point start, Point end, Duration duration) {
		boolean isAndroid = driver instanceof AndroidDriver;

		PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
		Sequence swipe = new Sequence(input, 0);
		swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
		swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		if (isAndroid) {
			duration = duration.dividedBy(ANDROID_SCROLL_DIVISOR);
		} else {
			swipe.addAction(new Pause(input, duration));
			duration = Duration.ZERO;
		}
		swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
		swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		((AppiumDriver)driver).perform(ImmutableList.of(swipe));
	}

	public static void swipe1(Point start, Point end, Duration duration) {
			Dimension size = driver.manage().window().getSize();
			 int startX = size.getWidth()/2;
			 int startY = size.getHeight()/2;
			 int endX = startX;
			 int endy = (int) (size.getHeight() * 0.25);
			PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH,"finger1");
			 Sequence sequence = new Sequence (finger1, 1)
			     .addAction(finger1.createPointerMove (Duration.ZERO, PointerInput.Origin.viewport(),startX, startY))
			     .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
			     .addAction(new Pause (finger1, Duration.ofMillis(200)))
			     .addAction(finger1.createPointerMove (Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, endy))
			     .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			driver.perform(Collections.singletonList(sequence));

		}
	
	 public static void click(By element){
		 
	        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(element)).click();
	    }

	 public static void sendKeys(By by, String text){
		 
	        WebElement ele = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(by));
	        ele.sendKeys(text);
	    }
	}


