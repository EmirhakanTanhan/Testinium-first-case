package com.testinium.testiniumcase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedConditions;
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

    //@AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        //Type 'bilgisayar' and click to submit button, search page should come
        main.searchInput.sendKeys("bilgisayar");
        main.searchButton.click();

        //Scroll down until getting the pagination button in the center of the view, then click the button
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true); return;", main.paginationButton);
        main.paginationButton.click();

        //Check either the pagination button is selected or not
        assertEquals("true", main.paginationButton.getAttribute("aria-current"));
    }

    @Test
    public void getProduct() {
        //Type 'bilgisayar' and click to submit button, search page should come
        main.searchInput.sendKeys("bilgisayar");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        main.searchButton.click();

        //Scroll down until getting the pagination button in the center of the view, then click the button
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(true); return;", main.paginationButton);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        main.paginationButton.click();

        //Get random product from the list
        Random rand = new Random();
        int upperBound = main.products.size();
        WebElement selectedProduct = main.products.get(rand.nextInt(upperBound));

        Actions actions = new Actions(driver);
        actions.moveToElement(selectedProduct).perform();

        WebElement addToCartButton = selectedProduct.findElement(By.cssSelector("[class='qjixn8-0 sc-1bydi5r-0 dGNqQc pXiHf sc-1n49x8z-3 bhXnM']"));
        js.executeScript("arguments[0].scrollIntoView(true); return;", addToCartButton);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        addToCartButton.click();
    }

}
