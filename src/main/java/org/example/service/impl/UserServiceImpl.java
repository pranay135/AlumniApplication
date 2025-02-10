package org.example.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.customExceptions.OtpDoesNotMatchException;
import org.example.customExceptions.UserAlreadyExistException;
import org.example.customExceptions.UserNotFoundException;
import org.example.dto.UserDto;
import org.example.entity.AluminiDetails;
import org.example.entity.Users;
import org.example.repository.IUserRepository;
import org.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;


import static org.example.mapper.UserMapper.toUserEntity;
import static org.example.mapper.AluminiDetailsMapper.toAluminiDetails;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;



    @Override
    public void userRegistration(UserDto userDto) {
        if(emailIdExists(userDto.getEmailId())){
            throw new UserAlreadyExistException("User Already Exist With Entered EmailId", "409 : "  + userDto.getEmailId());
        }
        Users users1 = toUserEntity(userDto);
        iUserRepository.save(users1);
    }

    private boolean emailIdExists(String emailId){
        return iUserRepository.findByEmailId(emailId).isPresent();
    }


//    @Override
//    public String verify(LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailId(), loginDto.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(loginDto.getEmailId());
//        } else {
//            return "fail";
//        }
//    }


    @Override
    public void sendOtp(String emailId) {
        Users users = iUserRepository.findByEmailId(emailId)
       .orElseThrow(() -> new UserNotFoundException("User Not Found With This EmailId", "404" + emailId));
        try{
            String otp = generateOtp();
            sendOtpToEmail(emailId, otp);
            users.setOtp(otp);
            users.setOtpGeneratedTime(LocalDateTime.now());
            iUserRepository.save(users);
        }
        catch (MessagingException messagingException){
            throw new RuntimeException("unable to send otp");
        }
    }

    public String generateOtp(){
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtpToEmail(String emailId, String otp) throws MessagingException{
//       MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//       MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//       mimeMessageHelper.setTo(emailId);
//       mimeMessageHelper.setSubject("Your OTP is :  ");
//       mimeMessageHelper.setText(otp);
//         try {
             Context context = new Context();
             context.setVariable("otp", otp);
             String emailContent = templateEngine.process("otp", context);
             MimeMessage mimeMessage = javaMailSender.createMimeMessage();
             MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
             mimeMessageHelper.setTo(emailId);
             mimeMessageHelper.setSubject("Your OTP is :  ");
             mimeMessageHelper.setText(emailContent, true);
//             try(var inputStream = Objects.requireNonNull(UserServiceImpl.class.getResourceAsStream("/template/otp.html"))){
//                 mimeMessageHelper.setText(
//                         new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), true
//                 );
//         }
             javaMailSender.send(mimeMessage);
//       } catch (IOException e) {
//           throw new RuntimeException(e);
//       }
    }

    @Override
    public String verifyAccount(String emailId, String otp) {
         Users users = iUserRepository.findByEmailId(emailId)
        .orElseThrow(() -> new UserNotFoundException("User Not Found With This EmailId", "404" + emailId));

         if(users.getOtp().equals(otp) && Duration.between(users.getOtpGeneratedTime(),
            LocalDateTime.now()).getSeconds() < (10 * 60))
         {
             users.setActive(true);
             iUserRepository.save(users);
             String token = jwtService.generateToken(emailId);
             return "OTP verified you can login \n JWT Token : " + token;
         }
         else {
             throw new OtpDoesNotMatchException("OTP Does Not Match", "401" + otp);
         }
//        return "Please regenerate otp and try again";
    }

//    @Override
//    public String regenerateOtp(String emailId) {
//        Users user = iUserRepository.findByEmailId(emailId)
//       .orElseThrow(() -> new RuntimeException("User not found with this email: " + emailId));
//        String otp = generateOtp();
//        try{
//            sendOtpToEmail(emailId, otp);
//        }catch (MessagingException e) {
//            throw new RuntimeException("Unable to send otp please try again");
//        }
//        user.setOtp(otp);
//        user.setOtpGeneratedTime(LocalDateTime.now());
//        iUserRepository.save(user);
//        return "Email sent... please verify account within 1 minute";
//    }

}