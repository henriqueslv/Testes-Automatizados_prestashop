package util;

public class Funcoes {

    public static Double replace_removeCifrao(String texto){
        texto = texto.replace("$","" );
        return Double.parseDouble(texto);
    }

    public static int replace_removeNomeItems(String texto){
        texto = texto.replace(" items","");
        return Integer.parseInt(texto);
    }

    public static String replace_removeTexto(String texto, String textoParaRemover){
        texto = texto.replace(textoParaRemover, "");
        return texto;
    }


}
