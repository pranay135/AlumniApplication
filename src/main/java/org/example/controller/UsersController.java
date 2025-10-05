package org.example.controller;

import jakarta.validation.Valid;
import org.example.constants.UsersConstants;
import org.example.dto.LoginDto;
import org.example.dto.UsersDto;
import org.example.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(UsersConstants.BASE_ENDPOINT)
public class UsersController {

    @Autowired
    IUsersService iUsersService;



    @PostMapping(UsersConstants.REGISTRATION_ALUMINI)
    public ResponseEntity<?> userRegistration(@RequestBody @Valid UsersDto usersDto){
            iUsersService.userRegistration(usersDto);
            return new ResponseEntity<>("User Registered Successfully ", HttpStatus.CREATED);
    }

    @PostMapping(UsersConstants.LOGIN_ALUMINI)
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(iUsersService.verify(loginDto));
    }

    @PostMapping(UsersConstants.SEND_OTP)
    public ResponseEntity<?> sendOtpToEmail(@Valid  @PathVariable("emailId") String emailId ){
        iUsersService.sendOtp(emailId);
        return new ResponseEntity<>("OTP Sent Successfully ", HttpStatus.OK);
    }


    @PutMapping(UsersConstants.VERIFY_ACCOUNT)
    public ResponseEntity<?> verifyAccount(@RequestParam String emailId, @RequestParam String otp){
        String verified = iUsersService.verifyAccount(emailId, otp);
        return new ResponseEntity<>(verified, HttpStatus.ACCEPTED);
    }


    @PutMapping(UsersConstants.REGENERATE_OTP)
    public ResponseEntity<String> regenerateOtp(@RequestParam String emailId) {
        return new ResponseEntity<>(iUsersService.regenerateOtp(emailId), HttpStatus.OK);
    }
}
