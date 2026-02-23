package models;

public class UserInsertRequest {

    private String id;
    private String email;
    private String name;
    private String phone;
    private String role;

    public UserInsertRequest(String id, String email, String name, String phone, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}
