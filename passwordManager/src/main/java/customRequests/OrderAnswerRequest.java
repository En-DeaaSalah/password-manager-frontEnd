package customRequests;

import cryptography.asymmericCipers.RSA;
import cryptography.signatures.UserSign;

import java.security.*;

public class OrderAnswerRequest extends Request<OrderAnswerRequest> {


    Long orderId;

    String answer;

    public OrderAnswerRequest(Long orderId, String answer) {
        this.orderId = orderId;
        this.answer = answer;
    }

    public OrderAnswerRequest() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public OrderAnswerRequest encrypt(PublicKey key) {
        OrderAnswerRequest result = new OrderAnswerRequest();

        result.setUserId(this.userId);
        result.setOrderId(this.orderId);
        result.setAnswer(RSA.encrypt(this.answer, key));
        return result;
    }

    @Override
    public OrderAnswerRequest decrypt(PrivateKey key) {
        return null;
    }

    @Override
    public void signature(PrivateKey key) {
        try {
            Signature mySig = Signature.getInstance("SHA1withRSA");
            mySig.initSign(key);

            mySig.update(this.answer.getBytes());
            userSign = new String(mySig.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "OrderAnswerRequest{" +
                "orderId=" + orderId +
                ", answer='" + answer + '\'' +
                ", userId=" + userId +
                '}';
    }
}
