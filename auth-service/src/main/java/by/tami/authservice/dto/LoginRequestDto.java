package by.tami.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDto {

    @Email(message = "email is not valid format")
    @NotBlank(message = "email is required")
    private String email;

    @Size(min = 8, message = "password must be at least 8 characters long")
    @NotBlank(message = "password is required")
    private String password;

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
}
