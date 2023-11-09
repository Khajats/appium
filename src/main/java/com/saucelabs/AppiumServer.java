package com.saucelabs;

import java.io.File;
import java.time.Duration;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServer {
private static AppiumDriverLocalService server;
	
	private static void getInstance() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder
				.withAppiumJS(new File("C:\\Users\\SANAKH\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
				.usingPort(4723)
				.withTimeout(Duration.ofSeconds(300))
				.withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
				.withLogFile(new File("Appium.txt"))
				.withIPAddress("127.0.0.1").build();
		
		  server = AppiumDriverLocalService.buildService(builder);
		  server.start();
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		getInstance();
		
	}
	

}
