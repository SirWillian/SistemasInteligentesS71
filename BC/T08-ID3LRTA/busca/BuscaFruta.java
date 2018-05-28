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

import comuns.*;

public class BuscaFruta {

    public Fruta labirinto[][];
    
    public BuscaFruta(Labirinto lab){
        labirinto = new Fruta[lab.getMaxLin()][lab.getMaxCol()];
        for(int i=0; i<lab.getMaxLin(); i++)
            for(int j=0; j<lab.getMaxCol(); j++)
                labirinto[i][j]=new Fruta();
    }
}
