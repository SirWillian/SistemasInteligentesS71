package sistema;

import ambiente.*;
import problema.*;
import comuns.*;
import busca.*;
import static comuns.PontosCardeais.*;
import java.util.Random;

/**
 *
 * @author tacla
 */
public class Agente implements PontosCardeais {
    /* referência ao ambiente para poder atuar no mesmo*/
    Model model;
    public Problema prob;
    public Estado estAtu; // guarda o estado atual (posição atual do agente)
    BuscaCega busca;
    int metodo_busca=0;
    int plan[];
    public int treeSize=1, ct_ja_explorados=0, ct_descartados_front=0;
    public int max_explorados=0, max_descartados=0;
    double custo;
    static int ct = -1;
           
    public Agente(Model m) {
        this.model = m;
        prob = new Problema();
        prob.criarLabirinto(9, 9);
        prob.crencaLabir.porParedeVertical(0, 1, 0);
        prob.crencaLabir.porParedeVertical(0, 0, 1);
        prob.crencaLabir.porParedeVertical(5, 8, 1);
        prob.crencaLabir.porParedeVertical(5, 5, 2);
        prob.crencaLabir.porParedeVertical(8, 8, 2);
        prob.crencaLabir.porParedeHorizontal(4, 7, 0);
        prob.crencaLabir.porParedeHorizontal(7, 7, 1);
        prob.crencaLabir.porParedeHorizontal(3, 5, 2);
        prob.crencaLabir.porParedeHorizontal(3, 5, 3);
        prob.crencaLabir.porParedeHorizontal(7, 7, 3);
        prob.crencaLabir.porParedeVertical(6, 7, 4);
        prob.crencaLabir.porParedeVertical(5, 6, 5);
        prob.crencaLabir.porParedeVertical(5, 7, 7);
        
        // Estado inicial, objetivo e atual
        prob.defEstIni(8, 0);
        prob.defEstObj(2, 8);
        this.estAtu = prob.estIni;
        this.custo = 0;
    }
    
    /**Escolhe qual ação (UMA E SOMENTE UMA) será executada em um ciclo de raciocínio
     * @return 1 enquanto o plano não acabar; -1 quando acabar
     */
    public int deliberar() {
        ct++;
        if(ct==0)
        {
            Random rand = new Random();
            metodo_busca=rand.nextInt(3); //rand()%3
            switch(metodo_busca){
                case 0:
                    busca = new BuscaAStar(this, 0);
                    break;
                case 1:
                    busca = new BuscaAStar(this, 1);
                    break;
                case 2:
                    busca = new BuscaCustoUniforme(this);
                    break;
            }
            plan=this.busca.gerarSolucao();
        }
        int ap[];
        ap = prob.acoesPossiveis(estAtu);
        // nao atingiu objetivo e ha acoesPossiveis a serem executadas no plano
        if (!prob.testeObjetivo(estAtu) && ct < plan.length) {
           System.out.println("estado atual: " + estAtu.getLin() + "," + estAtu.getCol());
           System.out.print("açoes possiveis: {");
           for (int i=0;i<ap.length;i++) {
               if (ap[i]!=-1)
                   System.out.print(acao[i]+" ");
           }

            executarIr(plan[ct]);
           
           // atualiza custo
           if (plan[ct] % 2 == 0 ) // acoes pares = N, L, S, O
               custo = custo + 1;
           else
               custo = custo + 1.5;
           
           System.out.println("}\nct = "+ ct + " de " + (plan.length-1) + " ação escolhida=" + acao[plan[ct]]);
           System.out.println("custo ate o momento: " + custo);
           System.out.println("**************************\n\n");
           
           // atualiza estado atual - sabendo que o ambiente eh deterministico
           estAtu = prob.suc(estAtu, plan[ct]);
                      
        }
        else
        {
            String str=metodo_busca==0 ? "A* com distância euclidiana" : (metodo_busca==1 ? "A* com distância de Chebyshev" : "Custo Uniforme");
            System.out.println("Método de busca: " + str);
            System.out.println("Tamanho da árvore: " + treeSize);
            System.out.println("Complexidade Temporal:");
            System.out.println("Nós descartados pois já tinham sido explorados: " + ct_ja_explorados); //não inseridos na fronteiro pois ja explorados
            System.out.println("Nós descartados0 na fronteira: " + ct_descartados_front);               //substituidos na fronteira
            System.out.println("Complexidade Espacial:");
            System.out.println("Valor máximo ct_ja_explorados/ct_descartados_front: " + max_explorados + ", " + max_descartados);
            System.out.print("Solução: ");
            for(int i=0; i<plan.length; i++)
            {
                switch(plan[i])
                {
                    case N:
                        System.out.print("N ");
                        break;
                    case NE:
                        System.out.print("NE ");
                        break;
                    case L:
                        System.out.print("E ");
                        break;
                    case SE:
                        System.out.print("SE ");
                        break;
                    case S:
                        System.out.print("S ");
                        break;
                    case SO:
                        System.out.print("SO ");
                        break;
                    case O:
                        System.out.print("O ");
                        break;
                    case NO:
                        System.out.print("NO ");
                        break;
                    
                }
            }
            System.out.println("");
            System.out.println("Custo da solução: " + custo);
            return (-1);
        }
        
        return 1;
    }
    
    /**Funciona como um driver ou um atuador: envia o comando para
     * agente físico ou simulado (no nosso caso, simulado)
     * @param direcao N NE S SE ...
     * @return 1 se ok ou -1 se falha
     */
    public int executarIr(int direcao) {
        model.ir(direcao);
        return 1; 
    }   
    
    // Sensor
        public Estado sensorPosicao() {
        int pos[];
        pos = model.lerPos();
        return new Estado(pos[0], pos[1]);
    }
}
    

