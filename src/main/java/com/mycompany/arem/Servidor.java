/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arem;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author nicolas
 */
public class Servidor {
    public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = null;
        Integer PORT;
        try{
            PORT = new Integer(System.getenv("PORT"));
        }catch(Exception e){
            PORT = 35000;
        }
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        
        ExecutorService ex= Executors.newFixedThreadPool(25);
        while (true) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }    
            RequestHandler func= new RequestHandler(clientSocket);
            ex.execute(func);
        }
    }
}
