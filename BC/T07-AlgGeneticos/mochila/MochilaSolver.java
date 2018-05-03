/** Encontra uma solução para o problema da mochila
 * Usa o conceito de algoritmo genético
 * @author WILLIAN
 */
package mochila;

import java.util.ArrayList;
import java.util.List;

public class MochilaSolver {
    private MochilaItems mochila;
    private List<Mochila> individuos;
    private List<int[]> genFit; //Pares ordenados (geração, fitness). Colocar o melhor fitness a cada geração
    private int best_fitness; //Pra achar a melhor mochila mais rápido
    private int capacidade;
    private int n_individuos;
    
    public MochilaSolver(MochilaItems m, int c, int n)
    {
        this.mochila=m;
        
        this.individuos=new ArrayList<>();
        Mochila tmp;
        for(int i=0; i<n; i++){
            tmp=new Mochila(m.n_elementos);
            tmp.itens[i]=1; //Cada individuo começa com um item diferente
            individuos.add(tmp);
        }
        
        this.genFit=new ArrayList<>();
        this.best_fitness=0;
        this.capacidade=c;
        this.n_individuos=n;
    }
    
    private void crossover()
    {
        /** Faz crossover dos indivíduos.
        * Exemplo:
        *   Antes:
        *       aaaaaaaaaaaaaa
        *       bbbbbbbbbbbbbb
        *   Depois:
        *       aaaaaaaaabbbbb
        *       bbbbbbbbbaaaaa
        * 
        *  N sei se esse método deveria fazer o cruzamento de todos os indivíduos
        *  ou se deveria receber dois pra cruzar de cada vez. Quem for implementar decide
        */
    }
    
    private void mutation()
    {
        /** Muta alelos (troca de 0 pra 1 / 1 pra 0)
         * De volta, n sei se muta todo mundo ou se faz um de cada vez. Melhor todos eu acho.
         */
    }
    
    public void evolve()
    {
        /** Implementação do AG canônico (slide do Tacla).
         * Eu sei q no slide fala pra retornar algo desse método, mas eu achei melhor colocar isso em outro método.
         */
    }
    
    public Mochila getBestMochila()
    {
        /** Retorna a melhor mochila.
         * Procura a mochila com best_fitness e retorna.
         */
        return new Mochila(42);
    }
    
    private void penalizaIndividuo(Mochila individuo)
    {
        /** Diminui o fitness do indivíduo se passar da capacidade máxima.
         * Tem q pensar em alguma forma de penalizar.
         * Meio q qualquer coisa serve mas talvez influencie em quão rapido a gente converge numa solução.
         */
    }
    
    private void reparaIndividuo(Mochila individuo)
    {
        /** Repara o indivíduo que passou da capacidade para ser factível.
         * De volta, tem q pensar em que item tirar da mochila.
         */
    }
}
