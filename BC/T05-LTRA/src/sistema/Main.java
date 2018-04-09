/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import ambiente.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static comuns.PontosCardeais.*;

/**
 *
 * @author tacla
 */
public class Main {
    public static void main(String args[]) {
        // Cria o ambiente (modelo) = labirinto com suas paredes
        Model model = new Model(5, 9);
        model.labir.porParedeVertical(3, 4, 1);
        model.labir.porParedeHorizontal(0, 1, 1);
        model.labir.porParedeHorizontal(3, 5, 1);
        model.labir.porParedeHorizontal(3, 6, 2);
        model.labir.porParedeHorizontal(3, 5, 3);
        model.labir.porParedeHorizontal(7, 8, 0);
        
        // seta a posição inicial do agente no ambiente - nao interfere no 
        // raciocinio do agente, somente no amibente simulado
        model.setPos(4, 0);
        model.setObj(2, 8);
        
        // Cria um agente
        Agente ag = new Agente(model);
        
        // Ciclo de execucao do sistema
        // desenha labirinto
        model.desenhar(); 
        
        // agente escolhe proxima açao e a executa no ambiente (modificando
        // o estado do labirinto porque ocupa passa a ocupar nova posicao)
        int execucoes=0, n_solucoes_otimas=0, iteracoes_ate_6otimas=0;
        List <List> solucoes_otimas = new ArrayList<>();
        
        System.out.println("\n*** Inicio do ciclo de raciocinio do agente ***\n");
        while(execucoes < 100)
        {
            execucoes++;
            System.out.println("Execuções: " + execucoes);
            while (ag.deliberar() != -1) {  
                //model.desenhar(); 
            }
            if(ag.custo==11.5)
            {
                if(!solucoes_otimas.contains(ag.plan))
                {
                    n_solucoes_otimas++;
                    solucoes_otimas.add(ag.plan);
                    if(n_solucoes_otimas==6)
                        iteracoes_ate_6otimas=execucoes;
                }
            }
            Main.escreveEmArquivo(execucoes, ag.custo, 0);
            model.setPos(4, 0);
            ag.resetarAgente();
        }
        System.out.println("Soluções: ");
        for(List lst : solucoes_otimas){
            for(int i=0; i<lst.size(); i++)
            {
                System.out.print(acao[(int)lst.get(i)] + " ");
            }
            System.out.println("");
        }
                
        Main.escreveEmArquivo(0,0,iteracoes_ate_6otimas);
    }
    
    static public void escreveEmArquivo(int execucoes, double custo, int iteracoes){
        BufferedWriter bw = null;
        FileWriter fw = null;
        String data;
        
        try {
            if(iteracoes!=0)
                data="Execucoes ate encontrar todas as solucoes otimas: " + iteracoes + System.getProperty("line.separator");
            else
                data=execucoes + " " + String.valueOf((float)custo/11.5) + System.getProperty("line.separator");

            File file = new File("competitividade.txt");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append to file
            if(execucoes==1)
                fw = new FileWriter(file.getAbsoluteFile(), false);
            else
                fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(data);
        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }
}
