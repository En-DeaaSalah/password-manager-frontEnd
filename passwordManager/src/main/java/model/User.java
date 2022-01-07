package model;

import cryptography.asymmericCipers.RSA;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class User {


    static private User user;

    public static User getUser() {

        if (user == null) {
            user = new User();
        }

        return user;
    }

    public static User getUser(Long id, String email, String password, String privateKey, String publicKey, List<Record> accounts) {

        if (user == null) {
            user = new User();
        }

        return user;
    }


    private Long id;

    private String name;

    private String email;

    private String password;

    private String privateKey;

    private String publicKey;


    private Boolean isLogin;

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    private List<Record> accounts;

    public User(Long id, String name, String email, String password, String privateKey, String publicKey, List<Record> accounts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.accounts = accounts;
    }

    private User() {
        accounts = new ArrayList<>();
    }


    public void addRecord(Record item) {

        accounts.add(item);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public List<Record> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Record> accounts) {
        this.accounts = accounts;
    }

    public static void setUser(User user) {
        User.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public User decrypt(PrivateKey key) {

        User user = new User();

        user.setName(RSA.decrypt(this.name, key));

        user.setId(this.id);

        user.setAccounts(this.accounts);

        user.setPassword(RSA.decrypt(this.password, key));

        user.setEmail(RSA.decrypt(this.email, key));

        return user;
    }


    @Override
    public String toString() {
        return "User{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
