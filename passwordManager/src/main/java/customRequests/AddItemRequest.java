package customRequests;

import cryptography.asymmericCipers.RSA;
import cryptography.signatures.UserSign;

import java.security.*;

public class AddItemRequest extends Request<AddItemRequest> {


    private String title;

    private String email;

    private String password;

    private String description;


    public AddItemRequest(Long userId, String title, String email, String password, String description) {
        this.userId = userId;
        this.title = title;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public AddItemRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public AddItemRequest encrypt(PublicKey key) {
        AddItemRequest result = new AddItemRequest();

        result.setUserId(this.userId);
        result.setDescription(RSA.encrypt(this.description, key));
        result.setTitle(RSA.encrypt(this.title, key));
        result.setEmail(RSA.encrypt(this.email, key));
        result.setPassword(RSA.encrypt(this.password, key));
        return result;
    }


    public AddItemRequest decrypt(PrivateKey key) {

        return null;

    }

    @Override
    public void signature(PrivateKey key) {


        try {
            Signature mySig = Signature.getInstance("SHA1withRSA");
            mySig.initSign(key);
            mySig.update(this.description.getBytes());
            mySig.update(this.title.getBytes());
            mySig.update(this.email.getBytes());
            mySig.update(this.password.getBytes());
            userSign = new String(mySig.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }


    }


    @Override
    public String toString() {
        return "AddItemRequest{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
