package mfk.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mfk.AbstractComponents.AbstractComponents;

public class SummaryPage extends AbstractComponents {
	WebDriver driver;

	public SummaryPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory
	@FindBy(css = "h1[class='hero-primary']")
	WebElement orderMessage;

	public String validatePurchase() {
		String confirmMessage = orderMessage.getText();
		return confirmMessage;

	}
}