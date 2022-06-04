package com.company.Recursos;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Hash {

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
}
