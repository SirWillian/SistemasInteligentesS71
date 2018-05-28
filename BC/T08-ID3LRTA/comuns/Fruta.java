/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WILLIAN
 */
package comuns;

import java.util.Random;

public class Fruta {
    public char[] cores;
    public boolean foi_comida;
    private int energia;
    
    public Fruta(){
        Random random = new Random();
        cores = new char[5];
        foi_comida=false;
        //Atribui as cores à fruta de forma aleatória
        if(random.nextInt(2)==0)
            cores[0]='K';
        else
            cores[0]='W';
        for(int i=0; i<3; i++){
            switch(random.nextInt(3)){
                case 0:
                    cores[i+1]='R';
                    break;
                case 1:
                    cores[i+1]='G';
                    break;
                case 2:
                    cores[i+1]='B';
                    break;
            }
        }
        if(random.nextInt(2)==0)
            cores[4]='K';
        else
            cores[4]='W';
        
        this.setEnergia();
    }
    
    private void setEnergia()
    {
        switch(cores[1]){
            case 'R':
                switch(cores[3]){
                    case 'R':
                        switch(cores[2]){
                            case 'R':
                                this.energia=4;
                                break;
                            case 'G':
                                this.energia=2;
                                break;
                            case 'B':
                                this.energia=2;
                                break;
                        }
                        break;
                    case 'G':
                        switch(cores[2]){
                            case 'R':
                                this.energia=2;
                                break;
                            case 'G':
                                this.energia=0;
                                break;
                            case 'B':
                                this.energia=0;
                                break;
                        }
                        break;
                    case 'B':
                        switch(cores[2]){
                            case 'R':
                                this.energia=2;
                                break;
                            case 'G':
                                this.energia=4;
                                break;
                            case 'B':
                                this.energia=2;
                                break;
                        }
                        break;
                }
                break;
            case 'G':
                if(cores[0]=='W')
                    this.energia=0;
                else{
                    switch(cores[3]){
                        case 'R':
                            switch(cores[2]){
                                case 'R':
                                    this.energia=2;
                                    break;
                                case 'G':
                                    this.energia=2;
                                    break;
                                case 'B':
                                    this.energia=0;
                                    break;
                            }
                            break;
                        case 'G':
                            switch(cores[2]){
                                case 'R':
                                    this.energia=2;
                                    break;
                                case 'G':
                                    this.energia=4;
                                    break;
                                case 'B':
                                    this.energia=2;
                                    break;
                            }
                            break;
                        case 'B':
                            switch(cores[2]){
                                case 'R':
                                    this.energia=0;
                                    break;
                                case 'G':
                                    this.energia=2;
                                    break;
                                case 'B':
                                    this.energia=2;
                                    break;
                            }
                            break;
                    }
                }
                break;
            case 'B':
                switch(cores[2]){
                    case 'R':
                        switch(cores[3]){
                            case 'R':
                                this.energia=2;
                                break;
                            case 'G':
                                this.energia=0;
                                break;
                            case 'B':
                                this.energia=2;
                                break;
                        }
                        break;
                    case 'G':
                        switch(cores[3]){
                            case 'R':
                                this.energia=0;
                                break;
                            case 'G':
                                this.energia=2;
                                break;
                            case 'B':
                                this.energia=2;
                                break;
                        }
                        break;
                    case 'B':
                        switch(cores[3]){
                            case 'R':
                                this.energia=2;
                                break;
                            case 'G':
                                this.energia=2;
                                break;
                            case 'B':
                                this.energia=4;
                                break;
                        }
                        break;
                }
                break;
        }
    }
    
    public int getEnergia(){
        return this.energia;
    }
}
