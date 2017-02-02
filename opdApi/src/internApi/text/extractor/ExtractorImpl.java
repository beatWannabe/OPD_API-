/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internApi.text.extractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pdgomezl
 */
public class ExtractorImpl {

    /**
     * Permite separar un caracter de una cadena completa
     *
     * @param value cadena inicial
     * @param separator valor a desprender de la cadena
     * @return el valor ingresado sin el valor ingresado para separar
     */
    public static String withoutChar(String value, char separator) {
        String withouFin = "";
        for (char object : value.toCharArray()) {
            if (object != separator) {
                withouFin += object;
            }
        }
        return withouFin;
    }

    /**
     * Obtiene un texto ingresado hasta la cantidad maxima ingresada si se
     * ingresa "hola mundo" con un valor de 4 se obtendra "hola" si se desea
     * puede agregarse 3 puntos suspensivos al final de la cadena (...)
     *
     * @param text valor a minimizar
     * @param max valor maximo de caracteres a obtener
     * @param indefinido si se desean 3 puntos suspensivos al final de la cadena
     * @return el valor minimizado
     */
    public static String incompleteText(String text, int max, boolean indefinido) {
        String indefin = "";
        if (indefinido) {
            indefin = "...";
        }
        String incompleteText = text;
        if (text.length() > max) {
            incompleteText = "";
            short cont = 0;
            for (char runnerObject : text.toCharArray()) {
                if (cont == max) {
                    break;
                } else {
                    incompleteText += runnerObject;
                    cont++;
                }
            }
            return incompleteText + indefin;
        }
        return incompleteText;
    }

    /**
     * Obtiene una lista de los archivos almacenados en un directorio especifico
     *
     * @param path directorio
     * @return lista de <span style="color:red" >archivos</span> en el 
     * directorio
     */
    public static List<File> getArchivesInDirectory(String path) {
        File dir = new File(path);
        File[] ficheros = dir.listFiles();
        List<File> files = new ArrayList<>();
        try {
            if (dir.exists()) {
                if (ficheros == null) {
                    System.out.println("No hay ficheros en el directorio especificado");
                } else {
                    for (File fichero : ficheros) {
                        //System.out.println(fichero.getName());
                        files.add(fichero);
                    }
                }
            } else {
                System.out.println("No existe el directorio : " + dir.getPath());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return files;
    }
}
