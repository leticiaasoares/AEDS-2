import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

//Classe ExtraiPalavra do programa 5.33 página 222 do livro do Ziviani
//a classe foi adaptada para guardar o número da linha

public class ExtraiPalavra {
    private BufferedReader arqAlf, arqTxt;
    private StringTokenizer palavras;
    private String delimitadores;
    
    private int linhaDaPalavra = 0; //guarda a linha em que a palavra está

    public ExtraiPalavra (String nomeArqAlf, String nomeArqTxt) throws Exception {
        this.arqAlf = new BufferedReader (new FileReader(nomeArqAlf)); //abre o arquivo com o delimitadores
        this.arqTxt = new BufferedReader (new FileReader(nomeArqTxt)); //abre o arquivo com o texto a ser lido

        this.delimitadores = arqAlf.readLine() /*+ "\r\n"*/;
        this.palavras = null;
    }

    public String proximaPalavra() throws Exception {
        if (palavras == null || !palavras.hasMoreTokens()) {
            linhaDaPalavra++;  //aumenta sempre que le a proxima linha
            String linha = arqTxt.readLine();
            if (linha == null) return null;
            this.palavras = new StringTokenizer(linha, this.delimitadores);
            if (!palavras.hasMoreTokens()) return "";
        }
        return this.palavras.nextToken(); //retorna as palavras
    }

    public void fecharArquivos() throws Exception { //fecha os arquivos
        this.arqAlf.close();
        this.arqTxt.close();
    }

    public int getLinhaDaPalavra(){  //retorna a linha que está sendo lida
        return this.linhaDaPalavra;
    }

}