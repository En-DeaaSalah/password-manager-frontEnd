package customResponses;


import cryptography.asymmericCipers.RSA;
import model.User;

import java.io.Serializable;
import java.security.PrivateKey;

public class RegistrationResponse implements Serializable {


    public User user;

    public String message;


    public RegistrationResponse(User user, String message) {
        this.user = user;
        this.message = message;

    }


    public RegistrationResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegistrationResponse decrypt(PrivateKey key) {
        RegistrationResponse result = new RegistrationResponse();


        result.setUser(user.decrypt(key));
//        result.setMessage(RSA.decrypt(this.message, key));
        return result;
    }


    @Override
    public String toString() {
        return "RegistrationResponse{" +
                "user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}
