package pi.quarto.semestre.codigo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cripto {
    
    public String crip(final String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte MessageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : MessageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhahex = hexString.toString();
        return senhahex;
    }
}
