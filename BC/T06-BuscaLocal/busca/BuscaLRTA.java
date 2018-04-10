/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WILLIAN
 */
package busca;
import java.util.Random;
import problema.Estado;
import sistema.Agente;

public class BuscaLRTA {
    private Agente agente;
    private float[][] heuristica;
    
    public BuscaLRTA(Agente agnt)
    {
        this.agente=agnt;
        int hLin=this.agente.prob.crencaLabir.getMaxLin();
        int hCol=this.agente.prob.crencaLabir.getMaxCol();
        this.heuristica=new float [hLin][hCol];
        
        //Inicia heuristica com distância euclidiana
        for(int i=0; i<hLin; i++){
            for(int j=0; j<hCol; j++)
            {
                int distanciaX = Math.abs(this.agente.prob.estObj.getCol()-j);
                int distanciaY = Math.abs(this.agente.prob.estObj.getLin()-i);
                heuristica[i][j]=(float)Math.sqrt(distanciaX*distanciaX + distanciaY*distanciaY);
                //heuristica[i][j]=0;
            }
        }
    }
    
    public int decideAcao(int[] ap){
        float menorCusto=1000000; //"Garante" que o primeiro custo encontrado será menor que isso
        int acao=0;
        for(int i=0; i<ap.length; i++){
            if(ap[i]!=-1)
            {
                Estado vizinho=this.agente.prob.suc(this.agente.estAtu, i);
                float custoAcao = this.heuristica[vizinho.getLin()][vizinho.getCol()] + this.agente.prob.obterCustoAcao(vizinho, i, vizinho);
                if(custoAcao < menorCusto)
                {
                    menorCusto=custoAcao;
                    acao=i;
                }
                else if(custoAcao == menorCusto)
                {
                    Random rand = new Random();
                    if(rand.nextInt(100)>=50)
                        acao=i;
                }
            }
        }
        Estado proximoEstado = this.agente.prob.suc(this.agente.estAtu, acao);
        this.heuristica[this.agente.estAtu.getLin()][this.agente.estAtu.getCol()]=this.heuristica[proximoEstado.getLin()][proximoEstado.getCol()]+this.agente.prob.obterCustoAcao(null, acao, null);
        
        //Printa a matriz de heuristica
        /*for(int i=0; i<5; i++){
            for(int j=0; j<9; j++)
            {
                System.out.printf("%.1f ", heuristica[i][j]);
            }
            System.out.println("");
        }
        System.out.println("**************************\n\n");*/
        return acao;
    }
}
