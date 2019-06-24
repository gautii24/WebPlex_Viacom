package com.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WebPlexPageObject {

	WebDriver driver;
	
	public WebPlexPageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	 @FindBy(xpath = "//input[@id='j_username']") 
	 WebElement userName;
	 
	 @FindBy(xpath = "//input[@id='j_password']") 
	 WebElement password;
	 
	 @FindBy(xpath = "//input[@id='submit']") 
	 WebElement submitBtn;
	
	 @FindBy(xpath = "//button[@id='repository-layout-revision-selector']") 
	 WebElement branchDropdown;
	 
	 @FindBy(xpath = "//input[@id='repository-layout-revision-selector-dialog-search-input']") 
	 WebElement branchInputBox;
	 
	 @FindBy(xpath = "//td[@class='item-name']//a[contains(text(),'packages')]") 
	 WebElement packagesFolder;
	 
	 @FindBy(xpath = "//td[@class='item-name']//a[contains(text(),'apps')]") 
	 WebElement appsFolder;
	
	 @FindBy(xpath = "//a[contains(text(),'webplex-app')]") 
	 WebElement webplexAppsFolder;
	 
	 @FindBy(xpath = "//tr[@class='folder file-row']//a[contains(text(),'config')]") 
	 WebElement configFolder;
	 
	 @FindBy(xpath = "//span[@class='file-path']//a[contains(text(),'config')]") 
	 WebElement configBreadcrumbLink;
	 
	 public WebElement selectBreadcrumbLink(String site) {
		 String xpath = "//span[@class='file-path']//a[contains(text(),'*str*')]";
		 WebElement webElement =
			        driver.findElement(By.xpath(xpath.replace("*str*", site)));
			    return webElement;
	 }
	 
	 @FindBy(xpath = "//span[@class='file-path']//a[contains(text(),'WebPlex')]") 
	 WebElement webPlexFolder;
	 
	 @FindBy(xpath = "//a[@class='aui-button aui-button-link raw-view-link']") 
	 WebElement rawViewLink;
	
	 @FindBy(xpath = "//table[@id='browse-table']//tr[(contains(@class,'file file-row'))]") 
	 List<WebElement> configFiles;
	 
	 @FindBy(xpath = "//pre[contains(text(),'module.exports = {')]") 
	 WebElement jsData;
	
	 @FindBy(xpath = "//div[@class='CodeMirror-code']//div[@class='line']//span[@role='presentation']") 
	 List<WebElement> json;
	
	
	public WebElement selectBranchFromDropdown(String branchName) {
		 String xpath = "//span[contains(text(),'*str*')]";
		 WebElement webElement =
			        driver.findElement(By.xpath(xpath.replace("*str*", branchName)));
			    return webElement;
	 }
	
	public WebElement selectSiteFolder(String folder) {
		 String xpath = "//td[@class='item-name']//a[contains(text(),'*str*')]";
		 WebElement webElement =
			        driver.findElement(By.xpath(xpath.replace("*str*", folder)));
			    return webElement;
	}
	
	public WebElement selectLastFolder(String folder) {
		 String xpath = "//span[@class='file-path']//a[contains(text(),'*str*')]";
		 WebElement webElement =
			        driver.findElement(By.xpath(xpath.replace("*str*", folder)));
			    return webElement;
	}
	
	public WebElement selectconfigFile(String row) {
		String xpath = "//table[@id='browse-table']//tr[(contains(@class,'file file-row'))][*str*]/td[1]/a";
		 WebElement webElement =
			        driver.findElement(By.xpath(xpath.replace("*str*", row)));
			    return webElement;
		
	}
}
