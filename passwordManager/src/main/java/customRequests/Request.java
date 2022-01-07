package customRequests;

import java.security.PrivateKey;
import java.security.PublicKey;

abstract public class Request<T> {


    public Long userId;

    String userSign;

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    abstract public T encrypt(PublicKey key);

    abstract public T decrypt(PrivateKey key);

    abstract public void signature(PrivateKey key);



}
