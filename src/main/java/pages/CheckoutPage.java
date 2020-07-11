package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    private By totalTaxIncTotal = By.cssSelector(".cart-total span.value");

    private By nomeCliente = By.cssSelector("div.address");

    private By botaoContinue = By.name("confirm-addresses");

    private By shipping = By.className("carrier-price");

    private By botaoContinueShipping = By.name("confirmDeliveryOption");

    private By payByCheck = By.id("payment-option-1");

    private  By aceitarTermos = By.cssSelector("span.custom-checkbox");

    private By gerarOrder = By.cssSelector("#payment-confirmation .center-block");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public String obter_totalTaxTotal(){
        return driver.findElement(totalTaxIncTotal).getText();
    }

    public String obter_nomeCliente(){
        return driver.findElement(nomeCliente).getText();
    }

    public void clicarBotaoContinue(){
        driver.findElement(botaoContinue).click();
    }

    public String obter_shipping(){
        return driver.findElement(shipping).getText();
    }

    public void clicarBotaoContinueShipping(){
        driver.findElement(botaoContinueShipping).click();
    }

    public void clicarPayByCheck(){
        driver.findElement(payByCheck).click();
    }

    public void clicarAceitarTermos(){
        driver.findElement(aceitarTermos).click();
    }

    public PedidoPage clicarGerarOrder(){
        driver.findElement(gerarOrder).click();
        return new PedidoPage(driver);
    }
}
