package busca;
import arvore.*;
import sistema.Agente;
import java.util.ArrayList;
import java.util.List;

public class BuscaCega
{
    protected TreeNode arvore;
    protected List<TreeNode> fronteira = new ArrayList<>();
    protected Agente agente;
    
    public BuscaCega(Agente agnt)
    {
        this.agente=agnt;
        this.arvore=new TreeNode(this.arvore);
        this.arvore.setState(agnt.estAtu);
        this.arvore.setGnHn(0, 0);
        this.fronteira.add(this.arvore);
    }
    
    public int[] gerarSolucao()
    {
        //Método criado para ser sobrescrito pelos filhos
        return null;
    }
    
    public void addNaFronteira(TreeNode node)
    {
        boolean isInFronteira=false;
        TreeNode replaceNode=new TreeNode(node);
        int index=0;
        for(TreeNode each : this.fronteira)
        {
            if(node.getFn() > each.getFn())
                index++;
            if(node.getState().igualAo(each.getState()))
            {
                isInFronteira=true;
                this.agente.ct_descartados_front++;
                replaceNode=each;
                break;
            }
        }
        if(isInFronteira)
        {
            if(node.getFn() < replaceNode.getFn())
                this.fronteira.set(this.fronteira.indexOf(replaceNode), node);
        }
        else
            this.fronteira.add(index, node);
    }
}