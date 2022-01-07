package cryptography.asymmericCipers;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {


    public static KeyPair generateKeys() {

        KeyPairGenerator generator;
        KeyPair pair = null;
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            pair = generator.generateKeyPair();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return pair;
    }


    public static PrivateKey generatePrivateKeyFormString(String privateKey) {


        try {

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoder(privateKey));

            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePrivate(keySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static PublicKey generatePublicKeyString(String publicKey) {


        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoder(publicKey));


            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(spec);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static KeyPair generateKeysFormString(String privateKey, String publicKey) {


        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoder(publicKey));

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoder(privateKey));

            KeyFactory factory = KeyFactory.getInstance("RSA");

            return new KeyPair(factory.generatePublic(spec), factory.generatePrivate(keySpec));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String encrypt(String object, PublicKey publicKey) {

        try {

            byte[] objectToByte = object.getBytes();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedByte = cipher.doFinal(objectToByte);

            return encoder(encryptedByte);


        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }


    public static String decrypt(String object, PrivateKey privateKey) {

        try {

            byte[] objectToByte = decoder(object);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptedByte = cipher.doFinal(objectToByte);

            return new String(decryptedByte, StandardCharsets.UTF_8);


        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }


    public static String encoder(byte[] data) {

        return Base64.getEncoder().encodeToString(data);


    }

    public static byte[] decoder(String data) {

        return Base64.getDecoder().decode(data);


    }


}
