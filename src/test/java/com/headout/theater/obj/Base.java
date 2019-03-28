package com.headout.theater.obj;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;

public class Base {
	
	
	WebDriver driver;
	
	public Base(WebDriver driver){
		this.driver=driver;
	}
	
	
	// This method will select locator based on the user input and perform action based on the defined action key
	public void wait_for_element_and_perform_action(String locator,String attribute,@Optional String action,@Optional String value){
		
		WebElement element;
		WebDriverWait wait=new WebDriverWait(driver, 10);
		try{
		switch(locator){
		case "id":element=driver.findElement(By.id(attribute));
		      wait.until(ExpectedConditions.visibilityOf(element));
		      switch(action){
		      case "click":element.click();
		      break;
		      case "input":element.sendKeys(value);
		      break;
		      case "clear":element.clear();
		      break;
		      
		      }
		break;      
		case "css":element=driver.findElement(By.cssSelector(attribute));
		      wait.until(ExpectedConditions.visibilityOf(element));
		      switch(action){
		      case "click":element.click();
		      break;
		      case "input":element.sendKeys(value);
		      break;
		      case "clear":element.clear();
		      }
		break;      
		case "xpath":element=driver.findElement(By.xpath(attribute));
		      wait.until(ExpectedConditions.visibilityOf(element));
	          switch(action){
	          case "click":element.click();
	          break;
	          case "input":element.sendKeys(value);
	          break;
	          case "clear":element.clear();
	      }
	    break;      
		case "name":element=driver.findElement(By.name(attribute));
	          wait.until(ExpectedConditions.visibilityOf(element));
	          switch(action){
	          case "click":element.click();
	          break;
	          case "input":element.sendKeys(value);
	          break;
	          case "clear":element.clear();
	      }
	    break;
		case "tag":element=driver.findElement(By.tagName(attribute));
	     	wait.until(ExpectedConditions.visibilityOf(element));
            switch(action){
            case "click":element.click();
            break;
            case "input":element.sendKeys(value);
            break;
            case "clear":element.clear();
    }
  break;
		case "linktest":element=driver.findElement(By.linkText(attribute));
		element.click();
	      
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//This method will verify the visibility of any element based on locator and attribute
	public boolean verify_element_is_displaying(String locator,String attribute){
		WebDriverWait wait=new WebDriverWait(driver,10);
		WebElement element;
		switch(locator){
		case "id":element=driver.findElement(By.id(attribute));
		          wait.until(ExpectedConditions.visibilityOf(element));
		          return element.isDisplayed();
  
		case "css":element=driver.findElement(By.cssSelector(attribute));
                   wait.until(ExpectedConditions.visibilityOf(element));
                   return element.isDisplayed();
   
		case "xpath":element=driver.findElement(By.xpath(attribute));
                     wait.until(ExpectedConditions.visibilityOf(element));
                     return element.isDisplayed();
	     
		case "name":element=driver.findElement(By.cssSelector(attribute));
                    wait.until(ExpectedConditions.visibilityOf(element));
		            return element.isDisplayed();	
		default :
			return false;
		}
	}
	
	public boolean isAppLaunch(String title){
		WebDriverWait wait=new WebDriverWait(driver, 10);
		try{
			wait.until(ExpectedConditions.titleIs(title));
			return driver.getTitle().equals(title);
		}catch(Exception e){
			return false;
		}
	}
	// this method will switch the focus to new window
	public void switchWindow(){
		Set<String> windows=driver.getWindowHandles();
		for(String str:windows){
			if(!str.equals(driver.getWindowHandle())){
				driver.switchTo().window(str);
			}
		}
	}
	// This method will select the date
	public void pickUpDate( String xpathAttribute,String dd){
		try{
			
		WebDriverWait wait=new WebDriverWait(driver, 10);
		List<WebElement> listOfAvaDate=driver.findElements(By.xpath(xpathAttribute));
		for(int i=0;i<listOfAvaDate.size();i++){
			wait.until(ExpectedConditions.visibilityOf(listOfAvaDate.get(i)));
			if(listOfAvaDate.get(i).getText().equalsIgnoreCase(dd)){
				listOfAvaDate.get(i).click();
			}
		}
			
		}catch(NoSuchElementException e){
			System.out.println("Date is not available, please enter available date");
		}catch(StaleElementReferenceException e){
		}
	}
	// This method will select the seat based on the user preference 
	public void selectTheSeat(String category,String row,String seatNumber) throws InterruptedException{
		
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[class='seatmap-wrapper']")));
		driver.switchTo().frame(0);
		String locator="circle[id='SE"+"-"+category+"-"+row+"-"+seatNumber+"']";
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locator)));

		try{
	      	driver.findElement(By.cssSelector(locator)).click();
		}catch(NoSuchElementException e){
			System.out.println("Please enter the available seat");
		}
		driver.switchTo().parentFrame();
	}
	// This method will wait for book now button to be click-able
	public void waitForBookNowButton(String locator) throws InterruptedException{
		Thread.sleep(2000);// if images is enable we need fix wait time
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));	
	}
	
	}
	