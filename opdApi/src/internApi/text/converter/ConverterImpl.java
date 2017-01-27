package internApi.text.converter;

import java.text.DecimalFormat;

/**
 *
 * @author pdgomezl
 */
public class ConverterImpl {

    /**
     * Permite transformar un valor en bytes a su valor mas dependiente
     *
     * @param bytes valor en bytes
     * @return valor de tipo cadena en su valor dependiente (si el valor en
     * bytes sobre-pasa un KB entonces retornara MB)
     */
    public static String informaticSizeFromBytes(long bytes) {
        double kb = bytes / 1024.0;
        double mb = bytes / 1048576.0;
        double gb = bytes / 1099511627776.0;
        double tb = bytes / 1208925819614629174706176.0;//*tb=proximo valor
        //double ne = bytes / tb*2;
        //continue multiplicando al exponente 2
        DecimalFormat dec = new DecimalFormat("0.00");

        String size = "0 OPDBytes";
        if (tb > 1) {
            size = dec.format(tb).concat(" TB");
        } else if (gb > 1) {
            size = dec.format(gb).concat(" GB");
        } else if (mb > 1) {
            size = dec.format(mb).concat(" MB");
        } else if (kb > 1) {
            size = dec.format(kb).concat(" KB");
        } else {
            size = bytes + " B";
        }
        return size;
    }
}
