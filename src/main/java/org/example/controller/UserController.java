package org.example.controller;

import jakarta.validation.Valid;
import org.example.constants.UserConstants;
import org.example.dto.LoginDto;
import org.example.dto.UserDto;
import org.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(UserConstants.BASE_ENDPOINT)
public class UserController {

    @Autowired
    IUserService iUserService;


    // ENDPOINT : localhost:8080/api/v1/auth/registration
    @PostMapping(UserConstants.REGISTRATION_ALUMINI)
    public ResponseEntity<?> userRegistration(@RequestBody @Valid UserDto userDto ){
            iUserService.userRegistration(userDto);
            return new ResponseEntity<>("User Registered Successfully ", HttpStatus.CREATED);
    }

//    @PostMapping(UserConstants.LOGIN_ALUMINI)
//    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
//        return ResponseEntity.ok(iUserService.verify(loginDto));
//    }

    // ENDPOINT : localhost:8080/api/v1/auth/sendOtp/{emailId}
    @PostMapping(UserConstants.SEND_OTP)
    public ResponseEntity<?> sendOtpToEmail(@Valid  @PathVariable("emailId") String emailId ){
        iUserService.sendOtp(emailId);
        return new ResponseEntity<>("OTP Sent Successfully ", HttpStatus.OK);
    }

    // ENDPOINT : localhost:8080/api/v1/auth/verify-account?emailId=test@gmail.com&otp=123456
    @PutMapping(UserConstants.VERIFY_ACCOUNT)
    public ResponseEntity<?> verifyAccount(@RequestParam String emailId, @RequestParam String otp){
        String verified = iUserService.verifyAccount(emailId, otp);
        return new ResponseEntity<>(verified, HttpStatus.ACCEPTED);
//        return ResponseEntity.ok( iUserService.verifyAccount(emailId, otp));

//        return new ResponseEntity<>("OTP Verified Successfully", HttpStatus.ACCEPTED);
    }

    // ENDPOINT : localhost:8080/api/v1/auth/regenerate-otp?emailId=test@gmail.com
//    @PutMapping(UserConstants.REGENERATE_OTP)
//    public ResponseEntity<String> regenerateOtp(@RequestParam String emailId) {
//        return new ResponseEntity<>(iUserService.regenerateOtp(emailId), HttpStatus.OK);
//    }
}
