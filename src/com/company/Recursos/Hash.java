package com.company.Recursos;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
//import org.apache.commons.codec.binary.Base64;


public class Hash {

    private static final int iterations = 10*1024;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;


    public String getPassHashed(String pass){
        String passEncrypt = "";
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256);
            SecretKey key = generator.generateKey();

            byte[] passByte = pass.getBytes();
            //Creamos cifrado
            Cipher cipher = Cipher.getInstance(generator.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);

            //Creamos pass encriptada
            byte[] passEncryptByte = cipher.doFinal(passByte);
            passEncrypt =  new String(passEncryptByte);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return  passEncrypt;
    }




//    public String getSaltedHash(String password) {
//        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
//        return Base64.encodeBase64String(salt + "$" + hash(password,salt));
//    }
//
//    public boolean check(String password, String stored){
//        String[] saltAndPass = stored.split("\\$");
//        if(saltAndPass.length != 2){
//            return false;
//        }
//        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
//        return hashOfInput.equals(saltAndPass[1]);
//    }
//
//    private String hash(String password, byte[] salt){
//        if(password == null  || password.length() == 0){
//            return null;
//        }
//
//        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//        SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
//
//        return Base64.encondeBase64String(key.getEncoded());
//    }


}
