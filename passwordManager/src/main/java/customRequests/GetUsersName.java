package customRequests;

import java.security.PrivateKey;
import java.security.PublicKey;

public class GetUsersName extends Request<GetUsersName> {





    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public GetUsersName encrypt(PublicKey key) {
        return null;
    }

    @Override
    public GetUsersName decrypt(PrivateKey key) {
        return null;
    }

    @Override
    public void signature(PrivateKey key) {

    }

    public GetUsersName(Long userId) {
        this.userId = userId;
    }

    public GetUsersName() {
    }
}
