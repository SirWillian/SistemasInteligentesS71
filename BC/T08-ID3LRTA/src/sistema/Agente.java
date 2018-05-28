package sistema;

import ambiente.*;
import problema.*;
import comuns.*;
import busca.*;
import static comuns.PontosCardeais.*;
import java.util.ArrayList;
import java.util.List;
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
    BuscaLRTA busca;
    BuscaFruta buscaFruta;
    int metodo_busca=0;
    List<Integer> plan = new ArrayList<>();
    public int treeSize=1, ct_ja_explorados=0, ct_descartados_front=0;
    public int max_explorados=0, max_descartados=0;
    public float custo;
    public boolean learning = true;
    public boolean random_eat = true;
    private float energia=3;
    public float pontuacao;
    static int ct = 0;
           
    public Agente(Model m) {
        this.model = m;
        prob = new Problema();
        prob.criarLabirinto(9, 9);
        prob.crencaLabir.porParedeHorizontal(0, 1, 0);
        prob.crencaLabir.porParedeHorizontal(4, 7, 0);
        prob.crencaLabir.porParedeHorizontal(0, 0, 1);
        prob.crencaLabir.porParedeHorizontal(3, 5, 2);
        prob.crencaLabir.porParedeHorizontal(3, 6, 3);
        prob.crencaLabir.porParedeVertical(6, 8, 1);
        prob.crencaLabir.porParedeVertical(5, 5, 2);
        prob.crencaLabir.porParedeVertical(8, 8, 2);
        prob.crencaLabir.porParedeVertical(6, 7, 4);
        prob.crencaLabir.porParedeVertical(5, 6, 5);
        prob.crencaLabir.porParedeVertical(5, 7, 7);
        
        // Estado inicial, objetivo e atual
        prob.defEstIni(8, 0);
        prob.defEstObj(2, 6);
        this.estAtu = prob.estIni;
        this.busca = new BuscaLRTA(this);
        this.custo = 0;
    }
    
    /**Escolhe qual ação (UMA E SOMENTE UMA) será executada em um ciclo de raciocínio
     * @return 1 enquanto o plano não acabar; -1 quando acabar
     */
    public int deliberar() {
        int ap[];
        ap = prob.acoesPossiveis(estAtu);
        // nao atingiu objetivo e ha acoesPossiveis a serem executadas no plano
        if(learning){
            if (!prob.testeObjetivo(estAtu)) {
                int decisao=this.busca.decideAcao(ap);
                plan.add(decisao);
                executarIr(decisao);

               // atualiza custo
               if (decisao % 2 == 0 ) // acoes pares = N, L, S, O
                   custo = custo + 1;
               else
                   custo = custo + (float)1.5;

               estAtu = prob.suc(estAtu, decisao);

            }
            else
            {
                System.out.print("Solução: ");
                for(int i=0; i<plan.size(); i++)
                {
                    System.out.print(acao[plan.get(i)]+" ");
                }
                System.out.println("");
                System.out.println("Custo da solução: " + custo);
                System.out.println("**************************\n\n");
                return (-1);
            }
        }
        else{
            if (!prob.testeObjetivo(estAtu)) {
                Fruta fruta_atual=buscaFruta.labirinto[estAtu.getLin()][estAtu.getCol()];
                if(!fruta_atual.foi_comida){
                    if(random_eat){
                        Random random = new Random();
                        if(random.nextBoolean()){
                            //Come a fruta
                            energia+=fruta_atual.getEnergia();
                            fruta_atual.foi_comida=true;
                        }
                    }
                    else{
                        int custoTotal=0;
                        for(int acao : plan)
                            custoTotal+=prob.obterCustoAcao(null, acao, null);
                        if(energia-(custoTotal-custo) < 2){
                            //Come a fruta
                            energia+=fruta_atual.getEnergia();
                            fruta_atual.foi_comida=true;
                        }
                            
                    }
                }
                if(energia - prob.obterCustoAcao(null, plan.get(ct), null) < 0){
                    this.pontuacao=-100;
                    System.out.println("Morreu de falta de energia ao andar " + ct + " passos");
                    System.out.println("**************************\n\n");
                    return(-1);
                }
                energia-=prob.obterCustoAcao(null, plan.get(ct), null);
                executarIr(plan.get(ct));
                custo+=prob.obterCustoAcao(null, plan.get(ct), null);
                estAtu = prob.suc(estAtu, plan.get(ct));
                ct++;
            }
            else{
                this.pontuacao=-energia;
                System.out.print("Terminou com pontuação ");
                System.out.printf("%.1f \n", pontuacao);
                System.out.println("**************************\n\n");
                return(-1);
            }
                
        }
        
        return 1;
    }
    
    public void resetarAgente()
    {
        this.estAtu = prob.estIni;
        this.plan = new ArrayList<>();
        custo=0;
        ct=0;
        energia=3;
        if(!learning)
            this.buscaFruta = new BuscaFruta(prob.crencaLabir);
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