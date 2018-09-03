/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author 2112712
 */
public class Pagina {
    private File pag=null;
    private String ruta;
    
    public  Pagina(String rutaPagina){
        pag=new File(rutaPagina);
        this.ruta=rutaPagina;
    }
    
    public String loadPage(){
        String result="";
        try{
            FileReader fReader= new FileReader(pag);
            BufferedReader leer = new BufferedReader(fReader);
            String a;
            while((a = leer.readLine())!= null)
                result+=a+"\n";
            leer.close();
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return result;
    }
    
   
}