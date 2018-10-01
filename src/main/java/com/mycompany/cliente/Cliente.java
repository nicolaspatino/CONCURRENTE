/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas Patiño
 */
public class Cliente {
        
    /**
     * @param args
     * @throws IOException 
     */

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        int res = 0;
        while (res<5){
            executor.execute(new URLReader(args));
            res+=1;
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
       
    }
        
}
