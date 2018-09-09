/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.arem;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author nicolas
 */
public class RequestHandler {
    private File imagen;
    private Socket socket;
    private ObjectOutputStream salida;
       /**
     * metodo creador de RequestHandler
     * @param sock representa el socket de cliente que se va a usar 
     */
    public RequestHandler(Socket sock){
        this.socket=sock;
    }
     /**
     * metodo encargado de leer las peticiones recibidas, extraera los bytes de un encabezado
     * y del archivo de la peticion y se los mostrara a el usuario segun el tipo de archivo (png o html)
     *@throws IOException
     */
    public void ejecutar ()throws IOException{
        try{     
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine, outputLine;
        String resultado;   
        while ((inputLine = in.readLine()) != null ) { 
            if (inputLine != " ") {
                try{
                inputLine=inputLine.split(" ")[1];}
                catch(Exception e){}
                if (inputLine.endsWith(".html")) {
                    
                    
                    File pagina = new File("./" + inputLine);
                    resultado = " ";
                    try {
                        System.out.println(pagina);
                        FileReader fReader = new FileReader(pagina);
                        try (BufferedReader bReader = new BufferedReader(fReader)) {
                            String line;
                            while ((line = bReader.readLine()) != null) {
                                resultado += line;
                            }
                        }
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
                    socket.close();
                }else if(inputLine.endsWith(".png")){ 
                    try{
                    byte[] bytesImagen = null;
                    bytesImagen = Files.readAllBytes(new File("./" + inputLine).toPath());
                    resultado = "" + bytesImagen.length;                            
                    String formato = "image/png"; 
                    outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: "
                    + formato
                    + "\r\n"
                    + resultado
                    + "\r\n\r\n";            
                    
                    byte[] bytesStream = outputLine.getBytes();
                    byte[] pantalla = new byte[bytesImagen.length + bytesStream.length];
                    for (int i = 0; i < bytesStream.length; i++) {
                        pantalla[i] = bytesStream[i];
                    }
                    for (int i = bytesStream.length; i < bytesStream.length + bytesImagen.length; i++) {
                        pantalla[i] = bytesImagen[i - bytesStream.length];
                    }
                    socket.getOutputStream().write(pantalla);
                    out.println(outputLine);
                    socket.close();
                    }catch(Exception e){
                        
                    
                    }
                }else{
                    
                    File pagina = new File("./" + "index.html");
                    resultado = " ";
                    try {
                        FileReader fReader = new FileReader(pagina);
                        try (BufferedReader bReader = new BufferedReader(fReader)) {
                            String line;
                            while ((line = bReader.readLine()) != null) {
                                resultado += line;
                            }
                        }
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
                    socket.close();
                }
                
        }
        }
    }
    catch(IOException e) {
        
    }       
    }
       
}
