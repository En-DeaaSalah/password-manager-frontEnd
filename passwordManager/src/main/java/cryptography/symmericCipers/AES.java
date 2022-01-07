package cryptography.symmericCipers;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AES {


    private static final String ALGORITHM_NAME = "AES";
    private static final int BLOCK_SIZE = 128;






    public static SecretKey keyGenerator() {


        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME);
            keyGenerator.init(BLOCK_SIZE);

            return keyGenerator.generateKey();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;

    }


    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }




    public static SecretKey getKeyFromPassword(String password, String salt) {

        try {

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            return new SecretKeySpec(factory.generateSecret(spec)
                    .getEncoded(), "AES");



        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }





    public static String messageDecryption(String cipherMessage, SecretKey key){

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(key.getAlgorithm());


            cipher.init(Cipher.DECRYPT_MODE,key);

            return new String(cipher.doFinal(decoder(cipherMessage)), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }


        return null;



    }




        public static String messageEncryption(String plantMessage, SecretKey key){

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(key.getAlgorithm());

            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] cipherText = cipher.doFinal(plantMessage.getBytes());
            return encoder(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }


        return null;

    }


    static String encoder(byte[]data){

        return Base64.getEncoder().encodeToString(data);


    }


    static byte[] decoder(String data){

        return Base64.getDecoder().decode(data);


    }












}
