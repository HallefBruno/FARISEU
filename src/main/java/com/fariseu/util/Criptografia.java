package com.fariseu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

    private static MessageDigest md = null;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.getMessage();
        }
    }

    private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }

    public static String criptografar(String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest(pwd.getBytes())));
        }
        return null;
    }
    
//    public static void main(String[] args) {
//        String senha = "123";
//        String hash = Criptografia.criptografar(senha);
//        
//        if(hash.equals("202CB962AC59075B964B07152D234B70")) {
//            System.out.println("Senha válida");
//        }
//        System.out.println(hash);
//    }
}
