package com.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class AWSPageObject {
	
	public AWSPageObject(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@id='Route_53']//span[contains(text(),'Route 53')]")
	WebElement route53;
	
	@FindBy(xpath="//input[@id='ctl00_ContentPlaceHolder1_UsernameTextBox']")
	WebElement usernameTab;
	
	@FindBy(xpath="//input[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']")
	WebElement passwordTab;
	
	@FindBy(xpath="//input[@id='ctl00_ContentPlaceHolder1_SubmitButton']")
	WebElement loginButton;
	
	@FindBy(xpath="//a[contains(text(),'Hosted zones')]")
	WebElement hostedZones;
	
	@FindBy(xpath="//a[contains(text(),'webplex.vmn.io.')]")
	WebElement webplexLink;
	
	@FindBy(xpath="//input[@placeholder='Record Set Name']")
	WebElement searchTab;
	
	@FindBy(xpath="(//select[@class='gwt-ListBox GEHJJTKDEHE'])[1]")
	WebElement dropMenu;
	
	@FindBy(xpath="//textarea[@class='gwt-TextArea']")
	WebElement valueTextArea;
	
	@FindBy(xpath="//td[@align='right']//div[@class='GEHJJTKDARE']")
	List<WebElement> links;
}
