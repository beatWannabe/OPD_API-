package internApi.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

/**
 *
 * @author pdgomezl
 */
public class FilesImpl {

    /**
     * Permite crear un archivo nuevo
     *
     * @param url la ruta donde se desea generar el archivo
     * @param name nombre del archivo
     * @throws IOException en caso de no tener acceso o ocurra un fallo en la
     * creacion
     */
    public static void createNewFile(String url, String name) throws IOException {
        String ruta = url + "/" + name;
        BufferedWriter bw = null;
        try {
            File archivo = new File(ruta);
            bw = new BufferedWriter(new FileWriter(archivo));
        } catch (IOException e) {
            System.out.println("Ocurrio un Exception al intentar crear el archivo : ");
            for (StackTraceElement runnerObject : e.getStackTrace()) {
                System.out.println(runnerObject);
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    /**
     * Permite sobreescriir un archivo
     *
     * @param url la ruta del archivo que se desea sobreescribir
     * @param name nombre del archivo que se desea sobreescribir
     * @param content contenido para reemplazar
     * @throws IOException
     */
    public static void overrideFile(String url, String name, String content) throws IOException {
        BufferedWriter bw = null;
        try {
            File archivo = new File(url + "/" + name);
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(content);
        } catch (IOException e) {
            System.out.println("Ocurrio un Exception al intentar sobreescribir el archivo : ");
            for (StackTraceElement runnerObject : e.getStackTrace()) {
                System.out.println(runnerObject);
            }
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }

    /**
     * Anade texto a un archivo creado
     *
     * @param url la ruta del archivo que se desea sobreescribir
     * @param name nombre del archivo que se desea sobreescribir
     * @param content contenido para reemplazar
     * @throws IOException
     */
    public static void adContentToFile(String url, String name, String content) throws IOException {
        BufferedWriter bw = null;
        try {
            File archivo = new File(url + "/" + name);
            bw = new BufferedWriter(new FileWriter(archivo, true));
            bw.write(content);
        } catch (IOException e) {
            System.out.println("Ocurrio un Exception al intentar sobreescribir el archivo : ");
            for (StackTraceElement runnerObject : e.getStackTrace()) {
                System.out.println(runnerObject);
            }
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }

    public static void overrideFileCustom(String url, String name, boolean newLine, String content, String separator) throws IOException {
        BufferedWriter bw = null;
        try {
            File archivo = new File(url + "/" + name);
            bw = new BufferedWriter(new FileWriter(archivo, true));
            if (newLine) {
                bw.newLine();
            }
            if (!separator.isEmpty()) {
                bw.newLine();
                bw.write(separator);
            }
            bw.write(content);
        } catch (IOException e) {
            System.out.println("Ocurrio un Exception al intentar sobreescribir el archivo : ");
            for (StackTraceElement runnerObject : e.getStackTrace()) {
                System.out.println(runnerObject);
            }
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }

    /**
     * Permite eliminar un archivo
     *
     * @param url la ruta del archivo que se desea eliminar
     * @param name nombre del archivo que se desea eliminar
     * @throws IOException
     */
    public static void deleteFile(String url, String name) throws IOException {
        File archivo = new File(url + "/" + name);
        if (archivo.exists()) {
            archivo.delete();
        } else {
            System.out.println("El archvivo o la ruta especificada no existe");
        }
    }

    public static void createInternLog(String clase, Exception exe) {
        try {
            String ruta = "C:/LOGSLinkin/Logger.txt";
            File archivo = new File(ruta);
            BufferedWriter bw;
            if (archivo.exists()) {
                bw = new BufferedWriter(new FileWriter(archivo, true));
                bw.newLine();
                bw.write("-----------------------------------------------");
                bw.newLine();
                bw.write("Error En : " + clase + " // " + new Timestamp(System.currentTimeMillis()));
                bw.newLine();
                bw.write("Exception : " + exe.getMessage());
                bw.newLine();
                bw.write("Exception Stack : ");
                for (StackTraceElement object : exe.getStackTrace()) {
                    bw.write(object.toString());
                }
            } else {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write("Error En : " + clase + " // " + new Timestamp(System.currentTimeMillis()));
                bw.newLine();
                bw.write("Exception : " + exe.getMessage());
                bw.newLine();
                bw.write("Exception Stack : ");
                for (StackTraceElement object : exe.getStackTrace()) {
                    bw.write(object.toString());
                }
            }
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void cretaeNewDirectory(String ruta) {
        try {
            File fil = new File(ruta);
            fil.mkdirs();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
