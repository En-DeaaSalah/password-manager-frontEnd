package customResponses;

import cryptography.asymmericCipers.RSA;
import model.Record;

import java.io.Serializable;
import java.security.PrivateKey;

public class AddItemResponse implements Serializable {

    public Long id;

    public String title;

    public String email;

    public String password;

    public String description;

    public String message;

    public AddItemResponse(Long id, String title, String email, String password, String description, String message) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.password = password;
        this.description = description;
        this.message = message;
    }

    public AddItemResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public AddItemResponse decrypt(PrivateKey key) {
        AddItemResponse result = new AddItemResponse();

        result.setId(this.id);
        result.setDescription(RSA.decrypt(this.description, key));
        result.setTitle(RSA.decrypt(this.title, key));
        result.setEmail(RSA.decrypt(this.email, key));
        result.setPassword(RSA.decrypt(this.password, key));
        return result;
    }


    @Override
    public String toString() {
        return "AddItemResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
