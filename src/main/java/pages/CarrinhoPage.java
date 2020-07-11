package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {

    private WebDriver driver;

    // Apontamentos dos elementos de tela
    private By nomeProduto = By.cssSelector(".product-line-info a");

    private By precoProduto = By.cssSelector("span.price");

    private By listaTamanhoECor = By.cssSelector(".product-line-info .value");

    private By quantidadeProduto = By.cssSelector("input.js-cart-line-product-quantity");

    private By subtotal = By.cssSelector("span.product-price strong");

    private By numeroItens_total = By.cssSelector("span.js-subtotal");

    private By subtotal_total = By.cssSelector("#cart-subtotal-products .value");

    private By shipping_total = By.cssSelector("#cart-subtotal-shipping span.value");

    private By total_total = By.cssSelector(".cart-total span.value");

    private By proceedToCheckout = By.cssSelector(".text-sm-center .btn-primary");

    public CarrinhoPage(WebDriver driver) {
        this.driver = driver;
    }

    public String obterNomeProduto(){
        return driver.findElement(nomeProduto).getText();
    }

    public String obterPrecoProduto(){
        return driver.findElement(precoProduto).getText();
    }

    public String obterTamanhoProduto(){
        return driver.findElements(listaTamanhoECor).get(0).getText();
    }

    public String obterCorProduto(){
        return driver.findElements(listaTamanhoECor).get(1).getText();
    }

    public String obterQuantidadeProduto(){
    return driver.findElement(quantidadeProduto).getAttribute("value");
    }

    public String obterSubtotalProduto(){
        return driver.findElement(subtotal).getText();
    }

    public String obterNumeroDeItems(){
        return driver.findElement(numeroItens_total).getText();
    }

    public String obterSubtotal_total(){
        return driver.findElement(subtotal_total).getText();
    }

    public String obterShipping_total(){
        return driver.findElement(shipping_total).getText();
    }

    public String obterTotal_total(){
        return driver.findElement(total_total).getText();
    }

    public CheckoutPage clicarProceedToCheckout(){
        driver.findElement(proceedToCheckout).click();
        return new CheckoutPage(driver);
    }


}
