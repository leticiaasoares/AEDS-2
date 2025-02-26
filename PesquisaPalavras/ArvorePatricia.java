import java.util.ArrayList;

//foram usados os códigos 5.13 a 5.18 do livro do ziviani (capítulo 5) 
//algumas modificações foram feitas para realizar a inserção e busca de palavras em forma de strings
//e tambem para guardar as posições do texto em que a palavra foi encontrada

public class ArvorePatricia {

    private class Posicao{ //classe com a linha e coluna da palavra no texto
        int linha;
        int coluna;

        public Posicao(int linha, int coluna){
            this.linha = linha;
            this.coluna = coluna;
        }
    }

    private static abstract class PatNo {}

    private static class PatNoInt extends PatNo { //classe de um nó interno
        int index; //guarda o bit que é diferente
        PatNo esq, dir; //guarda os nós a esquerda e a direita
    }

    private static class PatNoExt extends PatNo { //classe de um nó externo
        String chave; //a chave é a palavra 
        ArrayList<Posicao> linhas = new ArrayList<Posicao>(); //lista com todas as posições em que a palavra aparece no texto
    }

    private PatNo raiz; //nó raiz
    private int nbitsChave; //número total de bits, nesse caso será 128

    public ArvorePatricia (int nbitsChave) {
        this.raiz = null;
        this.nbitsChave = nbitsChave;
    }

    private boolean eExterno (PatNo p) { //confere se o nó é externo
        Class classe = p.getClass();
        return classe.getName().equals(PatNoExt.class.getName());
    }

    private PatNo criaNoInt (int i, PatNo esq, PatNo dir) { //cria um nó interno
        PatNoInt p = new PatNoInt();
        p.index = i;
        p.esq = esq;
        p.dir = dir;
        return p;
    }

    private PatNo criaNoExt (String s, int linha, int coluna) { //cria um nó externo
        PatNoExt p = new PatNoExt();
        p.chave = s; 
        Posicao posicaoNoTexto = new Posicao(linha, coluna);
        p.linhas.add(posicaoNoTexto);
        return p;
    }

    private int bit(int i, String s){ //retorna o bit na posição procurada
        if(i==0) return 0;

        int indiceDaLetra = i%8; //indiceDaLetra é usado para encontrar um dos bits da letra, baseado no codigo da letra na tabela ASCII
                                 //varia de 0 a 7
                                 //usa-se o resto da divisão por 8 pois a variavel i vai variar de 1 até 128
                                 //sendo 1 o primeiro bit da primeira letra e 128 o ultimo bit da ultima letra
                                 //portanto usa-se o resto da divisão para percorrer apenas entre os bits do codigo ASCII da letra

        int indiceDaPalavra = (i-1)/8; //indiceDaPalavra é usado para determinar qual letra da palavra será usada
                                       //varia de 0 até 15
                                       //portanto é preciso subtrair 1 de i, pois i varia de 1 a 128
                                       //e divide-se por 8 para encontrar a posição da letra

        int temp = (int)s.charAt(indiceDaPalavra);  //encontra o i-ésimo bit da letrra a partir da esquerda
        for (int j = 1; j <= 8 - indiceDaLetra ; j++) {
                temp = temp/2;
            }
        return (temp%2); //retorna o i-ésimo bit
    }

    
    public void insereChave(String s, int linha, int coluna){ //chama o metodo privado insere
        this.raiz = this.insere(s, this.raiz, linha, coluna);
    }

    private PatNo insere(String s, PatNo t, int linha, int coluna){ //insere uma palavra na árvore
        if(t==null) return this.criaNoExt(s, linha, coluna); //se a raiz ainda não existir, ela é criada e retornada
        else {
            PatNo p = t;
            while(!this.eExterno(p)){ //fica no while até chegar em um nó externo
                PatNoInt aux = (PatNoInt) p;
                if(this.bit(aux.index, s)==1) p = aux.dir; //verifica os bits da palavra, para determinar se vai para o nó da esquerda ou da direita
                else p = aux.esq;
            }
            PatNoExt aux = (PatNoExt)p;
            int i = 1;
            while((i<=this.nbitsChave) && (this.bit(i, s)==this.bit(i, aux.chave))){  //tenta encontrar um bit diferente entre a palavra a ser inserida e um nó externo já existente
                i++; //guarda a posiçaão do bit diferente
            }
            if(i>this.nbitsChave){ //significa que a chave já existe
                //System.out.println("Chave já está na arvore");
                aux.linhas.add(new Posicao(linha, coluna)); //adiciona as outras posições no texto em que a palavra foi encontrada
                return t;
            }
            else return this.insereEntre(s, t, i, linha, coluna); //chama metodo para inserir na arvore
        }
    }

    private PatNo insereEntre(String s, PatNo t, int i, int linha, int coluna){
        PatNoInt aux = null;
        if (!this.eExterno(t)) aux = (PatNoInt) t; //confere sese não é externo
        if (this.eExterno(t) || (i < aux.index)) { //confere se é externo e os bits de diferenciam
            PatNo p = this.criaNoExt(s, linha, coluna); //cria um nó com a chave e a posição no texto
            if (this.bit(i, s) == 1) return this.criaNoInt(i, t, p); 
            else return this.criaNoInt(i, p, t);
        } else {
            if (this.bit(aux.index, s) == 1)
                aux.dir = this.insereEntre(s, aux.dir, i, linha, coluna); //chama recursivamente
            else aux.esq = this.insereEntre(s, aux.esq, i, linha, coluna);
            return aux;
        }
    }


    public void pesqisaChave(String s){ //chama metodo privado pesquisa
        this.pesquisa(s, this.raiz);
    }

    private void pesquisa(String s, PatNo t){
        if(this.eExterno(t)){  //confere se o nó é externo
            PatNoExt aux = (PatNoExt) t;
            if(aux.chave.equals(s)){ //confere se foi encontrado
                 System.out.println("Elemento encontrado em: ");
                 for(Posicao p: aux.linhas){
                    System.out.println("linha: " + p.linha + " " + "coluna: " + p.coluna);
                 }
                 System.out.println("\n");
            }
            else System.out.println("Elemento não encontrado");
        }
        else{
            PatNoInt aux = (PatNoInt) t; 
            if(this.bit(aux.index, s)==0) pesquisa(s, aux.esq); //chama recursivamente, pesquisando na arvore até chegar em um nó externo
            else pesquisa(s, aux.dir); //chama recursivamente
        }
    }

}