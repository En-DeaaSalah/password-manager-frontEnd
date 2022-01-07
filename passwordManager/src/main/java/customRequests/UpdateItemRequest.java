package customRequests;

import cryptography.asymmericCipers.RSA;
import cryptography.signatures.UserSign;

import java.security.*;

public class UpdateItemRequest extends Request<UpdateItemRequest> {


    private Long itemId;


    private String title;

    private String email;

    private String password;

    private String description;

    public UpdateItemRequest(Long itemId, Long userId, String title, String email, String password, String description) {
        this.itemId = itemId;
        this.userId = userId;
        this.title = title;
        this.email = email;
        this.password = password;
        this.description = description;
    }


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }


    public UpdateItemRequest(Long userId, String title, String email, String password, String description) {
        this.userId = userId;
        this.title = title;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public UpdateItemRequest() {
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

    @Override
    public String toString() {
        return "UpdateItemRequest{" +
                "itemId=" + itemId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public UpdateItemRequest encrypt(PublicKey key) {
        UpdateItemRequest result = new UpdateItemRequest();

        result.setItemId(this.getItemId());
        result.setUserId(this.getUserId());
        result.setDescription(RSA.encrypt(this.description, key));
        result.setTitle(RSA.encrypt(this.title, key));
        result.setEmail(RSA.encrypt(this.email, key));
        result.setPassword(RSA.encrypt(this.password, key));
        return result;
    }

    public UpdateItemRequest decrypt(PrivateKey key) {
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
}
