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
    
    public Mochila(Mochila m)
    {
        this.itens = new int[m.itens.length];
        for(int i=0; i<m.itens.length; i++)
            this.itens[i]=m.itens[i];
        this.valor=m.valor;
        this.peso=m.peso;
        this.fitness=m.fitness;
        this.n_itens=m.n_itens;
    }

    public void calculaFitness(MochilaItems m)
    {
        this.valor=0;
        this.peso=0;
        this.n_itens=0;
        
        for(int index = 0; index < m.n_elementos; index++)
        {
            this.valor += this.itens[index] * m.valores[index];
            this.peso += this.itens[index] * m.pesos[index];
            if(this.itens[index]==1)
                this.n_itens++;
        }

        this.fitness = valor; //Enquanto não há penalização
    }
    public void printMochila(MochilaItems m)
    {
        System.out.println("Mochila  peso  valor");
        System.out.println("---------------------");
        for(int i=0; i<itens.length; i++)
            if(itens[i]==1)
                System.out.println("item["+i+"]   " + m.pesos[i] + "     " + m.valores[i]);
        System.out.println("---------------------");
        System.out.println("Mochila com " + n_itens + "ITENS");
        System.out.println("Mochila com " + peso + "KG");
        System.out.println("Mochila com " + valor + "VALOR");
        System.out.println("---------------------");
    }
}
