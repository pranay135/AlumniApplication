package org.example.customExceptions;


import lombok.*;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
public class UserAlreadyExistException extends RuntimeException {
    private String errorMessage;
    private String errorCode;
}
