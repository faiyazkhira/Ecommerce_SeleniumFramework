package mfk.SeleniumFrameworkDesign;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import mfk.TestComponents.BaseTest;
import mfk.pageObjects.CartPage;
import mfk.pageObjects.CheckoutPage;
import mfk.pageObjects.OrdersPage;
import mfk.pageObjects.ProductCatalogue;
import mfk.pageObjects.SummaryPage;

public class SubmitOrderTest extends BaseTest {
	public String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password")); // landing
																														// page

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("productName")); // product catalog page
		CartPage cartPage = productCatalogue.viewCart();

		Boolean match = cartPage.validateProduct(input.get("productName")); // cart page
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.checkout();

		checkoutPage.selectCountry("india"); // checkout page
		SummaryPage summaryPage = checkoutPage.placeOrder();

		String confirmMessage = summaryPage.validatePurchase(); // summary page
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void orderHistory() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("faz@gmail.com", "Test@123"); // landing page
		OrdersPage ordersPage = productCatalogue.viewOrders();
		Boolean match = ordersPage.validateOrder(productName);
		Assert.assertTrue(match);
	}

	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\mfk\\data\\PurchaseOrder.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

//	HashMap<String, String> map = new HashMap<String, String>();
//	map.put("email", "faz@gmail.com");
//	map.put("password", "Test@123");
//	map.put("productName", "ZARA COAT 3");
//
//	HashMap<String, String> map1 = new HashMap<String, String>();
//	map1.put("email", "faz2@gmail.com");
//	map1.put("password", "Test@123");
//	map1.put("productName", "ADIDAS ORIGINAL");

}
