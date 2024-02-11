package mfk.SeleniumFrameworkDesign;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import mfk.pageObjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriver driver = new ChromeDriver();

		// hit the url
		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String productName = "ZARA COAT 3";

		// object creation of POM
		LandingPage landingPage = new LandingPage(driver);

		// login
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("faz@gmail.com");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Test@123");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// getting a list of all the products and selecting desired product
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName))
				.findFirst().orElse(null);
		prod.findElement(By.cssSelector("button[style='float: right;']")).click();

		// validating added to cart pop up and loading animation
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		// click on cart button
		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();

		// validate products on cart page
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);

		// click on checkout button
		driver.findElement(By.xpath("//button[text()='Checkout']")).click();

		// select country on checkout page

		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")), "india").build()
				.perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='ta-results']")));
		driver.findElement(By.cssSelector(" button[class*='ta-item']:nth-child(3)")).click();

//		String desiredCountry = "India";
//		driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("ind");
//
//		List<WebElement> matchingCountries = driver.findElements(By.cssSelector("button[class*='list-group-item']"));
//		matchingCountries.stream().filter(country -> country.getText().equalsIgnoreCase(desiredCountry));
//		for (int i = 0; i < matchingCountries.size(); i++) {
//			if (matchingCountries.get(i).getText().equalsIgnoreCase(desiredCountry)) {
//				matchingCountries.get(i).click();
//			}
//		}

		// Place order
		driver.findElement(By.cssSelector("a[class*='action__submit']")).click();

		// Validate successful purchase on order summary page
		String confirmMessage = driver.findElement(By.cssSelector("h1[class='hero-primary']")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();

	}

}
