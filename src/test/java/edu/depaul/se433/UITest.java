package edu.depaul.se433;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class UITest {
    private static WebDriver driver;
    private SoftAssertions softly;

    private WebElement customerName, destinationState, nameOfProduct, unitPrice, quantity, addItem, currentTotal, checkOut, result, avg;

    private Select shippingOption;
    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupSoftly() {
        softly = new SoftAssertions();
        driver = new ChromeDriver();
        driver.get("http://localhost:8085/?#");
        customerName = driver.findElement(By.id("customer-name"));
        destinationState = driver.findElement(By.id("state"));
        shippingOption = new Select(driver.findElement(By.id("shipping")));
        nameOfProduct = driver.findElement(By.id("name"));
        unitPrice = driver.findElement(By.id("unit_price"));
        quantity = driver.findElement(By.id("quantity"));
        addItem = driver.findElement(By.id("add-item-btn"));
        currentTotal = driver.findElement(By.id("get-price-btn"));
        checkOut = driver.findElement(By.id("checkout-btn"));
        result = driver.findElement(By.id("result"));
        avg = driver.findElement(By.id("avg"));
    }

    @AfterEach
    void executeVerification() {
        softly.assertAll();
        driver.quit();
        customerName = null;
        destinationState = null;
        shippingOption = null;
        nameOfProduct = null;
        unitPrice = null;
        quantity = null;
        addItem = null;
        currentTotal = null;
        checkOut = null;
        result = null;
        avg = null;
    }

    @Test
    @DisplayName("Verify basic checkout processing")
    void testBasicCheckout() throws InterruptedException {
        customerName.sendKeys("Antonio");
        destinationState.sendKeys("AK");
        shippingOption.selectByIndex(1);
        nameOfProduct.sendKeys("Product A");
        unitPrice.sendKeys("10");
        quantity.sendKeys("1");
        addItem.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("Cart contains 1 items");
        currentTotal.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("Error: Request failed with status code 500");
        checkOut.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("ok");
        driver.quit();
    }

    @Test
    @DisplayName("Verify error thrown on Destination Error")
    void testDestinationErrorCheckout() throws InterruptedException {
        customerName.sendKeys("Antonio");
        destinationState.sendKeys("FAKE DESTINATION");
        shippingOption.selectByIndex(1);
        nameOfProduct.sendKeys("Product A");
        unitPrice.sendKeys("10");
        quantity.sendKeys("1");
        addItem.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("Cart contains 1 items");
        currentTotal.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("total: 20");
        checkOut.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("Error: Request failed with status code 500");
        driver.quit();
    }

    @Test
    @DisplayName("Verify error thrown on Shipping Option Error")
    void testShippingOptionErrorCheckout() throws InterruptedException {
        customerName.sendKeys("Antonio");
        destinationState.sendKeys("AK");
        shippingOption.selectByIndex(0);
        nameOfProduct.sendKeys("Product A");
        unitPrice.sendKeys("10");
        quantity.sendKeys("1");
        addItem.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("Cart contains 1 items");
        currentTotal.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("total: 20");
        checkOut.click();
        Thread.sleep(1000);
        softly.assertThat(result.getText()).as("result").isEqualTo("Error: Request failed with status code 500");
        driver.quit();
    }
}
