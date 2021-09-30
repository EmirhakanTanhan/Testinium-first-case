package com.testinium.testiniumcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Main {
    @FindBy(css = "[data-cy='header-search-input']")
    public WebElement searchInput;

    @FindBy(css = "[data-cy='search-find-button']")
    public WebElement searchButton;

    @FindBy(xpath = "//li[@data-testid='pagination-list-item']/a[@role]/span[contains(text(), '2')]/..")
    public WebElement paginationButton;

//    @FindBy(css = "[class='sc-1nx8ums-0 dyekHG']")
    @FindBy(xpath = "//li[@class='sc-1nx8ums-0 dyekHG']")
    public List<WebElement> products;

    public Main(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
