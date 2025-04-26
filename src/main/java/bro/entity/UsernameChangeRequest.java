package bro.entity;

import jakarta.validation.constraints.NotBlank;
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
public class UsernameChangeRequest {

    @NotNull(message = "username cannot be empty")
    @Size(min=3 , message = "username should be greater than 3 letters")
    @NotBlank(message = "username cannot be empty")
    private String username;

    @NotNull(message = "password cannot be empty")
    @NotBlank(message = "password cannot be empty")
    private String password;

}
