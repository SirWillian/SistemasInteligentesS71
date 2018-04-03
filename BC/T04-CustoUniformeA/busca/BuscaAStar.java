package busca;

import arvore.*;
import sistema.Agente;
import java.util.ArrayList;
import java.util.List;

public class BuscaAStar extends BuscaCega
{
    private int heuristica; //Heuristica 0 == Distância em linha reta; Heurística 1 == Distância de Chebyshev
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
                return null; //Não achou nada
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
                        this.agente.treeSize++;
                        tmp.setAction(i);
                        tmp.setState(this.agente.prob.suc(node.getState(), i));
                        tmp.setGn(node.getGn()+this.agente.prob.obterCustoAcao(node.getState(), i, tmp.getState()));
                        
                        int distanciaX = Math.abs(this.agente.prob.estObj.getCol()-tmp.getState().getCol());
                        int distanciaY = Math.abs(this.agente.prob.estObj.getLin()-tmp.getState().getLin());
                        if(heuristica==0)
                            tmp.setHn((float)Math.sqrt(distanciaX*distanciaX + distanciaY*distanciaY));
                        else
                            tmp.setHn(Math.max(distanciaX, distanciaY));
                        
                        boolean isExplorado=false;
                        for(TreeNode each : explorados)
                        {
                            if(tmp.getState().igualAo(each.getState()))
                            {
                                isExplorado=true;
                                this.agente.ct_ja_explorados++;
                                break;
                            }
                        }
                        if(!isExplorado)
                            addNaFronteira(tmp);
                    }
                }
            }
        }
        int[] solucao = new int[node.getDepth()];
        //Percorre de baixo pra cima na árvore
        while(node!=this.arvore)
        {
            solucao[node.getDepth()-1]=node.getAction();
            node=node.getParent();
        }
        //this.arvore.printSubTree();
        return solucao;
    }
}