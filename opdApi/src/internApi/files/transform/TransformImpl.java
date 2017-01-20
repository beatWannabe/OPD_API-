package internApi.files.transform;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
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
                System.out.println("ocurrio eun error al intentar transformar el archivo [ " + file.getName() + " ] a HTML");
            }
            System.out.println(e.getMessage());
        }
    }

}
