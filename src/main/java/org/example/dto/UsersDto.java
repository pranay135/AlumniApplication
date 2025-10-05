package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsersDto {

    private long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
}
