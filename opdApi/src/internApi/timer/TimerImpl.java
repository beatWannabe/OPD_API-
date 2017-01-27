package internApi.timer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pdgomezl
 */
public class TimerImpl {

    private static final String HOUR_TYPE_MILITAR = "militar";
    private static final String HOUR_TYPE_STANDARD = "standard";

    /**
     * Obtiene la fecha actual del sistema
     *
     * @return fecha actual del sistema en un objeto timeStamp de sql
     */
    public static Timestamp dateMillis() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Obtiene una lista completa de el dia en horas y minutos en formato
     * militar de 0(24) a 23:59 o standard 12am a 12pm
     *
     * @param type si se desea obtener la hora en formato militar (24horas) o
     * standard 12horas
     * @param withSeparator si se desea obtener la hora con alguna especie de
     * separador por ejemplo si el valor ingresado es ":" se obtendra 11:35
     * @param withFormat si se desea obtener el formato en horario (a.m,p.m)
     * @param completeHour si se desea obtener la hora completa y no en codigos
     * por ejemplo si es verdadero 02:05 si no 2:5 ; 09:45 si no 9:45
     * @return lista de horas del dia con minutos
     */
    public static List<String> getHourMap(String type, String withSeparator, boolean withFormat, boolean completeHour) {
        try {
            switch (type) {
                case HOUR_TYPE_MILITAR: {
                    return timerMilitarHour(withSeparator, withFormat, completeHour);
                }
                case HOUR_TYPE_STANDARD: {
                    return timerNormieHour(withSeparator, withFormat, completeHour);
                }
                default: {
                    System.out.println("---> Error!! : No se reconoce el tipo especificado : [ " + type + " ]");
                }
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | ArithmeticException e) {
            System.out.println("Ocurrio un error durante la ejecucion de el metodo "
                    + "[getHourMap] de opdApi.timer");
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    //--------------------------metodos privados e implementos-----------------
    /**
     * Obtiene una lista completa de el dia en horas y minutos en formato
     * militar de 0(24) a 23:59
     *
     * @param withSeparator si se desea obtener la hora con alguna especie de
     * separador por ejemplo si el valor ingresado es ":" se obtendra 11:35
     * @param withFormat si se desea obtener el formato en horario (a.m,p.m)
     * @param completeHour si se desea obtener la hora completa y no en codigos
     * por ejemplo si es verdadero 02:05 si no 2:5 ; 09:45 si no 9:45
     * @return lista de horas del dia con minutos
     */
    private static List<String> timerMilitarHour(String withSeparator, boolean withFormat, boolean completeHour) {
        List<String> times;
        String hour;
        try {
            if (withSeparator == null) {
                withSeparator = "";
            }
            times = new ArrayList<>();
            for (int horas = 0; horas != 24; horas++) {
                for (int minutos = 0; minutos != 60; minutos++) {
                    if (!completeHour) {
                        hour = String.valueOf(horas) + withSeparator + String.valueOf(minutos);
                    } else {
                        hour = converterCompleteHour(String.valueOf(horas) + ":" + String.valueOf(minutos), withSeparator);
                    }
                    if (withFormat) {
                        if (horas < 12) {
                            times.add(hour + " a.m");
                        } else {
                            times.add(hour + " p.m");
                        }
                    } else {
                        times.add(hour);
                    }
                }
            }
            return times;
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene una lista completa de el dia en horas y minutos en formato
     * standard de 12am a 12pm
     *
     * @param withSeparator si se desea obtener la hora con alguna especie de
     * separador por ejemplo si el valor ingresado es ":" se obtendra 11:35
     * @param withFormat si se desea obtener el formato en horario (a.m,p.m)
     * @param completeHour si se desea obtener la hora completa y no en codigos
     * por ejemplo si es verdadero 02:05 si no 2:5 ; 09:45 si no 9:45
     * @return lista de horas del dia con minutos
     */
    private static List<String> timerNormieHour(String withSeparator, boolean withFormat, boolean completeHour) {
        List<String> times = new ArrayList<>();
        String actualMinimal = " a.m";
        String hour;
        try {
            if (withSeparator == null) {
                withSeparator = "";
            }
            for (int dia = 1; dia < 3; dia++) {
                for (int hora = 1; hora != 13; hora++) {
                    for (int minuto = 0; minuto != 60; minuto++) {
                        if (hora == 12 && minuto == 0) {
                            if (actualMinimal.equals(" a.m")) {
                                actualMinimal = " p.m";
                            } else {
                                actualMinimal = " a.m";
                            }
                        }
                        if (completeHour) {
                            hour = converterCompleteHour(hora + ":" + minuto, withSeparator);
                        } else {
                            hour = hora + withSeparator + minuto;
                        }

                        if (withFormat) {
                            times.add(hour + actualMinimal);
                        } else {
                            times.add(hour);
                        }
                    }
                }
            }
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return times;
    }

    /**
     * Recibe una hora en formato "1:3" transforma este formato a "01:03" una
     * hora completa
     *
     * @param hour hora incompleta de tipo vh:vm;
     * @param withSeparator si se desea separar la hora de alguna forma
     * (":";"-";"." ...)
     * @return formato de hora de tipo cadena en hh:mm
     */
    private static String converterCompleteHour(String hour, String withSeparator/*, boolean withFormat*/) {
        String separator = "";
        if (withSeparator != null) {
            separator = withSeparator;
        }
        String firstValue = "";
        String value = "";
//      String format = "";
        short cont = 0;
        short cont2 = 0;

        for (char object1 : hour.toCharArray()) {
            if (object1 != ':') {
                firstValue += object1;
                cont2++;
            } else if (cont2 == 1) {
                firstValue = "0" + firstValue;
                break;
            } else {
                break;
            }
        }
        /*
        if (withFormat) {
            if (Integer.parseInt(firstValue) < 12) {
                format = " a.m";
            } else {
                format = " p.m";
            }
        }
         */
        for (char object : new StringBuilder(hour).reverse().toString().toCharArray()) {
            if (object != ':') {
                value += object;
                cont++;
            } else if (cont == 1) {
                return firstValue + separator + "0" + value/* + format*/;
            } else {
                return firstValue + separator + new StringBuilder(value).reverse()/* + format*/;
            }
        }
        return hour;
    }
}
