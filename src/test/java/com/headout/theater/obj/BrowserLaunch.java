package com.headout.theater.obj;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


public class BrowserLaunch extends Assert{
	
	public static WebDriver driver;
	
	@Parameters({"browser","url","image_disable","headless"})
	@BeforeClass
	public void setUp(@Optional String browser,String url,@Optional Boolean image_disable,@Optional Boolean headless) throws InterruptedException{
		System.setProperty("webdriver.gecko.driver", "Path");

	     if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver",".//src/main/resources/browser/chromedriver");
			if(image_disable==true&&headless==true){
				ChromeOptions options=new ChromeOptions();
				disableImageChrome(options);
			    options.addArguments("window-size=1200,684");
				options.addArguments("headless");
				driver=new ChromeDriver(options);
			    System.out.println("Running headless chrome with disable images");


			}else if(image_disable){
				ChromeOptions options=new ChromeOptions();
				disableImageChrome(options);
				driver=new ChromeDriver(options);
				System.out.println("Running chrome with disable images");
			}else if(headless){				
			    ChromeOptions options = new ChromeOptions(); 
			    options.addArguments("window-size=1200,684");
			    options.addArguments("headless");
			    driver = new ChromeDriver(options);	
			    System.out.println("Running headless chrome");
			}
			else			
			{
				driver=new ChromeDriver();
			}	
		}
		else if(browser.equalsIgnoreCase("Safari")){
			System.out.println("Running Safari");
			System.setProperty("webdriver.safari.driver","Path");
			driver=new SafariDriver();
			driver.manage().window().maximize();
			
		}else if(browser.equalsIgnoreCase("Firefox")){
			System.out.println("Running Firefox");
			driver=new FirefoxDriver();
			driver.manage().window().maximize();				
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);	
	}
	public static void disableImageChrome(ChromeOptions options){
		HashMap<String, Object> images=new HashMap<String ,Object>();
		images.put("images", 2);
		HashMap<String, Object> prefs=new HashMap<String ,Object>();
		prefs.put("profile.default_content_setting_values", images);
		options.setExperimentalOption("prefs", prefs);		
	}	
	@AfterClass
	public void close(){
		driver.quit();
	}
}
