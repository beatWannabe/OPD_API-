/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internApi.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pdgomezl
 */
public class ValidatorImpl {

    /**
     * Determina si una cadena contiene datos numericos
     *
     * @param cadena valor a comparar
     * @return
     */
    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Permite determinar si un objeto es de tipo cadena
     *
     * @param str valor a comparar
     * @return
     */
    public static boolean isString(Object str) {
        return str.equals(str.toString());
    }

    /**
     * Permite determinar si una cadena corresponde a un email valido de tipo
     * abc@abcd.com
     *
     * @param string valor a comparar
     * @return
     */
    public static boolean isAEmail(String string) {
        try {
            String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            Pattern pattern = Pattern.compile(PATTERN_EMAIL);
            Matcher matcher = pattern.matcher(string);
            return matcher.matches();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
