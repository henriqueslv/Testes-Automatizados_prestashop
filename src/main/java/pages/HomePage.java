package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    // Criando lista para obter os elementos
    List<WebElement> listaProdutos = new ArrayList();

    // Apontamentos dos elementos de tela
    private By cards = By.className("thumbnail-container");

    private By totalCarrinho = By.className("cart-products-count");

    private By nomeProduto = By.cssSelector(".product-description a");

    private By precoProduto = By.cssSelector(".product-description .price");

    private By botaoSingIn = By.cssSelector(".user-info a");

    private By usuarioLogado =  By.cssSelector(".user-info span");

    private By botaoSingOut = By.cssSelector("a.logout");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }



    public void carregarListaProdutos(){
        listaProdutos = driver.findElements(cards);
    }

    public int contarProdutos(){
        carregarListaProdutos();
        return listaProdutos.size();
    }

    public int totalDeItensNoCarrinho(){
        String quantidadeDeItens = driver.findElement(totalCarrinho).getText();
        quantidadeDeItens = quantidadeDeItens.replace("(","");
        quantidadeDeItens = quantidadeDeItens.replace(")","");
        return Integer.parseInt(quantidadeDeItens);
    }

    public String obterNomeDoProduto(int indice){
         return driver.findElements(nomeProduto).get(indice).getText();
    }

    public String obterPrecoDoProduto(int indice){
        return driver.findElements(precoProduto).get(indice).getText();
    }

    public ProdutoPage clicarProduto(int indice){
        driver.findElements(nomeProduto).get(indice).click();
        return new ProdutoPage(driver);
    }

    public LoginPage clicarBotaoSignIn(){
        driver.findElement(botaoSingIn).click();
        return new LoginPage(driver);
    }

    public boolean usuarioLogado(String texto){
        return texto.contentEquals(driver.findElement(usuarioLogado).getText());
    }

    public void clicarBotaoSignOut(){
        driver.findElement(botaoSingOut).click();
    }

    public void carregarPaginaInicial() {
        driver.get("https://marcelodebittencourt.com/demoprestashop/");
    }

    public String obterTituloDaPagina() {
        return driver.getTitle();
    }

    public boolean usuarioLogado() {
        return !"Sign in".contentEquals(driver.findElement(usuarioLogado).getText());
    }
}
