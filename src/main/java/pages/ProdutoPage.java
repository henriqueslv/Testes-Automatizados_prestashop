package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProdutoPage {

    private WebDriver driver;

    // Apontamentos dos elementos de tela
    private By nomeProduto = By.className("h1");

    private By precoProduto = By.cssSelector(".current-price span:nth-child(1)");

    private By tamanhoProduto = By.id("group_1");

    private By corPreta = By.xpath("//ul[@id='group_2']//input[@value='11']");

    private By quantidadeProduto = By.id("quantity_wanted");

    private By addCarrinho = By.className("add-to-cart");



    public ProdutoPage(WebDriver driver) {
        this.driver = driver;
    }

    public String obterNomeProduto_produtoPage(){
        return driver.findElement(nomeProduto).getText();
    }

    public String obterPrecoProduto_produtoPage(){
        return driver.findElement(precoProduto).getText();
    }

    public Select encontrarDropDown(){
        return new Select (driver.findElement(tamanhoProduto));
    }

    public List<String> obterOpcoesSelecionadas(){
        List<WebElement> elementosSelecionados = encontrarDropDown().getAllSelectedOptions();

        List<String> listaOpcoesSelecionadas =  new ArrayList();
        for(WebElement element : elementosSelecionados){
            listaOpcoesSelecionadas.add(element.getText());
        }
        return  listaOpcoesSelecionadas;
    }

    public void selecionarOpcaoDropDown(String opcao){
        encontrarDropDown().selectByVisibleText(opcao);
    }

    public void selecionarCorPreta(){
        driver.findElement(corPreta).click();
    }

    public void alterarQuantidade(int quantidade){
        driver.findElement(quantidadeProduto).clear();
        driver.findElement(quantidadeProduto).sendKeys(Integer.toString(quantidade));
    }

    public ModalPage adicionarNoCarrinho(){
        driver.findElement(addCarrinho).click();
        return new ModalPage(driver);
    }
}
