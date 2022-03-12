package businessLayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clasa InfoValidator contine mai multe metode statice de verificare a unor String-uri primite ca si
 * parametrii. Acestea returneaza valoarea booleana true daca String-ul respecta conditiile metodei
 * si false in caz contrar
 */

public class InfoValidator {
    /**parcurege tot String-ul si verifica fiecare caracter;
     * daca se gaseste un caracter care nu este litera, spatiu sau cratima, atunci se returneaza false;
     * se returneaza true in caz contrar
     */
    public static boolean checkText(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' ' || s.charAt(i)=='.')
                continue;
            if((s.charAt(i)<'a' || s.charAt(i)>'z') && (s.charAt(i)<'A' || s.charAt(i)>'Z'))
                return false;
        }
        return true;
    }
    /**se foloseste regex pentru verificarea String-ului;
     * daca String-ul nu respecta structura unui numar de telefon, adica sa inceapa cu "07" si dupa alte 8 cifre
     * atunci se returneaza false;
     * se returneaza true in caz contrar
     */
    public static boolean checkPhone(String s){
        Pattern p=java.util.regex.Pattern.compile("07[0-9]{8}");
        Matcher match=p.matcher(s);
        return match.matches();
    }
    /**parcurege tot String-ul si verifica fiecare caracter;
     * daca se gaseste un caracter care nu este cifra, atunci se returneaza false;
     * se returneaza true in caz contrar
     */
    public static boolean checkNumber(String s){
        for(int i=0;i<s.length();i++)
            if(s.charAt(i)<'0' || s.charAt(i)>'9')
                return false;
        return true;
    }
}
