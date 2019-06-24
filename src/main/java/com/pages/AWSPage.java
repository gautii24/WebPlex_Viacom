package com.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import com.core.BaseClass;
import com.utility.URLUtility;


public class AWSPage extends BaseClass {
	
	AWSPageObject awsPageObject;
	
	public AWSPage()
	{
		awsPageObject = new AWSPageObject(driver);
	}
	
	public void clickRoute53()
	{
		awsPageObject.route53.click();
	}
	
	public void enterUsername()
	{
		awsPageObject.usernameTab.sendKeys("mandev");
	}
	
	public void enterPassword()
	{
		awsPageObject.passwordTab.sendKeys("Welcome1");
	}
	
	public void clickLoginButton()
	{
		awsPageObject.loginButton.click();
	}
	
	public void clickHostedZones()
	{
		awsPageObject.hostedZones.click();
	}
	
	public void clickWebplexIo()
	{
		awsPageObject.webplexLink.click();
	}
	
	public void enterURLSearchTab() throws InterruptedException
	{
		awsPageObject.searchTab.sendKeys(URLUtility.findURLAWS());
		awsPageObject.searchTab.sendKeys(Keys.ENTER);
	}
	
	public void selectCname()
	{
		Select sc = new Select(awsPageObject.dropMenu);
		
		sc.selectByVisibleText("CNAME – Canonical name");
	}
	
	public String getTextAreaValue()
	{ 
		String value = null;
		
		
		value = awsPageObject.valueTextArea.getAttribute("value");
		
		return value;
		
	}
	
	public void clickSearchedLink() throws InterruptedException
	{
		//int count = awsPageObject.links.size();
		
		int position=0;
		
		String CustomisedURL=null;
		
		for(int i=0; i < awsPageObject.links.size(); i++)
			{
				String abc = awsPageObject.links.get(i).getText();
				
				System.out.println(abc);
				
				CustomisedURL = URLUtility.findURLAWS();
				
				if(abc.startsWith(CustomisedURL))
				{
					position = i;
					
					break;
				}
				
			}
		awsPageObject.links.get(position).click();
	}
}
