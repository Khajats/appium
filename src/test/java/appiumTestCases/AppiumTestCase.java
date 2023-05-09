package appiumTestCases;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumTestCase {
	
	
	@Test
	public void myAppiumTest() throws MalformedURLException {
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.APP,System.getProperty("user.dir")+"/src/test/resources/ApkFiles/ApiDemos-debug.apk");
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),cap);
	}

}

