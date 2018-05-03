package sistema;

import mochila.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WILLIAN
 */
public class Main {
    boolean first_print=true;
    
    public static void main(String args[]) {
        int[] pesos = {3,8,12,2,8,4,4,5,1,1,8,6,4,3,3,5,7,3,5,7,4,3,7,2,3,5,4,3,7,19,20,21,11,24,13,17,18,6,15,25,12,19};
        int[] valores = {1,3,1,8,9,3,2,8,5,1,1,6,3,2,5,2,3,8,9,3,2,4,5,4,3,1,3,2,14,32,20,19,15,37,18,13,19,10,15,40,17,39};
        MochilaItems mochila = new MochilaItems(pesos, valores, 42);
        
        List<Mochila> best_mochilas = new ArrayList<>();
        int best_fitness=0;
        Mochila tmp_best;
        
        for(int execucoes=0; execucoes<1000; execucoes++)
        {
            MochilaSolver solver = new MochilaSolver(mochila, 113, 42);
            solver.evolve();
            tmp_best=solver.getBestMochila();
            if(tmp_best.fitness > best_fitness){
                best_mochilas.clear();
                best_mochilas.add(tmp_best);
                best_fitness=tmp_best.fitness;
            }
            else if (tmp_best.fitness==best_fitness)
                best_mochilas.add(tmp_best);
        }
        
    }
    
    public void escreveEmArquivo(int[] parametros, String string){
        BufferedWriter bw = null;
        FileWriter fw = null;
        String data;
        
        try {
            if(string!=null)
                data=string + System.getProperty("line.separator");
            else
            {
                data=String.valueOf(parametros[0]);
                for(int i=0; i<parametros.length; i++)
                    data+=","+String.valueOf(parametros[i]);
                data+=System.getProperty("line.separator");
            }
            File file = new File("alg_geneticos.txt");
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
