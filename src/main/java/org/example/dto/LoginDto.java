package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginDto {
    private String emailId;
    private String password;
}
