package customRequests;

import cryptography.asymmericCipers.RSA;
import cryptography.signatures.UserSign;

import java.security.*;

public class RegistrationRequest extends Request<RegistrationRequest> {


    private String email;


    private String password;


    private String publicKey;


    private String userName;


    public RegistrationRequest(String email, String password, String publicKey, String userName) {
        this.email = email;
        this.password = password;
        this.publicKey = publicKey;
        this.userName = userName;
    }

    public RegistrationRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public RegistrationRequest encrypt(PublicKey key) {
        RegistrationRequest result = new RegistrationRequest();
        result.setPublicKey(this.publicKey);
        result.setEmail(RSA.encrypt(this.email, key));
        result.setPassword(RSA.encrypt(this.password, key));
        result.setUserName(RSA.encrypt(this.userName, key));
        return result;
    }

    public RegistrationRequest decrypt(PrivateKey key) {
        return null;
    }

    @Override
    public void signature(PrivateKey key) {
        try {
            Signature mySig = Signature.getInstance("SHA1withRSA");
            mySig.initSign(key);

            mySig.update(this.email.getBytes());
            mySig.update(this.password.getBytes());
            mySig.update(this.userName.getBytes());

            userSign = new String(mySig.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    }
}
