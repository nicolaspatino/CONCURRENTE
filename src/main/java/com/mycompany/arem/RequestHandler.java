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
import java.nio.file.NoSuchFileException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author nicolas
 */
public class RequestHandler implements Runnable{
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
     */
    @Override
    public void run (){
         try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine=in.readLine();
            String outputLine;
            String archivo = null;
            String data = null;
            byte[] bytes = null;
            if (in.readLine() != null) {
                inputLine=inputLine.split(" ")[1];
                System.out.println(inputLine);
                try {
                if (inputLine.endsWith(".html")) {
                    bytes = Files.readAllBytes(new File("./" + inputLine.substring(1)).toPath());
                    data = "" + bytes.length;
                    archivo = "text/html";
                } else if (inputLine.endsWith("png")) {
                    bytes = Files.readAllBytes(new File("./" + inputLine.substring(1)).toPath());
                    data = "" + bytes.length;
                    archivo = "image/png";
                } else {
                    bytes = Files.readAllBytes(new File("./index.html").toPath());
                    data = "" + bytes.length;
                    archivo = "text/html";         
                }
                } catch (Exception e){
                    bytes = Files.readAllBytes(new File("./index.html").toPath());
                    data = "" + bytes.length;
                    archivo = "text/html";
                }
            }
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: "
                    + archivo
                    + "\r\n"
                    + "Content-Length: "
                    + data
                    + "\r\n\r\n";
            byte[] bt = outputLine.getBytes();
            try {
                byte[] salida = new byte[bytes.length + bt.length];
                for (int i = 0; i < bt.length; i++) {
                    salida[i] = bt[i];
                }
                for (int i = bt.length; i < bt.length + bytes.length; i++) {
                    salida[i] = bytes[i - bt.length];
                }
                socket.getOutputStream().write(salida);
            } catch (java.lang.NullPointerException e) {

            }
            socket.close();

        }
        catch (IOException ex) {
     Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
}
       

