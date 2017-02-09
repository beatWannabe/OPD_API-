package internApi.files.transform;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * @author pdgomezl
*
 * Libs: Apache Poi
 * (poi-3.9.jar, poi-ooxml-3.9.jar, poi-ooxml-schemas-3.9.jar, dom4j-1.6.1.jar, xmlbeans-2.3.0.jar)
 * 
 */
public class TransformImpl {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
//--------------------->
    private static ZipOutputStream zipOutputStream;
    private final static int BUFFER = 2048;

    /**
     * Permite descromprimir un archivo en formato zip
     *
     * @param file nombre del archivo
     * @param path ruta del archivo
     */
    public static void decompressFileZip(String file, String path) {
        try {
            BufferedOutputStream dest = null;
            FileInputStream archivo = new FileInputStream(file);
            File dirDestino = new File(path);
            try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(archivo))) {
                FileOutputStream archivoTerminal;
                ZipEntry entrada;
                int count;
                int index;
                byte data[] = new byte[BUFFER];
                String rutaArchivo;
                while ((entrada = zis.getNextEntry()) != null) {
                    //System.out.println("Extracting: " + entry.getName() + " : " + informaticSizeFromBytes(entry.getSize()));
                    rutaArchivo = entrada.getName();
                    index = rutaArchivo.indexOf("/");
                    rutaArchivo = rutaArchivo.substring(index + 1);
                    if (rutaArchivo.trim().length() > 0) {
                        try {
                            dest = null;
                            File fileDest = new File(dirDestino.getAbsolutePath() + "/" + rutaArchivo);
                            if (entrada.isDirectory()) {
                                fileDest.mkdirs();
                            } else {
                                if (fileDest.getParentFile().exists() == false) {
                                    fileDest.getParentFile().mkdirs();
                                }
                                archivoTerminal = new FileOutputStream(fileDest);
                                dest = new BufferedOutputStream(archivoTerminal, BUFFER);
                                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                                    dest.write(data, 0, count);
                                }
                                dest.flush();
                            }
                        } finally {
                            try {
                                if (dest != null) {
                                    dest.close();
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permite comprimir un archivo en formato zip
     *
     * @param fileName nombre del archivo
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void compressToZip(String fileName)
            throws IOException, FileNotFoundException {
        File file = new File(fileName);
        zipOutputStream = new ZipOutputStream(new FileOutputStream(file + ".zip"));
        recurseFiles(file);
        zipOutputStream.close();
    }

    private static void recurseFiles(File file)
            throws IOException, FileNotFoundException {
        if (file.isDirectory()) {
            String[] fileNames = file.list();
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    recurseFiles(new File(file, fileName));
                }
            }
        } else {
            byte[] buf = new byte[1024];
            int len;
            ZipEntry zipEntry = new ZipEntry(file.toString());
            FileInputStream fin = new FileInputStream(file);
            try (BufferedInputStream in = new BufferedInputStream(fin)) {
                zipOutputStream.putNextEntry(zipEntry);
                while ((len = in.read(buf)) >= 0) {
                    zipOutputStream.write(buf, 0, len);
                }
            }
            zipOutputStream.closeEntry();
        }
    }

    //--------------------> transformExcelToHTML excel to html
    /**
     * Permite Buscar en el equipo un archivo de tipo excel ya sea xlsx o xls
     * convierte este en un achivo de tipo HTML
     *
     */
    private static final String[] FILE_TYPES = new String[]{"xls", "xlsx"};
    private static final String HTML_DOC = "<!DOCTYPE html><html><head><title>";
    private static final String HTML_HEAD_TABLE = "</title></head><body><table border=\"3px\">";
    private static final String HTML_FOOT = "</table></body></html>";
    private static final String HTML_TR = "<tr>";
    private static final String HTML_TR_CLOSE = "</tr>";
    private static final String HTML_TD = "<td>";
    private static final String HTML_TD_CLOSE = "</td>";
    private static final String NEW_LINE = "\n";
    private static final String HTML_FILE_EXTENSION = ".html";
    private static final String TEMP_FILE_EXTENSION = ".tmp";

    /**
     * Crea un archivo de tipo html con informacion en tablas desde un archivo
     * excel xlsx o xlx
     *
     * @param ruta
     * @throws java.io.FileNotFoundException
     */
    public static void excelToHtml(String ruta) throws FileNotFoundException, IOException {
        File file = null;
        try {
            file = new File(ruta);
            BufferedWriter writer;
            Workbook workbook;
            String fileName = file.getName();
            String folderName = file.getParent();
            if (fileName.toLowerCase().endsWith(TransformImpl.FILE_TYPES[0])) {
                workbook = new HSSFWorkbook(new FileInputStream(file));
            } else {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            }

            File tempFile = File.createTempFile(fileName + '-', HTML_FILE_EXTENSION
                    + TEMP_FILE_EXTENSION, new File(folderName));
            writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write(HTML_DOC);
            writer.write(fileName);
            writer.write(HTML_HEAD_TABLE);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            Iterator<Cell> cells;
            while (rows.hasNext()) {
                Row row = rows.next();
                cells = row.cellIterator();
                writer.write(NEW_LINE);
                writer.write(HTML_TR);
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    writer.write(HTML_TD);
                    writer.write(cell.toString());
                    writer.write(HTML_TD_CLOSE);
                }
                writer.write(HTML_TR_CLOSE);
            }
            writer.write(NEW_LINE);
            writer.write(HTML_FOOT);
            writer.close();
            File newFile = new File(folderName + '\\' + fileName + '-'
                    + System.currentTimeMillis() + HTML_FILE_EXTENSION);
            tempFile.renameTo(newFile);
            //Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + newFile);
            Desktop.getDesktop().open(newFile);
            //cerrar aplciacion
            System.exit(0);
            //newFile.delete();
        } catch (Exception e) {
            if (file != null) {
                System.out.println("ocurrio un error al intentar transformar el archivo [ " + file.getName() + " ] a HTML");
            }
            System.out.println(e.getMessage());
        }
    }
    //---------------------->
    //System.out.println("WIDTH : " +screenSize.width + " HEIGTH : " + screenSize.height);

    /**
     * Obtiene la dimencion width correcta en relacion con la pantalla y la
     * propiedad width de un objeto
     *
     * @param frameWidth propiedad width del objeto
     * @return tamaño ajustado a la pantalla
     */
    public static int screenDimensionWidthReziser(int frameWidth) {
        int widthTemporalCenter = (int) SCREEN_SIZE.getWidth() / 2 - frameWidth / 2;
        if (widthTemporalCenter < 0) {
            widthTemporalCenter *= -1;
        }
        return widthTemporalCenter;
    }

    /**
     * Obtiene la dimencion heith correcta en relacion con la pantalla y la
     * propiedad heith de un objeto
     *
     * @param frameHeith propiedad heith del objeto
     * @return tamaño ajustado a la pantalla
     */
    public static int screenDimensionHeithReziser(int frameHeith) {
        int heithTemporalCenter = (int) SCREEN_SIZE.getHeight() / 2 - frameHeith / 2;
        if (heithTemporalCenter < 0) {
            heithTemporalCenter *= -1;
        }
        return heithTemporalCenter;
    }
}
