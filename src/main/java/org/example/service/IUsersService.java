package org.example.service;

import org.example.dto.UsersDto;

public interface IUsersService {

    void userRegistration(UsersDto usersDto);

//    String verify(LoginDto loginDto);

    void sendOtp(String emailId);

    String verifyAccount(String emailId, String otp);

//    String regenerateOtp(String emailId);

}
