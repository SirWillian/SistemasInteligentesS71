package busca;

import arvore.*;
import sistema.Agente;
import java.util.ArrayList;
import java.util.List;

public class BuscaAStar extends BuscaCega
{
    private int heuristica; //Heuristica 0 == Distância em linha reta; Heurística 1 == Distância de Manhattan
    public BuscaAStar(Agente agnt, int h)
    {
        super(agnt);
        this.heuristica=h;
    }
    
    public int [] gerarSolucao()
    {
        List<TreeNode> explorados = new ArrayList<>();
        TreeNode node;
        int[] ap;
        
        while(true)
        {
            if(this.fronteira.isEmpty())
                return new int[1]; //Não achou nada
            else
            {
                node=this.fronteira.remove(0); //"Pop" da fronteira
                if(this.agente.prob.testeObjetivo(node.getState()))
                    break;
                explorados.add(node);
                
                //Para cada ação possível, adiciona um nó na árvore de busca
                ap=this.agente.prob.acoesPossiveis(node.getState());
                for (int i=0; i<ap.length; i++)
                {
                    if(ap[i]!=-1)
                    {
                        TreeNode tmp = node.addChild();
                        tmp.setAction(i);
                        tmp.setState(this.agente.prob.suc(node.getState(), i));
                        tmp.setGn(node.getGn()+this.agente.prob.obterCustoAcao(node.getState(), i, tmp.getState()));
                        
                        int distanciaX = Math.abs(node.getState().getCol()-tmp.getState().getCol());
                        int distanciaY = Math.abs(node.getState().getLin()-tmp.getState().getLin());
                        if(heuristica==0)
                            tmp.setHn((float)Math.sqrt(distanciaX*distanciaX + distanciaY*distanciaY));
                        else
                            tmp.setHn(distanciaX+distanciaY);
                        
                        //fazer check de já explorado
                        
                        //addNaFronteira(tmp);
                    }
                }
            }
        }
        //Remonta solução a partir do break
    }
}