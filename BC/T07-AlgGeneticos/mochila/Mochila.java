/** Mochila contendo itens
 * Indivíduo do AG resolvendo o problema da mochila
 * @author WILLIAN e Terumi
 */
package mochila;

public class Mochila {
    public int[] itens; //Lista de presença dos itens (0 - ausente, 1 - presente)
    public int valor; //Valor total dos conteúdos da mochila
    public int peso;
    public int fitness;
    public int n_itens;
    
    public Mochila(int tamanho)
    {
        this.itens=new int[tamanho]; //Quantidade de itens considerados no MochilaItems
        this.valor=0;
        this.peso=0;
        this.fitness=0;
        this.n_itens=0;
    }

    public void calculaFitness(MochilaItems m)
    {
        for(int index = 0; index < n_itens; index++)
        {
            this.fitness = this.itens[index] * m.valores[index];
            this.peso = this.itens[index] * m.pesos[index];
        }

        this.valor = fitness; //Enquanto não há penalização

    }

}
