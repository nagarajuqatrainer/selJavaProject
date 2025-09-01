package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;



import base.DriverFactory;
import io.cucumber.java.en.Then;
import utils.AppLogger;
import utils.ElementUtil;

public class LoginPage {

     WebDriver driver;
     ElementUtil webelements = new ElementUtil(DriverFactory.getDriver());
     
     private String textBoxXpath = "//label[normalize-space(text())='%s']//following::input";
     private String buttonXpath = "//input[@type='submit' or @value='%s' or @name='%s']";
     private String commonButtonXpath = "//input[@value='%s']";
     
//     private String linksXpath = "//a[normalize-space(text())='%s' or contains(text(),'%s') or contains(@href,'%s')]";
//     private String welcomemessage = "//h5[normalize-space(text())='%s' or contains(text(),'%s') or contains(@href,'%s')]";
     private String logoutLink = "//a[normalize-space(text())='%s']";
     private String welcomemessage = "//h5[normalize-space(text())='%s']";
     private String uNameXpath = "//input[@name='user']";
     private String passwordXpath = "//input[@name='pass']";
     
     
     
	public LoginPage(WebDriver driver) {
		this.driver = DriverFactory.getDriver();
		 PageFactory.initElements(driver, this);
	
		}
	
	
	 
	public void clickSubmitButton(String value) {
		
		if(driver.getPageSource().contains(value)) {
			
			    // Build dynamic xpath using String.format
			    String dynamicXpath = String.format(commonButtonXpath, value);

			    WebElement submitButton = driver.findElement(By.xpath(dynamicXpath));
			    submitButton.click();
			}
}
	
	
	public void insertUserName(String field,String value) {
		
			if(driver.getPageSource().contains(field)) {
//			String dynamicXpath = String.format(textBoxXpath, field);   // replaces %s with label text
	        WebElement element = driver.findElement(By.xpath(uNameXpath));
	        element.sendKeys(value);
	        AppLogger.info("user enter user name successfully");
	        
	       	}else
	       	{
	       		AppLogger.info("Step failed");
	       	}
	}
	
	
	public void insertPassword(String field,String value) {
		
		if(driver.getPageSource().contains(field)) {
//			String dynamicXpath = String.format(textBoxXpath, field);   // replaces %s with label text
	        WebElement element = driver.findElement(By.xpath(passwordXpath));
	        element.sendKeys(value);
		
			}
	}
	
	
	
//	public void clickSubmitButton(String buttonName) {
//		
//			if(driver.getPageSource().contains("Login")) {
////			String dynamicXpath = String.format(buttonXpath, buttonName, buttonName);
//		    WebElement submitBtton =  driver.findElement(By.xpath(buttonXpath));
////		    webelements.clickButton(submitBtton);
//		    submitBtton.click();
//			}
//	}
	
	
	

	 public void verifyHomePage(String welcomeText) {
		
		if(driver.getPageSource().contains(welcomeText)) {
		String dynamicTextXpath = String.format(welcomemessage);
		WebElement welcome =  driver.findElement(By.xpath(dynamicTextXpath));
		webelements.verifyText(welcome, welcomeText);
		String actText = welcome.getText();
		
		if(actText.equals(welcomeText)) {
			System.out.println("Text not found");
		}else
		{
			System.out.println("Not found");
		}
		
		
		
		}
    }
	
	
	
	 public void clickLogoutButton(String logoutText) {
		   
		 if (driver.getPageSource().contains(logoutText)) {
		        // Replace %s with actual logout text
		        String dynamicXpath = String.format(logoutLink, logoutText);

		        WebElement logoutBtn = driver.findElement(By.xpath(dynamicXpath));
		        logoutBtn.click();
		    }
}
	
	
	
	
	
	

}
