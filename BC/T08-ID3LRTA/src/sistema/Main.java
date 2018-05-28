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
import java.util.Random;
import static comuns.PontosCardeais.*;

/**
 *
 * @author Willian
 */
public class Main {
    static boolean first_print=true;
    
    public static void main(String args[]) {
        // Cria o ambiente (modelo) = labirinto com suas paredes
        Model model = new Model(9, 9);
        model.labir.porParedeHorizontal(0, 1, 0);
        model.labir.porParedeHorizontal(4, 7, 0);
        model.labir.porParedeHorizontal(0, 0, 1);
        model.labir.porParedeHorizontal(3, 5, 2);
        model.labir.porParedeHorizontal(3, 6, 3);
        model.labir.porParedeVertical(6, 8, 1);
        model.labir.porParedeVertical(5, 5, 2);
        model.labir.porParedeVertical(8, 8, 2);
        model.labir.porParedeVertical(6, 7, 4);
        model.labir.porParedeVertical(5, 6, 5);
        model.labir.porParedeVertical(5, 7, 7);
        
        // seta a posição inicial do agente no ambiente - nao interfere no 
        // raciocinio do agente, somente no amibente simulado
        model.setPos(8, 0);
        model.setObj(2, 6);
        
        // Cria um agente
        Agente ag = new Agente(model);
        
        // Ciclo de execucao do sistema
        // desenha labirinto
        model.desenhar(); 
        
        // agente escolhe proxima açao e a executa no ambiente (modificando
        // o estado do labirinto porque ocupa passa a ocupar nova posicao)
        int execucoes=0, n_solucoes_otimas=0;
        float best_custo=100;
        List <List> solucoes_otimas = new ArrayList<>();
        
        System.out.println("\n*** Inicio do ciclo de raciocinio do agente ***\n");
        while(execucoes < 100)
        {
            execucoes++;
            System.out.println("Execuções: " + execucoes);
            while (ag.deliberar() != -1) {  
                //model.desenhar(); 
            }
            
            if(ag.custo<best_custo)
            {
                n_solucoes_otimas=1;
                solucoes_otimas.clear();
                solucoes_otimas.add(ag.plan);
                best_custo=ag.custo;
            }
            else if(ag.custo==best_custo)
            {
                if(!solucoes_otimas.contains(ag.plan))
                {
                    n_solucoes_otimas++;
                    solucoes_otimas.add(ag.plan);
                }
            }
            model.setPos(8, 0);
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
        
        model.setPos(8, 0);
        ag.learning=false;
        ag.random_eat=true; 
        ag.resetarAgente();
        
        Main.escreveEmArquivo(null, "Pontuação - Aleatório");
        for(execucoes=0; execucoes<100; execucoes++)
        {
            System.out.println("Execuções: " + execucoes);
            Random random = new Random();
            ag.plan=solucoes_otimas.get(random.nextInt(solucoes_otimas.size()));
            while(ag.deliberar()!=-1);
            float[] parametros={execucoes,ag.pontuacao};
            Main.escreveEmArquivo(parametros, null);
            model.setPos(8, 0);
            ag.resetarAgente();
        }
        
        ag.random_eat=false;
        Main.escreveEmArquivo(null, "Pontuação - Estratégia");
        
        for(execucoes=0; execucoes<100; execucoes++)
        {
            System.out.println("Execuções: " + execucoes);
            Random random = new Random();
            ag.plan=solucoes_otimas.get(random.nextInt(solucoes_otimas.size()));
            while(ag.deliberar()!=-1);
            float[] parametros={execucoes,ag.pontuacao};
            Main.escreveEmArquivo(parametros, null);
            model.setPos(8, 0);
            ag.resetarAgente();
        }
    }
    
    static public void escreveEmArquivo(float[] parametros, String string){
        BufferedWriter bw = null;
        FileWriter fw = null;
        String data;
        
        try {
            if(string!=null)
                data=string + System.getProperty("line.separator");
            else
            {
                data=String.valueOf(parametros[0]);
                for(int i=1; i<parametros.length; i++)
                    data+=","+String.valueOf(parametros[i]);
                data+=System.getProperty("line.separator");
            }
            File file = new File("id3-lrta.txt");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append to file
            if(!first_print)
                fw = new FileWriter(file.getAbsoluteFile(), true);
            else {
                fw = new FileWriter(file.getAbsoluteFile(), false);
                first_print=false;
            }
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