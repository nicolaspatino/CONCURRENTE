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
 * @author estudiante
 */
public class Cliente {
    public static Integer threads= 4;
    public static Integer request= 4;

    
    public static void main(String[] args) throws IOException {
        boolean isCompleted=false;
        
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        while (request>0){
            executor.execute(new URLReader(args));
            request--;
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
       
    }
    
    
        
}
