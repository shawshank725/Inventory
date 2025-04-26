package bro.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUser {

    @NotNull(message = "username is required")
    @Size(min = 3, message = "username must be greater than 2 characters")
    private String username;

    private String firstname;
    private String lastname;

    @NotNull(message = "password is required")
    @Size(min = 8, message = "password must be greater than 8 characters")
    private String password;

    @NotNull(message = "email is required")
    @Size(min = 3, message = "email must be valid")
    private String email;

    private String profilePhoto = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/2048px-Default_pfp.svg.png";

    @Override
    public String toString() {
        return "NewUser{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
