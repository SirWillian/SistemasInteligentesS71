/** Itens que serão considerados para se colocar na mochila
 *
 * @author WILLIAN
 */
package mochila;

public class MochilaItems
{
    public int[] pesos;
    public int[] valores;
    public int n_elementos;
    
    public MochilaItems(int[] p, int[] v, int n)
    {
        this.pesos=p;
        this.valores=v;
        this.n_elementos=n;
    }
}
