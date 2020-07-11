package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class ModalPage {

    private WebDriver driver;

    // Apontamentos dos elementos de tela
    private By mensagemProdutoAdicionado = By.id("myModalLabel");

    private By nomeProduto = By.className("product-name");

    private By precoProduto = By.cssSelector(".divide-right .product-price");

    private By listaDeValores = By.cssSelector(".divide-right .col-md-6:nth-child(2) span strong");

    private By subTotal = By.cssSelector(".cart-content p:nth-child(2) span.value");

    private By total = By.cssSelector(".cart-content p:nth-child(5) span.value");

    private By checkout = By.cssSelector(".cart-content-btn a");

    public ModalPage(WebDriver driver) {
        this.driver = driver;
    }

    public String obterMensagemProdutoAdicionado() {
        //ignorar e esperar 5s
        FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        // condição de espera - esperar até que apareca a mensagem
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mensagemProdutoAdicionado));

        return driver.findElement(mensagemProdutoAdicionado).getText();
    }

    public String obterNomeProduto_modalPage() {
        return driver.findElement(nomeProduto).getText();
    }

    public String obterPrecoProduto() {
        return driver.findElement(precoProduto).getText();
    }

    public String obterTamanhoProduto() {
            return driver.findElements(listaDeValores).get(0).getText();
    }

    public String obterCorProduto() {
        if(driver.findElements(listaDeValores).size() == 3)
            return driver.findElements(listaDeValores).get(1).getText();
        else return "N/A";
    }

    public String obterQuantidadeProduto() {
        if(driver.findElements(listaDeValores).size() == 3)
            return driver.findElements(listaDeValores).get(2).getText();
        else
            return driver.findElements(listaDeValores).get(1).getText();
    }

    public String obterSubTotal() {
        return driver.findElement(subTotal).getText();
    }

    public String obterTotal() {
        return driver.findElement(total).getText();
    }

    public CarrinhoPage clicarCheckout() {
        driver.findElement(checkout).click();
        return new CarrinhoPage(driver);
    }
}
