/** Encontra uma solução para o problema da mochila
 * Usa o conceito de algoritmo genético
 * @author WILLIAN e Terumi
 */
package mochila;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MochilaSolver {
    private MochilaItems mochila;
    private List<Mochila> individuos;
    private List<Mochila> filhos; // Será necessário fazer a comparação com os pais para a seleção dos melhores
    public List<int[]> genFit; //Pares ordenados (geração, fitness). Colocar o melhor fitness a cada geração
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
            tmp.fitness = m.valores[i]; //Calculo do fitness
            individuos.add(tmp);
        }
        
        this.genFit=new ArrayList<>();
        this.best_fitness=0;
        this.capacidade=c;
        this.n_individuos=n;
    }
    
    public void crossover() //numero de filhos = numero de individuos
    {
        /** Faz crossover dos indivíduos (de acordo com uma probabilidade p_cross).
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
        double p_cross = 0.8;
        this.filhos = new ArrayList<>();

        /* Determinando as probabilidades de seleção dos indivíduos*/
        int total_fitness = 0;
        double[] p_selecao;
        p_selecao = new double[n_individuos];

        for(int i = 0; i < n_individuos; i++)
            total_fitness += individuos.get(i).fitness;
        //System.out.println("total fitness: " + total_fitness);

        for(int i = 0; i < n_individuos; i++)
            p_selecao[i] = individuos.get(i).fitness/(double)total_fitness;

        /*Print teste individuos*/
        /*
        System.out.print("individuos: \n");
        for(int i = 0; i < n_individuos; i++)
        {
            for (int j = 0; j < 42; j++) {
                System.out.print(individuos.get(i).itens[j]);
            }
            System.out.print(" " + p_selecao[i]);
            System.out.print("\n");
        }
        */

        /* Seleção dos indivíduos pais de acordo com a probabilidade de seleção*/
        for(int i = 0; i < n_individuos; i++)
        {
            Random rand0 = new Random();
            double r = rand0.nextDouble();
            double soma = p_selecao[0];
            int index = 0;
            //System.out.println("sorteado: " + r);
            while(soma < r)
            {
                ++index;
                soma += p_selecao[index];
            }

            filhos.add(individuos.get(index));

        }
        /*Print teste filhos*/
        /*
        System.out.print("filhos: \n");
        for(int i = 0; i < n_individuos; i++)
        {
            for (int j = 0; j < 42; j++) {
                System.out.print(filhos.get(i).itens[j]);
            }
            System.out.print("\n");
        }
        */

        /* Procedimento de crossover dois a dois de acordo com pCross*/
        Random rand1 = new Random();
        Random rand2 = new Random();
        int tmp, sorteio;

        for(int individuo = 0; individuo < n_individuos; individuo+=2)
        {
            sorteio = rand2.nextInt(10) + 1;  //entre valores de 1 a 10
            if(sorteio <= p_cross*10)
            {
                int corte = rand1.nextInt(41) + 1; //entre as posições 1 a 41
                for (int j = corte; j < mochila.n_elementos; j++)
                {
                    tmp = filhos.get(individuo).itens[j];
                    filhos.get(individuo).itens[j] = filhos.get(individuo + 1).itens[j];
                    filhos.get(individuo + 1).itens[j] = tmp;
                }
            }
        }
        /*Print teste filhos após crossover*/ // A principio deu certo :)
        /*
        System.out.print("filhos depois do cross: \n");
        for(int i = 0; i < n_individuos; i++)
        {
            for (int j = 0; j < 42; j++) {
                System.out.print(filhos.get(i).itens[j]);
            }
            System.out.print("\n");
        }
        */

    }
    
    public void mutation()
    {
        /** Muta alelos (troca de 0 pra 1 / 1 pra 0)
         * De volta, n sei se muta todo mundo ou se faz um de cada vez. Melhor todos eu acho.
         */
        double pMUT = 0.05;
        Random rand = new Random();

        for(int filho = 0; filho < n_individuos; filho++)
        {
            /*System.out.print("antes: \n");
            for (int j = 0; j < 42; j++) {

                System.out.print(filhos.get(filho).itens[j]);
            }
            System.out.print("\n");
            */
            for (int alelo = 0; alelo < mochila.n_elementos; alelo ++)
            {
                int sorteio = rand.nextInt(100);
                //System.out.println("rando: " + sorteio);
                if(sorteio <= pMUT*100) //Se o numero sorteado estiver entre 0 e 5
                {
                    System.out.print("Mutação em ");
                    System.out.print(" filho " + filho);
                    System.out.println(" alelo " + alelo);
                    if(filhos.get(filho).itens[alelo] == 1)
                        filhos.get(filho).itens[alelo] = 0;
                    else
                        filhos.get(filho).itens[alelo] = 1;
                }
            }

            /*System.out.print("depois: \n");
            for (int j = 0; j < 42; j++) {

                System.out.print(filhos.get(filho).itens[j]);
            }
            System.out.print("\n");
            */
        }
    }
    
    public void evolve()
    {
        /** Implementação do AG canônico (slide do Tacla).
         * Eu sei q no slide fala pra retornar algo desse método, mas eu achei melhor colocar isso em outro método.
         */
        for (int index = 0; index < n_individuos; index++)
            filhos.get(index).calculaFitness(mochila);

        for(int index = 0; index < n_individuos; index++)
        {
            int fitness_pai = individuos.get(index).fitness;
            int fitness_filho = filhos.get(index).fitness;

            // Armazenar os escolhidos na Lista de Individuos, pode ser em outro lugar tbm
            if(fitness_filho > fitness_pai)
                for (int i = 0; i < mochila.n_elementos; i++)
                    individuos.get(index).itens[i] = filhos.get(index).itens[i];

        }

    }
    
    public Mochila getBestMochila()
    {
        /** Retorna a melhor mochila.
         * Procura a mochila com best_fitness e retorna.
         */
        Mochila best_mochila;
        best_mochila = new Mochila(42);
        int best = individuos.get(0).fitness; // Não sabia se queria atualizar a best_mochila através desta func, n entendi direito
        for(int index = 1; index < n_individuos; index++)
        {
            if(individuos.get(index).fitness > best)
            {
                best = individuos.get(index).fitness;
                best_mochila = individuos.get(index);
            }
        }
        return best_mochila;
    }
    
    private void penalizaIndividuo(Mochila individuo)
    {
        /** Diminui o fitness do indivíduo se passar da capacidade máxima.
         * Tem q pensar em alguma forma de penalizar.
         * Meio q qualquer coisa serve mas talvez influencie em quão rapido a gente converge numa solução.
         */
        //Talvez em penalizar retirando 1 no fitness para cada 1 que ultrapasse o limite máximo(?)
    }
    
    private void reparaIndividuo(Mochila individuo)
    {
        /** Repara o indivíduo que passou da capacidade para ser factível.
         * De volta, tem q pensar em que item tirar da mochila.
         */
    }
}
