package customRequests;

import cryptography.asymmericCipers.RSA;

import java.security.PrivateKey;
import java.security.PublicKey;

public class DeleteItemRequest extends Request<DeleteItemRequest> {



    private Long ItemId;

    public DeleteItemRequest() {
    }

    public DeleteItemRequest(Long userId, Long itemId) {
        this.userId = userId;
        ItemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return ItemId;
    }

    public void setItemId(Long itemId) {
        ItemId = itemId;
    }

    @Override
    public String toString() {
        return "DeleteItemRequest{" +
                "userId=" + userId +
                ", ItemId=" + ItemId +
                '}';
    }



    public DeleteItemRequest encrypt(PublicKey key) {

        DeleteItemRequest result = new DeleteItemRequest();

        result.setItemId(Long.valueOf(RSA.encrypt(String.valueOf(this.ItemId), key)));

        result.setUserId(Long.valueOf(RSA.encrypt(String.valueOf(this.userId), key)));
        return result;
    }


    public DeleteItemRequest decrypt(PrivateKey key) {



        return null;

    }

    @Override
    public void signature(PrivateKey key) {

    }


}
