package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.Funcoes;

public class PedidoPage {
    private WebDriver driver;

    private By mensagemDeConfirmacao = By.cssSelector("#content-hook_order_confirmation .card-title");

    private By emailConfirmacao = By.cssSelector("#content-hook_order_confirmation p");

    private By subtotalConfirmacao = By.className("bold");

    private By totalConfirmacao = By.cssSelector(".order-confirmation-table table tr.total-value td:nth-child(2)");


    public PedidoPage(WebDriver driver) {
        this.driver = driver;
    }

    public String retornar_pedidoConfirmado() {
        return driver.findElement(mensagemDeConfirmacao).getText();
    }

    public String retornar_emailConfirmacao() {
        String texto = driver.findElement(emailConfirmacao).getText();
        texto = Funcoes.replace_removeTexto(texto, "An email has been sent to the ");
        texto = Funcoes.replace_removeTexto(texto, " address.");
        return texto;
    }

    public Double retornar_subtotalConfirmacao() {
        return Funcoes.replace_removeCifrao(driver.findElement(subtotalConfirmacao).getText());
    }

    public Double retornar_totalConfirmacao() {
        return Funcoes.replace_removeCifrao(driver.findElement(totalConfirmacao).getText());
    }



}
