package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    // Apontamentos dos elementos de tela
    private By email = By.name("email");

    private By senha = By.name("password");

    private By clicarLogin = By.id("submit-login");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inserirEmail(String texto) {
        driver.findElement(email).sendKeys(texto);
    }

    public void inserirSenha(String texto) {
        driver.findElement(senha).sendKeys(texto);
    }

    public void fazerLogin() {
        driver.findElement(clicarLogin).click();
    }
}
