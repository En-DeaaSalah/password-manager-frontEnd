package cryptography.signatures;

import cryptography.asymmericCipers.RSA;
import customRequests.Request;
import customResponses.AddItemResponse;

import java.security.*;

public class UserSign {


    public static byte[] signMessage(byte[] message, PrivateKey privateKey) {


        byte[] signedData = null;
        try {
            Signature mySig = Signature.getInstance("SHA1withRSA");
            mySig.initSign(privateKey);
            mySig.update(message);
            signedData = mySig.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }


        return signedData;

    }





    public static boolean verifySign(byte[] message, PublicKey publicKey, byte[] sign) {

        try {

            Signature theirSig = Signature.getInstance("SHA1withRSA");
            theirSig.initVerify(publicKey);
            theirSig.update(message);

            if (theirSig.verify(sign)) {
                System.out.println("Signature checks out.");
            } else {
                System.out.println("Signature failed. Possible imposter found.");
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static void main(String[] args) {


        String message = "this is message for test";


        KeyPair keyPair = RSA.generateKeys();


        System.err.println(message);

        String encryptedMessage = RSA.encrypt(message, keyPair.getPublic());


        System.err.println(encryptedMessage);

        String signMessage = new String(signMessage(encryptedMessage.getBytes(), keyPair.getPrivate()));


        System.err.println(verifySign(encryptedMessage.getBytes(), keyPair.getPublic(), signMessage.getBytes()));


        String decryptedMessage = RSA.decrypt(encryptedMessage, keyPair.getPrivate());


        System.err.println(decryptedMessage);


    }
}
