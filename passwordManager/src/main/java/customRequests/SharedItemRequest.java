package customRequests;

import cryptography.asymmericCipers.RSA;
import cryptography.signatures.UserSign;

import java.security.*;

public class SharedItemRequest extends Request<SharedItemRequest> {


    String message;

    String userSharedName;


    Long itemID;

    public SharedItemRequest(String message, String userSharedName, Long itemID) {
        this.message = message;
        this.userSharedName = userSharedName;
        this.itemID = itemID;
    }

    public SharedItemRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserSharedName() {
        return userSharedName;
    }

    public void setUserSharedName(String userSharedName) {
        this.userSharedName = userSharedName;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }


    public SharedItemRequest encrypt(PublicKey key) {
        SharedItemRequest result = new SharedItemRequest();

        result.setUserId(this.userId);
        result.setItemID(this.itemID);
        result.setUserSharedName(RSA.encrypt(this.userSharedName, key));
        result.setMessage(RSA.encrypt(this.message, key));
        return result;
    }

    @Override
    public SharedItemRequest decrypt(PrivateKey key) {
        return null;
    }

    @Override
    public void signature(PrivateKey key) {
        try {
            Signature mySig = Signature.getInstance("SHA1withRSA");
            mySig.initSign(key);

            mySig.update(this.message.getBytes());
            mySig.update(this.userSharedName.getBytes());


            userSign = new String(mySig.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "SharedItemRequest{" +
                "message='" + message + '\'' +
                ", userSharedName='" + userSharedName + '\'' +
                ", itemID=" + itemID +
                ", userId=" + userId +
                '}';
    }
}
