/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import internApi.timer.TimerImpl;
import internApi.files.FilesImpl;
import internApi.files.transform.TransformImpl;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import javafx.scene.transform.Transform;

/**
 *
 * @author pdgomezl
 */
public class test {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        //FilesImpl.createNewFile("C:/holis", "holis.txt");
//        for (String arg : TimerImpl.getHourMap("militar", ":", true, true)) {
//            System.out.println(arg);
//        }
//        TransformImpl.transformExcelToHTML();

        //Desktop.getDesktop().mail(new URI("mailto:pdglobo@misena.edu.co?subject=This&body=asdasd"));
        /* beware
        Process proceso = Runtime.getRuntime().exec("cmd /c SHUTDOWN /S /T 50");
         */
        //FilesImpl.createNewFile("C:\\Users\\pdgomezl\\Desktop\\", "hologram.png");
        //FilesImpl.adContentToFile("C:\\Users\\pdgomezl\\Desktop\\", "hologram.txt", "-0 -2 -3 -3 -25 -235 -3 -3 3 4 4");
        //FilesImpl.deleteFile("C:\\Users\\pdgomezl\\Desktop\\", "hologram.txt");
        //FilesImpl.cretaeNewDirectory("C:\\lelelele\\");
        //TransformImpl.excelToHtml("C:\\Users\\pdgomezl\\Desktop\\registroempleados.xlsx");
        //TransformImpl.decompressFile("C:\\Users\\pdgomezl\\Desktop\\EXPERIMENTS.zip", "C:\\Users\\pdgomezl\\Desktop\\EXPERIMENTS");
        TransformImpl.compressToZip("C:\\Users\\pdgomezl\\Desktop\\EXPERIMENTS");
    }
    
}
