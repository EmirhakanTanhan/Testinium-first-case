package com.testinium.testiniumcase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.sql.Time;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainTest {
    private WebDriver driver;
    private Main main;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        Dimension pageSize = new Dimension(1200, 800);
        driver.manage().window().setSize(pageSize);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.gittigidiyor.com/");

        main = new Main(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        //Type 'bilgisayar' and click to submit button, search page should come
        main.searchInput.sendKeys("bilgisayar");
        main.searchButton.click();

        if (!driver.findElements(By.cssSelector("[class='sc-1i52yn4-0 cXPYPb']")).isEmpty()) {
            driver.get(driver.getCurrentUrl());
        }

        //Scroll down until getting the pagination button in the center of the view, then click the button
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true); return;", main.paginationButton);
        main.paginationButton.click();

        //Check either the pagination button is selected or not
        assertEquals("true", main.paginationButton.getAttribute("aria-current"));
    }

    @Test
    public void compareProductPrices() {
        //Type 'bilgisayar' and click to submit button, search page should come
        main.searchInput.sendKeys("bilgisayar");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        main.searchButton.click();

        if (!driver.findElements(By.cssSelector("[class='sc-1i52yn4-0 cXPYPb']")).isEmpty()) {
            driver.get(driver.getCurrentUrl());
        }

        //Scroll down until getting the pagination button in the center of the view, then click the button
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true); return;", main.paginationButton);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        main.paginationButton.click();

        if (!driver.findElements(By.cssSelector("[class='sc-1i52yn4-0 cXPYPb']")).isEmpty()) {
            driver.get(driver.getCurrentUrl());
        }

        //Get random product from the list
        Random rand = new Random();
        int upperBound = main.products.size();
        WebElement selectedProduct = main.products.get(rand.nextInt(upperBound));

        //Hover over the product to be able to see 'add to cart' button
        Actions actions = new Actions(driver);
        actions.moveToElement(selectedProduct).perform();

        //Save the price of the selected product
        String priceTagOnList = selectedProduct.findElement(By.cssSelector("[class='sc-1tdlrg-0 yf6n83-0 jPaJyh elANpG sc-1n2i5kn-3 lcRLro']")).getText();
        System.out.println(priceTagOnList);

        //Scroll into view the 'add to cart' button and click it
        WebElement addToCartButton = selectedProduct.findElement(By.cssSelector("[class='qjixn8-0 sc-1bydi5r-0 dGNqQc pXiHf sc-1n49x8z-3 bhXnM']"));
        js.executeScript("arguments[0].scrollIntoView(true); return;", addToCartButton);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        addToCartButton.click();
        WebDriverWait wt = new WebDriverWait(driver,5);
        wt.until(ExpectedConditions.
                invisibilityOfElementLocated(By.cssSelector("[class='gekhq4-7 MOSbm']")));

        //Go into the cart page and compare prices
        main.cartButton.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String priceTagOnCart = main.productInBasket.getText();
        System.out.println(priceTagOnCart);

        assertEquals(priceTagOnList, priceTagOnCart);
    }

    @Test
    public void cartOperationChangeQuantity () {
        //Type 'bilgisayar' and click to submit button, search page should come
        main.searchInput.sendKeys("bilgisayar");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        main.searchButton.click();

        if (!driver.findElements(By.cssSelector("[class='sc-1i52yn4-0 cXPYPb']")).isEmpty()) {
            driver.get(driver.getCurrentUrl());
        }

        //Scroll down until getting the pagination button in the center of the view, then click the button
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true); return;", main.paginationButton);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        main.paginationButton.click();

        if (!driver.findElements(By.cssSelector("[class='sc-1i52yn4-0 cXPYPb']")).isEmpty()) {
            driver.get(driver.getCurrentUrl());
        }

        //Get random product from the list
        Random rand = new Random();
        int upperBound = main.products.size();
        WebElement selectedProduct = main.products.get(rand.nextInt(upperBound));

        //Hover over the product to be able to see 'add to cart' button
        Actions actions = new Actions(driver);
        actions.moveToElement(selectedProduct).perform();

        //Scroll into view the 'add to cart' button and click it
        WebElement addToCartButton = selectedProduct.findElement(By.cssSelector("[class='qjixn8-0 sc-1bydi5r-0 dGNqQc pXiHf sc-1n49x8z-3 bhXnM']"));
        js.executeScript("arguments[0].scrollIntoView(true); return;", addToCartButton);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        addToCartButton.click();
        WebDriverWait wt = new WebDriverWait(driver,5);
        wt.until(ExpectedConditions.
                invisibilityOfElementLocated(By.cssSelector("[class='gekhq4-7 MOSbm']")));

        //Go into the cart page and change quantity of the product
        main.cartButton.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Select dropdown = new Select(main.cartProductQuantity);
        dropdown.selectByVisibleText("2");

        //Verify the quantity is equal to 2
        String productQuantity = main.cartProductQuantity.getAttribute("value");
        assertEquals("2", productQuantity);
    }

    @Test
    public void cartOperationDeleteProduct () {
        //Type 'bilgisayar' and click to submit button, search page should come
        main.searchInput.sendKeys("bilgisayar");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        main.searchButton.click();

        if (!driver.findElements(By.cssSelector("[class='sc-1i52yn4-0 cXPYPb']")).isEmpty()) {
            driver.get(driver.getCurrentUrl());
        }

        //Scroll down until getting the pagination button in the center of the view, then click the button
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true); return;", main.paginationButton);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        main.paginationButton.click();

        if (!driver.findElements(By.cssSelector("[class='sc-1i52yn4-0 cXPYPb']")).isEmpty()) {
            driver.get(driver.getCurrentUrl());
        }

        //Get random product from the list
        Random rand = new Random();
        int upperBound = main.products.size();
        WebElement selectedProduct = main.products.get(rand.nextInt(upperBound));

        //Hover over the product to be able to see 'add to cart' button
        Actions actions = new Actions(driver);
        actions.moveToElement(selectedProduct).perform();

        //Scroll into view the 'add to cart' button and click it
        WebElement addToCartButton = selectedProduct.findElement(By.cssSelector("[class='qjixn8-0 sc-1bydi5r-0 dGNqQc pXiHf sc-1n49x8z-3 bhXnM']"));
        js.executeScript("arguments[0].scrollIntoView(true); return;", addToCartButton);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        addToCartButton.click();
        WebDriverWait wt = new WebDriverWait(driver,10);
        wt.until(ExpectedConditions.
                invisibilityOfElementLocated(By.cssSelector("[class='gekhq4-7 MOSbm']")));

        //Go into the cart page and delete product
        main.cartButton.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        main.cartProductDeleteButton.click();
        wt.until(ExpectedConditions.
                invisibilityOfElementLocated(By.xpath("//div[not(contains(@class,'hidden')) and contains(@id,'empty-cart-container')]")));
    }

}
