package steps;

import com.google.common.io.Files;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.ModalPage;
import pages.ProdutoPage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComprarProdutosSteps {

    private static WebDriver driver;
    private HomePage homePage = new HomePage(driver);

    @Before
    public static void inicializar() {
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver\\83\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //Tempo de esperar implicita, normalmente o máximo são 3s
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Dado("que estou na pagina inicial")
    public void que_estou_na_pagina_inicial() {
        homePage.carregarPaginaInicial();
        assertThat(homePage.obterTituloDaPagina(), is("Loja de Teste"));
    }

    @Quando("nao estou logado")
    public void nao_estou_logado() {
        homePage.usuarioLogado();
        assertThat(homePage.usuarioLogado(), is(false));
    }

    @Entao("visualizo {int} produtos disponiveis")
    public void visualizo_produtos_disponiveis(Integer int1) {
        assertThat(homePage.contarProdutos(), is(int1));
    }

    @Entao("carrinho esta zerado")
    public void carrinho_esta_zerado() {
        assertThat(homePage.totalDeItensNoCarrinho(), is(0));
    }

    LoginPage loginPage;

    @Quando("estou logado")
    public void estou_logado() {
        // Clicar no botão SignIn
        loginPage = homePage.clicarBotaoSignIn();

        // Na página login inserir os dados e logar
        loginPage.inserirEmail("henrique@teste.com");
        loginPage.inserirSenha("henrique");
        loginPage.fazerLogin();

        // Validar se o usuário está logado
        assertThat(homePage.usuarioLogado("Henrique Silva"), is(true));

        homePage.carregarPaginaInicial();
    }

    ProdutoPage produtoPage;
    String nomeDoProduto_ProdutoPage;
    String precoDoProduto_ProdutoPage;

    String nomeDoProduto_HomePage;
    String precoDoProduto_HomePage;

    @Quando("seleciono um produto na posicao {int}")
    public void seleciono_um_produto_na_posicao(Integer indice) {
        nomeDoProduto_HomePage = homePage.obterNomeDoProduto(indice);
        precoDoProduto_HomePage = homePage.obterPrecoDoProduto(indice);

        produtoPage = homePage.clicarProduto(indice);

        nomeDoProduto_ProdutoPage = produtoPage.obterNomeProduto_produtoPage();
        precoDoProduto_ProdutoPage = produtoPage.obterPrecoProduto_produtoPage();
    }

    @Quando("nome do produto na tela principal e na tela produto eh {string}")
    public void nome_do_produto_na_tela_princical_eh(String nomeProduto) {
        assertThat(nomeDoProduto_HomePage.toUpperCase(), is(nomeProduto.toUpperCase()));
        assertThat(nomeDoProduto_ProdutoPage.toUpperCase(), is(nomeProduto.toUpperCase()));
    }

    @Quando("preco do produto na tela principal e na tela produto eh {string}")
    public void preco_do_produto_na_tela_principal_eh(String precoProduto) {
        assertThat(precoDoProduto_HomePage, is(precoProduto.toUpperCase()));
        assertThat(precoDoProduto_ProdutoPage, is(precoProduto.toUpperCase()));
    }

    ModalPage modalPage;

    @Quando("adiciono o produto no carrinho com tamanho {string} cor {string} e quantidade {int}")
    public void adiciono_o_produto_no_carrinho_com_tamanho_cor_e_quantidade(String tamanhoProduto, String corProduto, Integer quantidadeProduto) {
        // Selecionar Tamanho
        List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
        produtoPage.selecionarOpcaoDropDown(tamanhoProduto);
        listaOpcoes = produtoPage.obterOpcoesSelecionadas();

        // Selecionar cor
        if (!corProduto.equals("N/A"))
            produtoPage.selecionarCorPreta();

        // Alterar quantidade
        if (!quantidadeProduto.equals("N/A"))
        produtoPage.alterarQuantidade(quantidadeProduto);

        // Clicando add no carrinho
        modalPage = produtoPage.adicionarNoCarrinho();

        // Validar se aparece a mensagem de confirmação
        assertTrue(modalPage.obterMensagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));
    }


    @Entao("o produto aparece na confirmacao com nome {string} preco {string} tamanho {string} cor {string} e quantidade {int}")
    public void o_produto_aparece_na_confirmacao_com_o_nome_hummingbird_printed_t_shirt_preco_tamanho_cor_e_quantidade(String nome, String precoProduto, String tamanhoProduto, String corProduto, Integer quantidadeProduto) {
        // Validar se as informações da compra está correta ( nome, tamanho, cor e quantidade )
        String nomeModalPage = modalPage.obterNomeProduto_modalPage().toUpperCase();
        assertThat(nomeModalPage, is(nomeDoProduto_ProdutoPage.toUpperCase()));

        Double precoProdutoDoubleEncontrado = Double.parseDouble(modalPage.obterPrecoProduto().replace("$", ""));
        Double precoProdutoDoubleEsperado = Double.parseDouble(precoProduto.replace("$", ""));

        if (!tamanhoProduto.equals("N/A"))
        assertThat(modalPage.obterTamanhoProduto(), is(tamanhoProduto));

        if (!corProduto.equals("N/A"))
            assertThat(modalPage.obterCorProduto(), is(corProduto));

        assertThat(modalPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));

        // Validar subtotal do produto e converter de String para Double
        String subTotalString = modalPage.obterSubTotal();
        subTotalString = subTotalString.replace("$", "");
        Double subtotalEncontrado = Double.parseDouble(subTotalString);

        Double subtotalCalculadoEsperado = quantidadeProduto * precoProdutoDoubleEsperado;

        assertThat(subtotalEncontrado, is(subtotalCalculadoEsperado));

    }

    @After (order = 1)
    public void capturarTela(Scenario scenario){
        TakesScreenshot camera = (TakesScreenshot) driver;
        File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);

        String scenarioID =  scenario.getId().substring(scenario.getId().lastIndexOf(".feature:") + 9);

        String nomeArquivo = "resources/screenshots/" + scenario.getName()
                + "_" + scenarioID + "_" +  scenario.getStatus() + ".png";

        try {
            Files.move(capturaDeTela,new File(nomeArquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After (order = 0)
    public static void finalizar() {
        driver.quit();
    }


}


