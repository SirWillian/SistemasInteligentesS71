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
    int metodo_busca=0;
    List<Integer> plan = new ArrayList<>();
    public int treeSize=1, ct_ja_explorados=0, ct_descartados_front=0;
    public int max_explorados=0, max_descartados=0;
    public double custo;
    static int ct = -1;
           
    public Agente(Model m) {
        this.model = m;
        prob = new Problema();
        prob.criarLabirinto(5, 9);
        prob.crencaLabir.porParedeVertical(3, 4, 1);
        prob.crencaLabir.porParedeHorizontal(0, 1, 1);
        prob.crencaLabir.porParedeHorizontal(3, 5, 1);
        prob.crencaLabir.porParedeHorizontal(3, 6, 2);
        prob.crencaLabir.porParedeHorizontal(3, 5, 3);
        prob.crencaLabir.porParedeHorizontal(7, 8, 0);
        
        // Estado inicial, objetivo e atual
        prob.defEstIni(4, 0);
        prob.defEstObj(2, 8);
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
        if (!prob.testeObjetivo(estAtu)) {
            int decisao=this.busca.decideAcao(ap);
            plan.add(decisao);
            executarIr(decisao);
           
           // atualiza custo
           if (decisao % 2 == 0 ) // acoes pares = N, L, S, O
               custo = custo + 1;
           else
               custo = custo + 1.5;
           
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
        
        return 1;
    }
    
    public void resetarAgente()
    {
        this.estAtu = prob.estIni;
        this.plan = new ArrayList<>();
        custo=0;
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