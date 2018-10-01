/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.reflexWebServer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * ejecuta el servidor con una clase y servidor definidos
 * @author Nicolas Patiño
 */
public class ReflexWebServer {

    public static void main(String[] args) throws Exception {
        
        //Class c = Class.forName(args[0]);
            Class c = Class.forName("com.mycompany.arem.Servidor");
        for (Method m : c.getMethods()) {
            Constructor cons = c.getConstructor();
            if (m.isAnnotationPresent(Mapping.class)) {
                try {
                    Mapping anot = m.getAnnotation(Mapping.class);
                    System.out.println( anot.value());
                    Object o = cons.newInstance();
                    Object a = m.invoke(o);
                    String resp = a.toString();
                    System.out.println("La respuesta es: " + resp);

                } catch (Throwable ex) {
                    System.out.printf("error on %s: %s %n", m, ex.getCause());
                    ;
                }
            }
        }
        System.out.printf("Finished");
    }
}