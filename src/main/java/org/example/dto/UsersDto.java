package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsersDto {

    private long id;
    @NotNull(message = "First Name Cannot Be Blank")
    private String firstName;
    @NotNull(message = "Last Name Cannot Be Blank")
    private String lastName;
    @Email
    private String emailId;
//    @NotNull
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",
//    message = "Password should contain minimum 8 character, 1 uppercase, 1 lowercase, 1 digit & 1 special character")
//    private String password;
}
