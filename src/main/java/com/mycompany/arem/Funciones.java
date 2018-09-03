/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arem;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageReader;

/**
 *
 * @author nicolas
 */
public class Funciones {
    private File imagen;
    private Socket socket;
    public Funciones(Socket sock){
        this.socket=sock;
    }
    
    public void ejecutar ()throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
       
        String inputLine, outputLine;
        String resultado;   
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            try {
                inputLine = inputLine.split(" ")[1];
                if (inputLine.endsWith(".html")) {
                    
                    File pagina = new File("./" + inputLine);
                    resultado = " ";
                    try {
                        FileReader fReader = new FileReader(pagina);
                        BufferedReader bReader = new BufferedReader(fReader);
                        String line;
                        while ((line = bReader.readLine()) != null) {
                            resultado += line;
                        }
                        
                        bReader.close();
                    } catch (IOException ex) {
                        System.err.println("Error en la lectura del Buffer");
                        ex.printStackTrace();
                    }
                    if (!in.ready()) {
                        break;
                    }

                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            + resultado;
                    out.println(outputLine);
    
                }else if(inputLine.endsWith(".png")){
                    File image;
                    image=new File(inputLine.substring(1,inputLine.length()));
                    byte[] data =null;
                    try { 
                        FileInputStream inFile = new FileInputStream(image);
                        data =new byte[(int) image.length()];
                        inFile.read(data);
                        inFile.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ImageReader.class.getName()).log(Level.SEVERE, null, ex);
                        System.err.println("Error en la lectura de el archivo");
                    } catch(IOException ex){                        
                        System.err.println("Error en la lectura de el archivo");
                    }  
               
                    
                    
                    
                    
                    
                    
                    
                    
                ///byte[] lectorIg = lectorImg("imagen.png");
                DataOutputStream binaryOut  =new DataOutputStream(socket.getOutputStream());
                binaryOut.writeBytes("HTTP/1.1 200 OK \r\n");
                binaryOut.writeBytes("Content-Type: image/png\r\n");
                binaryOut.writeBytes("Content-Length: " + data.length);
                binaryOut.writeBytes("\r\n\r\n");
                binaryOut.write(data);
                binaryOut.close();
            }
                out.close();
                in.close();
            } catch (Exception e) {

            }
        }
    }
    public byte[] lectorImg(String direccion){
        imagen= new File(direccion);
        byte[] data =null;
        try{
            FileInputStream archivo = new FileInputStream(imagen);
            archivo.read(data);
            archivo.close();
        }catch(IOException e){
            System.err.println("Error en imagen");     
        }
        return data;
    }
    
}
