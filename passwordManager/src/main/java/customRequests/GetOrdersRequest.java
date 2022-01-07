package customRequests;

import java.security.PrivateKey;
import java.security.PublicKey;

public class GetOrdersRequest extends Request<GetOrdersRequest> {
    @Override
    public GetOrdersRequest encrypt(PublicKey key) {
        return null;
    }

    @Override
    public GetOrdersRequest decrypt(PrivateKey key) {
        return null;
    }

    @Override
    public void signature(PrivateKey key) {

    }


    public GetOrdersRequest(Long userId) {

        this.userId=userId;
    }
}
