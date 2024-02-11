package mfk.SeleniumFrameworkDesign;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import mfk.TestComponents.BaseTest;
import mfk.pageObjects.CartPage;
import mfk.pageObjects.ProductCatalogue;

public class ErrorValidationTest extends BaseTest {

	@Test(groups = { "ErrorHandling" })
	public void loginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("faz@gmail.com", "Test123"); // landing page
		Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage());

	}

	@Test
	public void productErrorValidation() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";

		ProductCatalogue productCatalogue = landingPage.loginApplication("faz2@gmail.com", "Test@123"); // landing page

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName); // product catalog page
		CartPage cartPage = productCatalogue.viewCart();

		Boolean match = cartPage.validateProduct("ZARA COAT 33"); // cart page
		Assert.assertFalse(match);

	}

}
