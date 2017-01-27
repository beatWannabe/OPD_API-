package internApi.text.formater;

import java.text.DecimalFormat;

/**
 *
 * @author pdgomezl
 */
public class FormaterImpl {

    /**
     * Cambia el formato de una cadena numerica a un valor numerico de 
     * reconocible como dinero
     * 
     * @param string valor a formatear
     * @return 
     */
    public static Long formatToMoney(String string) {
        DecimalFormat format = new DecimalFormat("###,###.##");
        return Long.decode(format.format(string));
    }

}
