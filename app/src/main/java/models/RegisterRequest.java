package models;

public class RegisterRequest {
    private String name;
    private String phone_number;
    private String email;
    private String password;

    public RegisterRequest(String name, String phone_number, String email, String password) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
    }
}