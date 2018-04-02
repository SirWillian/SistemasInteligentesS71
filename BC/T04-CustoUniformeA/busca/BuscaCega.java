package busca;
import arvore.*;
import sistema.Agente;
import java.util.ArrayList;
import java.util.List;

public class BuscaCega
{
    protected TreeNode arvore;
    protected List<TreeNode> fronteira = new ArrayList<>();
    protected fnComparator comparador;
    protected Agente agente;
    
    public BuscaCega(Agente agnt)
    {
        this.agente=agnt;
        this.arvore=new TreeNode(arvore);
        this.arvore.setState(agnt.estAtu);
        this.arvore.setGnHn(0, 0);
        this.fronteira.add(arvore);
    }
    
    public int[] gerarSolucao()
    {
        //Método criado para ser sobrescrito pelos filhos
        return new int[1];
    }
    
    public void addNaFronteira(TreeNode node)
    {
        boolean isInFronteira=false;
        for(TreeNode each : fronteira)
        {
            if(node.getState()==each.getState())
            {
                isInFronteira=true;
                break;
            }
        }
        if(isInFronteira)
        {
            //replace "each" with "node"
        }
        else
            //procura o indice e insere
    }
}