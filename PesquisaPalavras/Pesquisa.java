/*
 *
 * Dupla:
 * Letícia de Oliveira Soares
 * Matheus Monteiro Huebra Perdigão
 * 
 */

public class Pesquisa {
    public static void main(String[] args) throws Exception {
        
        ExtraiPalavra extraiTexto01 = null; //vai extrair palavras do texto de exemplo 1
        ExtraiPalavra extraiTexto02 = null; //vai extrair palavras do texto de exemplo 2
        String str;

        ArvorePatricia texto01 = new ArvorePatricia(128); //guarda palavras do texto 1
        ArvorePatricia texto02 = new ArvorePatricia(128); //guarda palavras do texto 2
        
        try {
            extraiTexto01 = new ExtraiPalavra("auxiliar.txt", "exemplo1.txt");
            extraiTexto02 = new ExtraiPalavra("auxiliar.txt", "exemplo2.txt");
        } catch (Exception e) { 
            System.out.println(e.getMessage());
        }

        int linha = 1;
        int linhaAux = linha;
        int coluna = 0;
        
        while((str = extraiTexto01.proximaPalavra()) != null){ //str vai guuardas as palavras lidas até o texto acabar
            linha = extraiTexto01.getLinhaDaPalavra(); //encontra a linha em que a palavra aparece
            if(linha == linhaAux){ 
                coluna++; //enquanto a linha for a mesma, a coluna aumenta (cada palavra é considerada uma coluna diferente)
            }
            else{ //se a linha mudar, a coluna volta para 1
                linhaAux = linha; //atualiza linhaAux
                coluna = 1;
            }
            if(str.length()>16){ //confere se a palavra tem mais do que 16 caracteres 
                String strAux = null;
                for(int i=0 ; i<16 ; i++){ 
                    strAux = strAux + str.charAt(i); //se tiver mais do que 16, considera apenas os 16 primeiros
                }
                str = strAux;
            }
            else{
                while(str.length()<16){ //se a palavra tiver menos do que 16 caracteres completa com brancos, no caso foi usado *
                    str = str + "*";
                }
            }
            texto01.insereChave(str, linha, coluna); //insere na arvore
        }

        System.out.println("Computacao:");
        texto01.pesqisaChave("Computacao******"); //observação: o algoritmo é case-sensitive, então Computacao é uma chave diferente de computacao
                                               //portanto a pesquisa por essas chaves dá resultados diferentes
        //pesquisa das palavras:
        System.out.println("Pesquisa das palavras no texto 1:");
        System.out.println("trabalho:");
        texto01.pesqisaChave("trabalho********");
        System.out.println("computacao");
        texto01.pesqisaChave("computacao******");
        System.out.println("governo:");
        texto01.pesqisaChave("governo*********");
        System.out.println("educacao:");
        texto01.pesqisaChave("educacao********");
        System.out.println("tecnologia:");
        texto01.pesqisaChave("tecnologia******");
        System.out.println("formacao:");
        texto01.pesqisaChave("formacao********");
        System.out.println("desenvolvimento:");
        texto01.pesqisaChave("desenvolvimento*");
        System.out.println("que:");
        texto01.pesqisaChave("que*************");
        System.out.println("informatica:");
        texto01.pesqisaChave("informatica*****");
        System.out.println("em:");
        texto01.pesqisaChave("em**************");
        System.out.println("crise:");
        texto01.pesqisaChave("crise***********");



        //a mesma lógia do código acima é usada abaixo
        //mas agora é usado o texto do exemplo 2

        linha = 1;
        linhaAux = linha;
        coluna = 0;
        
        while((str = extraiTexto02.proximaPalavra()) != null){ 
            linha = extraiTexto02.getLinhaDaPalavra(); 
            if(linha == linhaAux){ 
                coluna++; 
            }
            else{ 
                linhaAux = linha;
                coluna = 1;
            }
            if(str.length()>16){ 
                String strAux = null;
                for(int i=0 ; i<16 ; i++){ 
                    strAux = strAux + str.charAt(i); 
                }
                str = strAux;
            }
            else{
                while(str.length()<16){ 
                    str = str + "*";
                }
            }
            texto02.insereChave(str, linha, coluna); 
        }

        //pesquisa das palavras:
        System.out.println("Pesquisa das palavras no texto 2:");
        System.out.println("sociedade:");
        texto02.pesqisaChave("sociedade*******");
        System.out.println("software:");
        texto02.pesqisaChave("software********");
        System.out.println("ideia:");
        texto02.pesqisaChave("ideia***********");
        System.out.println("pessoa:");
        texto02.pesqisaChave("pessoa**********");
        System.out.println("Informatica:");
        texto02.pesqisaChave("Informatica*****");
        System.out.println("etica:");
        texto02.pesqisaChave("etica***********");
        System.out.println("muito:");
        texto02.pesqisaChave("muito***********");
        System.out.println("ciencia:");
        texto02.pesqisaChave("ciencia*********");
        System.out.println("computacao:");
        texto02.pesqisaChave("computacao******");
        System.out.println("que:");
        texto02.pesqisaChave("que*************");
        System.out.println("area:");
        texto02.pesqisaChave("area************");
        System.out.println("moral:");
        texto02.pesqisaChave("moral***********");

    }
}