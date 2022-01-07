package customRequests;

import cryptography.asymmericCipers.RSA;

import java.security.PrivateKey;
import java.security.PublicKey;

public class GetItemRequest extends Request<GetItemRequest> {



    private Long itemId;

    public GetItemRequest() {
    }

    public GetItemRequest(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "GetItemRequest{" +
                "userId=" + userId +
                ", ItemId=" + itemId +
                '}';
    }

    public GetItemRequest encrypt(PublicKey key) {
        GetItemRequest result = new GetItemRequest();

        result.setItemId(Long.valueOf(RSA.encrypt(String.valueOf(this.itemId), key)));

        result.setUserId(Long.valueOf(RSA.encrypt(String.valueOf(this.userId), key)));

        return result;
    }

    public GetItemRequest decrypt(PrivateKey key) {

        return null;
    }

    @Override
    public void signature(PrivateKey key) {

    }


}
