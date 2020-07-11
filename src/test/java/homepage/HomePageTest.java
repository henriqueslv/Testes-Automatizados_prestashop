package homepage;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.*;
import util.Funcoes;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTest extends BaseTest {
    protected ProdutoPage produtoPage;
    protected LoginPage loginPage;
    protected ModalPage modalPage;
    protected CarrinhoPage carrinhoPage;
    protected CheckoutPage checkoutPage;
    protected PedidoPage pedidoPage;

    String nomeDoProduto_ProdutoPage;
    String precoDoProduto_ProdutoPage;
    String nomeDoProduto_HomePage;
    String precoDoProduto_HomePage;
    String nomeModalPage;


    // Validações Básicas
    @Test
    public void testContarProdutos_oitoProdutos() {
        carregarPaginaInicial();
        assertThat(homePage.contarProdutos(), is(8));
    }

    @Test
    public void testQuantidadeDeItensNoCarrinho_zeroItens() {
        assertThat(homePage.totalDeItensNoCarrinho(), is(0));
    }

    // Fluxo Padrão


    //Validar detalhes do produto da pagina incial e quando abrir ele
    @Test
    public void testValidarDetalhesDoProduto_descricaoEValorIguais() {
        int indice = 0;
        nomeDoProduto_HomePage = homePage.obterNomeDoProduto(indice).toUpperCase();
        precoDoProduto_HomePage = homePage.obterPrecoDoProduto(indice);

        produtoPage = homePage.clicarProduto(indice);

        nomeDoProduto_ProdutoPage = produtoPage.obterNomeProduto_produtoPage().toUpperCase();
        precoDoProduto_ProdutoPage = produtoPage.obterPrecoProduto_produtoPage().toUpperCase();

        assertThat(nomeDoProduto_HomePage, is(nomeDoProduto_ProdutoPage));
        assertThat(precoDoProduto_HomePage, is(precoDoProduto_ProdutoPage));
    }

    @Test
    public void testLogarUsuario_usuarioLogado() {
        // Clicar no botão SignIn
        loginPage = homePage.clicarBotaoSignIn();

        // Na página login inserir os dados e logar
        loginPage.inserirEmail("henrique@teste.com");
        loginPage.inserirSenha("henrique");
        loginPage.fazerLogin();

        // Validar se o usuário está logado
        assertThat(homePage.usuarioLogado("Henrique Silva"), is(true));

        carregarPaginaInicial();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/massaTeste_Login.csv", numLinesToSkip = 1, delimiter = ';')
    public void testLogin_usuarioLogadoComDadosValidos(String nomeTeste, String email, String password,
                                                       String nomeUsuario, String resultado){
        // Clicar no botão SignIn
        loginPage = homePage.clicarBotaoSignIn();

        // Na página login inserir os dados e logar
        loginPage.inserirEmail(email);
        loginPage.inserirSenha(password);
        loginPage.fazerLogin();

        boolean esperadoLoginOK;
        if (resultado.equals("positivo"))
            esperadoLoginOK = true;
        else
            esperadoLoginOK = false;

        // Validar se o usuário está logado
        assertThat(homePage.usuarioLogado(nomeUsuario), is(esperadoLoginOK));

        capturarTela(nomeTeste, resultado);

        if (esperadoLoginOK)
            homePage.clicarBotaoSignOut();

        carregarPaginaInicial();
    }

    // Incluir Produto no Carrinho
    @Test
    public void testIncluirProdutoNoCarrinho_produtoIncluidoComSucesso() {

        String tamanhoProduto = "M";
        String corProduto = "Black";
        int quantidadeProduto = 2;

        // verificiar se está logado, se não estiver, logar.
        if (!homePage.usuarioLogado("Henrique Silva")) {
            testLogarUsuario_usuarioLogado();
        }
        // Validar as informações do produto selecionado
        testValidarDetalhesDoProduto_descricaoEValorIguais();

        // Selecionar Tamanho
        List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
        produtoPage.selecionarOpcaoDropDown(tamanhoProduto);
        listaOpcoes = produtoPage.obterOpcoesSelecionadas();

        // Selecionar cor
        if (!corProduto.equals("N/A"))
            produtoPage.selecionarCorPreta();

        // Alterar quantidade
        produtoPage.alterarQuantidade(quantidadeProduto);

        // Clicando add no carrinho
        modalPage = produtoPage.adicionarNoCarrinho();

        // Validar se aparece a mensagem de confirmação
        assertTrue(modalPage.obterMensagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));

        // Validar se as informações da compra está correta ( nome, tamanho, cor e quantidade )
        nomeModalPage = modalPage.obterNomeProduto_modalPage().toUpperCase();
        assertThat(nomeModalPage, is(nomeDoProduto_ProdutoPage));

        assertThat(modalPage.obterTamanhoProduto(), is(tamanhoProduto));
        assertThat(modalPage.obterCorProduto(), is(corProduto));
        assertThat(modalPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));

        // Validar preco do produto e converter de String para Double
        String precoProdutoString = modalPage.obterPrecoProduto();
        precoProdutoString = precoProdutoString.replace("$", "");
        Double precoProduto = Double.parseDouble(precoProdutoString);

        // Validar subtotal do produto e converter de String para Double
        String subTotalString = modalPage.obterSubTotal();
        subTotalString = subTotalString.replace("$", "");
        Double subtotal = Double.parseDouble(subTotalString);

        Double valorSubTotal = quantidadeProduto * precoProduto;

        assertThat(valorSubTotal, is(subtotal));

    }


    //Valores Esperados
    String esperado_nomeProduto = "Hummingbird printed t-shirt";
    Double esperado_precoProduto = 19.12;
    String esperado_tamanhoProduto = "M";
    String esperado_corProduto = "Black";
    int esperado_quantidadeProduto = 2;
    Double esperado_subTotalProduto = esperado_precoProduto * esperado_quantidadeProduto;

    int esperado_numeroItemsTotal = esperado_quantidadeProduto;
    Double esperado_subTotalTotal = esperado_subTotalProduto;
    Double esperado_shippingTotal = 7.00;
    Double esperado_TotalTotal = esperado_subTotalTotal + esperado_shippingTotal;
    String esperado_nomeCliente = "Henrique Silva";
    String esperado_emailConfirmacao = "henrique@teste.com";

    @Test
    public void testIrParaCarrinho_confirmarInformacoes() {
        // Rodar o test para incluir produtos
        testIncluirProdutoNoCarrinho_produtoIncluidoComSucesso();

        // clicar no botao checkout
        carrinhoPage = modalPage.clicarCheckout();

        // Validar informações do carrinho
        assertThat(carrinhoPage.obterNomeProduto(), is(esperado_nomeProduto));
        assertThat(Funcoes.replace_removeCifrao(carrinhoPage.obterPrecoProduto()), is(esperado_precoProduto));
        assertThat(carrinhoPage.obterTamanhoProduto(), is(esperado_tamanhoProduto));
        assertThat(carrinhoPage.obterCorProduto(), is(esperado_corProduto));
        assertThat(Integer.parseInt(carrinhoPage.obterQuantidadeProduto()), is(esperado_quantidadeProduto));
        assertThat(Funcoes.replace_removeCifrao(carrinhoPage.obterSubtotalProduto()), is(esperado_subTotalProduto));
        assertThat(Funcoes.replace_removeNomeItems(carrinhoPage.obterNumeroDeItems()), is(esperado_numeroItemsTotal));
        assertThat(Funcoes.replace_removeCifrao(carrinhoPage.obterSubtotal_total()), is(esperado_subTotalTotal));
        assertThat(Funcoes.replace_removeCifrao(carrinhoPage.obterShipping_total()), is(esperado_shippingTotal));
        assertThat(Funcoes.replace_removeCifrao(carrinhoPage.obterTotal_total()), is(esperado_TotalTotal));

    }

    @Test
    public void testIrParaCheckout_freteMeioDePagamentoEnderecoListados() {
        // Rodar o test para incluir produtos e validar informações no carrinho
        testIrParaCarrinho_confirmarInformacoes();

        // clicar no botao Proceed to Checkout
        checkoutPage = carrinhoPage.clicarProceedToCheckout();

        // Validar informações
        assertThat(Funcoes.replace_removeCifrao(checkoutPage.obter_totalTaxTotal()), is(esperado_TotalTotal));
        // Selecionar apenas o nome por isso usar o 'startsWith'
        assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));
        checkoutPage.clicarBotaoContinue();
        // Validar o valor do shipping
        String encontrado_shippingValor = checkoutPage.obter_shipping();
        encontrado_shippingValor = Funcoes.replace_removeTexto(encontrado_shippingValor, " tax excl.");
        Double encontrado_shippingValor_Double = Funcoes.replace_removeCifrao(encontrado_shippingValor);

        assertThat(encontrado_shippingValor_Double, is(esperado_shippingTotal));

        checkoutPage.clicarBotaoContinueShipping();
        checkoutPage.clicarPayByCheck();
        checkoutPage.clicarAceitarTermos();

    }

    @Test
    public void testFinalizarPedido_pedidoFinalizado() {
        // Checkout concluido
        testIrParaCheckout_freteMeioDePagamentoEnderecoListados();
        // Clicar para finalizar pedido e gerar OS
        pedidoPage = checkoutPage.clicarGerarOrder();
        // Validar mensagem de confirmação
        assertTrue(pedidoPage.retornar_pedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));
        // Validar email
        assertThat(pedidoPage.retornar_emailConfirmacao(), is(esperado_emailConfirmacao));
        // Validar subtotal
        assertThat(pedidoPage.retornar_subtotalConfirmacao(), is(esperado_subTotalTotal));
        // Validar Total
        assertThat(pedidoPage.retornar_totalConfirmacao(), is(esperado_TotalTotal));

    }


}
