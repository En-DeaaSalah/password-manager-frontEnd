package customRequests;

import cryptography.asymmericCipers.RSA;

import java.security.PrivateKey;
import java.security.PublicKey;

public class GetAllItemRequest extends Request<GetAllItemRequest> {



    public GetAllItemRequest(Long userId) {
        this.userId = userId;
    }

    public GetAllItemRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GetAllItemRequest{" +
                "userId=" + userId +
                '}';
    }


    public GetAllItemRequest encrypt(PublicKey key) {

        GetAllItemRequest result = new GetAllItemRequest();


        result.setUserId(Long.valueOf(RSA.encrypt(String.valueOf(this.userId), key)));

        return result;
    }

    public GetAllItemRequest decrypt(PrivateKey request) {
        return null;
    }

    @Override
    public void signature(PrivateKey key) {

    }


}
