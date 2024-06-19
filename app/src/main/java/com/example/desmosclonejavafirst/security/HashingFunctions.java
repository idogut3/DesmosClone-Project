//package com.example.desmosclonejavafirst.security;
//
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Formatter;
//
//public class HashingFunctions {
//
//    public static String encryptPasswordInSHA1(String password)
//    {
//        String sha1 = "";
//        try
//        {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//            crypt.reset();
//            crypt.update(password.getBytes(StandardCharsets.UTF_8));
//            sha1 = byteToHex(crypt.digest());
//        }
//        catch(NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//        return sha1;
//    }
//
//    private static String byteToHex(final byte[] hash)
//    {
//        Formatter formatter = new Formatter();
//        for (byte b : hash)
//        {
//            formatter.format("%02x", b);
//        }
//        String result = formatter.toString();
//        formatter.close();
//        return result;
//    }
//
//}
