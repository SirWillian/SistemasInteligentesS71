/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subidaencosta;

/**Tabuleiro de 4 x 4: o objetivo é dispor os números de 1 a 8 nas
 * posicoes indicadas sem que antecessores/sucessores tenham posicoes adjacentes
 * nas linhas, colunas e diagonal. As posicoes com X nao sao utilizadas.
 * 
 * X 1 2 X
 * 3 4 5 6 
 * X 7 8 X

 * @author tacla 
 */
public class Tabuleiro {
    /** representa o número de peças no tabuleiro */
    protected static final int NUM_PEDRAS = 8;
    Sorteio sorteador = new Sorteio();
    /** controle de pedras já colocadas no tabuleiro durante a inicialização */
    public boolean[] pedraUsada = new boolean[NUM_PEDRAS]; 
    /** posição das pedras no tabuleiro; zero representa posição não utilizada*/
    public int[][] pedra = {{0, 2, 5, 0}, {1, 3, 8, 6}, {0, 7, 4, 0}}; 
    /** armazena o valor da fun objetivo (avaliação) para a posicao atual das pedra */
    public int vlrObj; 
    
    /** construtora: inicializa vetor de controle de pedras já colocadas; 
     *  inicialmente nenhuma pedra foi colocada.
     */
    public Tabuleiro() {
        // inicializa controle de pedras jah colocadas - inicialmente nenhuma
        for (int i = 0; i < pedraUsada.length; i++) {
            pedraUsada[i] = false;
        }
    }
    
    /** Coloca pedras de 1 a NUM_PEDRAS de forma aleatória no tabuleiro */
    public void inicializarTabuleiroAleatoriamente() {
        posicionarPedrasAleatoriamente();
        avaliarSolucao();
    }

    /**Geração de vizinhos: cria 1 (um) estado sucessor pela 
     * permutacao de duas pedras sorteadas aleatoriamente.
     * 
     * @param atual tabuleiro atual que serve de base para gerar um vizinho
     */
    public void gerarVizinho(Tabuleiro atual) {
        //System.out.println("gerarVizinho: THIS: " + this.toString() + " tab: " + atual.toString());
        // sorteia duas posicoes (a e b) que serao trocadas
        int a = sorteador.sortearItem(0, pedra.length - 1);
        int b = sorteador.sortearItem(0, pedra[0].length - 1);
        
        // enquanto 
        while (pedra[a][b] == 0) {
            a = sorteador.sortearItem(0, pedra.length - 1);
            b = sorteador.sortearItem(0, pedra[0].length - 1);
        }
        int c = sorteador.sortearItem(0, pedra.length - 1);
        int d = sorteador.sortearItem(0, pedra[0].length - 1);
        while (pedra[c][d] == 0) {
            c = sorteador.sortearItem(0, pedra.length - 1);
            d = sorteador.sortearItem(0, pedra[0].length - 1);
        }
        // copia array de controle de pedras colocadas soh para manter coerencia
        for (int i = 0; i < atual.pedraUsada.length; i++) {
            this.pedraUsada = atual.pedraUsada;
        }

        // copia posicao das pedras do estado atual para o sucessor
        for (int i = 0; i < atual.pedra.length; i++) {
            for (int j = 0; j < atual.pedra[0].length; j++) {
                this.pedra[i][j] = atual.pedra[i][j];
            }
        }

        // troca posicao das pedras sorteadas para gerar estado sucessor
        //System.out.println("GERA SUCESSOR: troca peça " + pedra[a][b] + " com " + pedra[c][d]);
        this.pedra[a][b] = atual.pedra[c][d];
        this.pedra[c][d] = atual.pedra[a][b];

        //System.out.println("GERA SUCESSOR: estado atual posicao das pedras");
        /*for (int i = 0; i < atual.pedra.length; i++) {
            for (int j = 0; j < atual.pedra[0].length; j++) {

                System.out.printf("%d ", atual.pedra[i][j]);
            }
        }
        */
        //System.out.println("");
        avaliarSolucao();
    }

    /**
     * Posiciona pedras aleatoriamente no tabuleiro sem repeticao de numeros
     */
    private void posicionarPedrasAleatoriamente() {
        for (int i = 0; i < pedra.length; i++) {

            for (int j = 0; j < pedra[i].length; j++) {
                if (pedra[i][j] > 0) {
                    int p = sorteador.sortearItem(1, 8);
                    while (pedraUsada[p - 1]) {
                        p = sorteador.sortearItem(1, 8);
                    }
                    pedra[i][j] = p;
                    pedraUsada[p - 1] = true;
                }
            }
        }
    }
    /** Calcula o valor da heuristica para o estado atual do tabuleiro.
     *
     */
    private void avaliarSolucao() {
       /* !!!! TO DO  !!!!!
        this.vlrObj = <valorar o estado atual do tabuleiro analisando a posição
        das pedras que estão armazenadas em pedra[i][j]
        */
      for(int i = 0; i < 3; i++){
          for(int j = 0; j < 4; j++){

              //Caso de ter uma parede nesta posição
              if(pedra[i][j] == 0)
                  break;

              /*Analise da linha*/
              if(i != 0)
                  if(pedra[i][j] == pedra[i-1][j] + 1)this.vlrObj++;

              if(i != 2)
                  if(pedra[i][j] == pedra[i+1][j] + 1)this.vlrObj++;

              /*Analise da coluna*/
              if(j != 0)
                  if(pedra[i][j] == pedra[i][j-1] + 1)this.vlrObj++;

              if(j != 3)
                  if(pedra[i][j] == pedra[i][j+1] + 1)this.vlrObj++;

              /*Analise das diagonais*/
              if(i != 0){
                  if(j != 0){
                      if(pedra[i][j] == pedra[i-1][j-1] + 1)this.vlrObj++;
                  }
                  else if(j != 3)
                      if(pedra[i][j] == pedra[i-1][j+1] + 1)this.vlrObj++;

              }
              if(i != 2){
                  if(j != 0){
                      if(pedra[i][j] == pedra[i+1][j-1] + 1)this.vlrObj++;
                  }
                  else if(j != 3)
                      if(pedra[i][j] == pedra[i+1][j+1] + 1)this.vlrObj++;

              }
          }
      }
    }

    /** Imprime o tabuleiro no formato comma separated value (CSV) para ser
     * gravado em arquivo em disco.
     * @return String o tabuleiro no formato CSV na ordem: primeira,
     * segunda e terceira linha (inclusive com as casas não ocupadas)
     */
    public String imprimirTabuleiroCSV() {
        String strCSV;
        strCSV = String.valueOf(vlrObj);
        for (int i = 0; i < pedra.length; i++) {
            for (int j = 0; j < pedra[0].length; j++) {
                strCSV = strCSV.concat("," + String.valueOf(pedra[i][j]));
            }
        }
        return strCSV;
    }
    /** Imprime o tabuleiro na saída padrão. */
    public void imprimirTabuleiro() {
        System.out.printf("TABULEIRO  (val=%d) ref: %s\n", vlrObj, this.toString());
        System.out.printf("-------------------\n");
        for (int i = 0; i < pedra.length; i++) {
            for (int j = 0; j < pedra[0].length; j++) {
                System.out.printf("%1d ", pedra[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.printf("---------------------\n\n");
    }
}
