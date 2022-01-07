package customResponses;


import java.io.Serializable;

public class SharedOrderResponce {


    private Long id;

    private String title;

    private String email;

    private String password;

    private String description;


    private String message;


    public SharedOrderResponce(Long id, String title, String email, String password, String description, String message) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.password = password;
        this.description = description;
        this.message = message;
    }


    public SharedOrderResponce() {
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


    @Override
    public String toString() {
        return "OrdersList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
