package mfk.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mfk.AbstractComponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents {
	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "button[class*='ta-item']:nth-child(3)")
	WebElement selectedCountry;

	@FindBy(css = "a[class*='action__submit']")
	WebElement placeOrder;

	By dropdownResults = By.cssSelector("[class*='ta-results']");

	@FindBy(css = "input[placeholder='Select Country']")
	WebElement myCountry;

	public void selectCountry(String country) throws InterruptedException {
		getActions().sendKeys(myCountry, country).build().perform();
		waitForElementToAppear(dropdownResults);
		selectedCountry.click();
	}

	public SummaryPage placeOrder() {
		placeOrder.click();
		SummaryPage summaryPage = new SummaryPage(driver);
		return summaryPage;
	}

}