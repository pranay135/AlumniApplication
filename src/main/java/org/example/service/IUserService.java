package org.example.service;

import org.example.dto.LoginDto;
import org.example.dto.UserDto;

public interface IUserService {

    void userRegistration(UserDto userDto);

//    String verify(LoginDto loginDto);

    void sendOtp(String emailId);

    String verifyAccount(String emailId, String otp);

//    String regenerateOtp(String emailId);

}
