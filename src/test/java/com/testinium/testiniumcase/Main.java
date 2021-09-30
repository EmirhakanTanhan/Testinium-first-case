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

    @FindBy(css = "[class='sc-1i52yn4-0 cXPYPb']")
    public WebElement emptyPageIdentifier;

    @FindBy(xpath = "//li[@data-testid='pagination-list-item']/a[@role]/span[contains(text(), '2')]/..")
    public WebElement paginationButton;

    @FindBy(xpath = "//li[@class='sc-1nx8ums-0 dyekHG']")
    public List<WebElement> products;

    @FindBy(css = "[class='sc-84am1q-0 sc-1r48nyr-0 WZTpV kEgEYI']")
    public WebElement cartButton;

    @FindBy(xpath = "//div[@class='gg-w-offset-1 gg-w-6 gg-d-offset-1 gg-d-6 gg-t-offset-0 gg-t-7 gg-m-offset-0 gg-m-17 pull-right-m pr0']/div/div[@class='total-price']")
    public WebElement productInBasket;

    @FindBy(xpath = "//div[@class='gg-input gg-input-select ']/select")
    public WebElement cartProductQuantity;

    @FindBy(xpath = "//div[@class='gg-d-24 hidden-m update-buttons-container']/div/a[@class='btn-delete btn-update-item']")
    public WebElement cartProductDeleteButton;

    public Main(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
