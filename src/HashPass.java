import java.util.*;
import java.security.*;
import java.math.*;

/**
 * Klasa odpowiedzialna za hashowanie haseł
 */
public class HashPass {

    /**
     * Konstruktor domyslny
     */
    private HashPass() {}

    /**
     * Metoda zwracająca zahashowane hasło
     * @param txt, tekst do formatowania
     * @param hashType MD5 lub SHA1
     * @return CreateHash Hashowanie w podanym formacie
     */
    public static String CreateHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            //error action
        }
        return null;
    }

    /**
     * Metoda zwracająca zahashowane hasło w formacie MD5
     * @param txt tekst do formatowania
     * @return CreateHashHash MD5
     */
    public static String md5(String txt) {
        return HashPass.CreateHash(txt, "MD5");
    }

    /**
     * Metoda zwracająca zahashowane hasło w formacie SHA1
     * @param txt tekst do formatowania
     * @return CreateHashHash SHA1
     */
    public static String sha1(String txt) {
        return HashPass.CreateHash(txt, "SHA1");
    }
}
