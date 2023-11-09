package mydemoappTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import com.saucelabs.ScrollDirection;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class FirstSampleTest {
	
	private static AppiumDriver driver;

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		
		UiAutomator2Options options = new UiAutomator2Options();
			 options.setDeviceName ("emulator-5554")
			        .setPlatformVersion ("13.0")
			        .setAppPackage("com.saucelabs.mydemoapp.rn")
			        .setAppActivity(".MainActivity");

			 driver = new AppiumDriver(new URL("http://localhost:4723/wd/hub"),options);
			 driver.findElement(AppiumBy.accessibilityId("open menu")).click();
			 driver.findElement(AppiumBy.accessibilityId("menu item log in")).click();
			 Thread.sleep(1000);
			 driver.findElement(AppiumBy.accessibilityId("Username input field")).sendKeys("khaja ts");
			 driver.findElement(AppiumBy.accessibilityId("password input field")).sendKeys("Khaja9731");
			 driver.findElement(AppiumBy.accessibilityId("Login button")).click();
			 
	}
	
	public static void scroll(ScrollDirection direction) {
		Dimension size = driver.manage().window().getSize();
		Point midpoint = new Point((int)(size.width*0.5), (int)(size.height*0.5));
		int top = (int) (midpoint.y-midpoint.y*0.5);
		int bottom = (int)(midpoint.y+midpoint.y*0.5);
		int left = (int)(midpoint.x-midpoint.x*0.5);
		int right = (int)(midpoint.x+midpoint.x*0.5);
		
		if(direction==ScrollDirection.UP) {
			swipe(new Point(midpoint.x,top), new Point(midpoint.x,bottom),Duration.ofMillis(3000));
		}else if (direction==ScrollDirection.DOWN) {
			swipe(new Point(midpoint.x,bottom), new Point(midpoint.x,top),Duration.ofMillis(3000));
		}else if (direction==ScrollDirection.LEFT) {
			swipe(new Point(left,midpoint.y), new Point(right,midpoint.y),Duration.ofMillis(3000));
		}else if (direction==ScrollDirection.RIGHT) {
			swipe(new Point(right,midpoint.y), new Point(left,midpoint.y),Duration.ofMillis(3000));
		}
			
	}

	private static void swipe(Point start, Point end, Duration duration) {
		PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence sequence = new Sequence(input, 0)
				.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, end.y))
				.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
				.addAction(new Pause(input, duration))
				.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x,end.y))
				.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		driver.perform(Collections.singletonList(sequence));
	}

}
