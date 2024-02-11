package mfk.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mfk.AbstractComponents.AbstractComponents;

public class OrdersPage extends AbstractComponents {
	WebDriver driver;

	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory
	@FindBy(xpath = "//tr/td[2]")
	List<WebElement> orders;

	public List<WebElement> getOrderNames() {
		return orders;
	}

	public Boolean validateOrder(String productName) {
		Boolean match = getOrderNames().stream()
				.anyMatch(orderedItem -> orderedItem.getText().equalsIgnoreCase(productName));
		return match;
	}

}