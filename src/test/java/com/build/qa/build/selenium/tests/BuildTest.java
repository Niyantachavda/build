package com.build.qa.build.selenium.tests;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.build.qa.build.selenium.framework.BaseFramework;
import com.build.qa.build.selenium.pageobjects.homepage.HomePage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

public class BuildTest<var> extends BaseFramework { 

	/** 
	 * Extremely basic test that outlines some basic
	 * functionality and page objects as well as assertJ
	 */
	@Test
	public void navigateToHomePage() { 
		driver.get(getConfiguration("HOMEPAGE"));
		HomePage homePage = new HomePage(driver, wait);

		softly.assertThat(homePage.onBuildTheme())
		.as("The website should load up with the Build.com desktop theme.")
		.isTrue();
	}

	/** 
	 * Search for the Quoizel MY1613 from the search bar
	 * @throws InterruptedException 
	 * @assert: That the product page we land on is what is expected by checking the product title
	 * @difficulty Easy
	 */
	@Test
	public void searchForProductLandsOnCorrectProduct() throws InterruptedException { 
		// TODO: Implement this test
		Set<String> winids = driver.getWindowHandles();
		Iterator<String> iterator =winids.iterator();
		String firstwindow = iterator.next();
		winids = driver.getWindowHandles();
		iterator = winids.iterator();
		String popup =iterator.next();
		driver.switchTo().window(popup);

		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ESCAPE);
		driver.switchTo().window(firstwindow);

		JavascriptExecutor ff = (JavascriptExecutor)driver;
		String searchBox = null;
		searchBox = (WebElement) ff.executeScript("return document.getElementById('search_txt');", searchBox)+ ".value='Quoizel MY1613'";	
		WebElement SearchButton;
		try{

			WebDriverWait wait = new WebDriverWait(driver,3);
			SearchButton= (WebElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button-primary.search-site-search")));   
			SearchButton.click();
		}	
		catch (TimeoutException e){

		}
		//assertTrue(driver.findElement(By.linkText("Quoizel MY1613")).getText().contains("Quoizel MY1613"));


	}
	/** 
	 * Go to the Bathroom Sinks category directly (https://www.build.com/bathroom-sinks/c108504) 
	 * and add the second product on the search results (Category Drop) page to the cart.
	 * @throws InterruptedException 
	 * @assert: the product that is added to the cart is what is expected
	 * @difficulty Easy-Medium
	 */
	@Test
	public void addProductToCartFromCategoryDrop() throws InterruptedException { 
		// TODO: Implement this test
		driver.navigate().to("https://www.build.com/bathroom-sinks/c108504");
		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='product-composite-560374']/div[2]/a/div[1]/img")));
		try{
		driver.findElement(By.xpath(".//*[@id='product-composite-560374']/div[2]/a/div[1]/img")).click(); 
		}catch(ElementNotVisibleException e)
		{

		}
		driver.findElement(By.xpath(".//*[@id='configure-product-wrap']/button")).click();

		assertTrue(driver.findElement(By.linkText("Kohler K-2214")).getText().contains("Kohler K-2214"));

	}

	/** 
	 * Add a product to the cart and email the cart to yourself, also to my email address: jgilmore+SeleniumTest@build.com
	 * Include this message in the "message field" of the email form: "This is {yourName}, sending you a cart from my automation!"
	 * @assert that the "Cart Sent" success message is displayed after emailing the cart
	 * @difficulty Medium-Hard
	 */
	@Test
	public void addProductToCartAndEmailIt() { 



		JavascriptExecutor ff = (JavascriptExecutor)driver;
		String AddToCart = null;
		AddToCart = (WebElement) ff.executeScript("return document.getElementById('add-to-cart-wrap');", AddToCart)+ ".click()";

		WebElement table;

		try{
			WebDriverWait wait = new WebDriverWait(driver,3);
			table= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='page-content']/div[1]/div[1]/div/section[2]/div/div[1]/table")));   
		}	
		catch (TimeoutException e){
		}

		String EmailButton;
		EmailButton = (WebElement) ff.executeScript("return document.getElementById('add-to-cart-wrap');", AddToCart)+ ".click()";



		Set<String> winids = driver.getWindowHandles();
		Iterator<String> iterator =winids.iterator();
		String firstwindow = iterator.next();
		winids = driver.getWindowHandles();
		iterator = winids.iterator();
		String popup =iterator.next();
		driver.switchTo().window(popup);

		try{
		new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated((By.cssSelector("[tabindex='1']")))).sendKeys("Niyanta");
		driver.findElement(By.xpath(".//*[@id='yourEmail']")).sendKeys("niyanta.chavda99@gmail.com");
		driver.findElement(By.xpath(".//*[@id='recipientName']")).sendKeys("Niyanta");
		driver.findElement(By.xpath(".//*[@id='recipientEmail']")).sendKeys("niyanta.chavda@yahoo.com");
		driver.findElement(By.xpath(".//*[@id='recipientPhone']")).sendKeys("408-886-1404");
		driver.findElement(By.xpath(".//*[@id='otherRecipients']")).sendKeys("jgilmore+SeleniumTest@build.com");
		driver.findElement(By.xpath(".//*[@id='projectTitle']")).sendKeys("Build.com");
		driver.findElement(By.xpath(".//*[@id='quoteMessage']")).sendKeys("This is Niyanta Chavda, sending you a cart from my automation!");

		driver.findElement(By.xpath(".//*[@id='estimatedCloseDate']")).sendKeys("09/24/2017");
		driver.findElement(By.xpath(".//*[@id='cart-email']/div/div/div[2]/div[2]/form/div[4]/button")).click();


		}
		catch(TimeoutException t){

		}

	//String alertMessage = driver.switchTo().alert().getText();
	//Assert.assertEquals("Cart Sent", alertMessage);

	}
	 


	/** 
	 * Go to a category drop page (such as Bathroom Faucets) and narrow by
	 * at least two filters (facets), e.g: Finish=Chromes and Theme=Modern
	 * @assert that the correct filters are being narrowed, and the result count
	 * is correct, such that each facet selection is narrowing the product count.
	 * @difficulty Hard
	 */
	@Test
	public void facetNarrowBysResultInCorrectProductCounts() { 
		// TODO: Implement this test
		Select category;
		try{
			WebDriverWait wait = new WebDriverWait(driver,3);
			category = (Select) wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header-menu-style.shop-all-tab>a")));   
			category.selectByVisibleText("Bathroom");
			Select dropBathroom = new Select(driver.findElement(By.cssSelector("#shop-all>li>av")));
			dropBathroom.selectByVisibleText("Bathroom Faucets");
		}catch(TimeoutException t){

		}


	}
}
