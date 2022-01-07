package model;

import cryptography.asymmericCipers.RSA;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Record {

    private Long id;

    private String title;

    private String email;

    private String password;

    private String description;


    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Record(Long id, String title, String email, String password, String description) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public Record() {
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


    public void decrypt(PrivateKey key) {


        this.setDescription(RSA.decrypt(this.description, key));
        this.setTitle(RSA.decrypt(this.title, key));
        this.setEmail(RSA.decrypt(this.email, key));
        this.setPassword(RSA.decrypt(this.password, key));

    }


    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
