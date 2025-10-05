package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomUserDto {

    private String firstName;
    private String lastName;
    private String emailId;
}
