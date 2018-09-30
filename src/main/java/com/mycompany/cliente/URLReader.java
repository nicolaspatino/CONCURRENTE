/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cliente;

import java.io.*; 
import java.net.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
public class URLReader implements Runnable  { 
    String[] args;
    
    URLReader(String[] args) {
        this.args=args;
    }
 
 @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        URL url;
        try {
            url = new URL(args[0]);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()))) {
                String inputLine = null;
                while ((inputLine = reader.readLine()) != null) {
                    System.out.println(inputLine);
                }
            } catch (IOException x) {
                System.err.println(x);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tiempo de ejecucion : " + startTime);
    }
}