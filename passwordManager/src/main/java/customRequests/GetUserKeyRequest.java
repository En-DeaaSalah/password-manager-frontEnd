package customRequests;

import cryptography.asymmericCipers.RSA;
import cryptography.signatures.UserSign;

import java.security.*;

public class GetUserKeyRequest extends Request<GetUserKeyRequest> {


    String userName;


    public GetUserKeyRequest(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public GetUserKeyRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GetUserKeyRequest encrypt(PublicKey key) {

        GetUserKeyRequest result = new GetUserKeyRequest();

        result.setUserId(this.userId);
        result.setUserName(RSA.encrypt(this.getUserName(), key));

        return result;
    }

    @Override
    public GetUserKeyRequest decrypt(PrivateKey key) {
        return null;
    }

    @Override
    public void signature(PrivateKey key) {
        try {
            Signature mySig = Signature.getInstance("SHA1withRSA");
            mySig.initSign(key);
            mySig.update(this.getUserName().getBytes());
            userSign = new String(mySig.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "GetUserKeyRequest{" +
                "UserName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
