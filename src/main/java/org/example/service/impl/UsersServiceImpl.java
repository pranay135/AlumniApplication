package org.example.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.customExceptions.OtpDoesNotMatchException;
import org.example.customExceptions.UserAlreadyExistException;
import org.example.customExceptions.UserNotFoundException;
import org.example.dto.UsersDto;
import org.example.entity.Users;
import org.example.repository.IUsersRepository;
import org.example.service.IUsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;


@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUsersRepository iUsersRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void userRegistration(UsersDto usersDto) {
        if(emailIdExists(usersDto.getEmailId())){
            throw new UserAlreadyExistException("User Already Exist With Entered EmailId", "409 : "  + usersDto.getEmailId());
        }
        Users users = this.modelMapper.map(usersDto, Users.class);
        iUsersRepository.save(users);
    }

    private boolean emailIdExists(String emailId){
        return iUsersRepository.findByEmailId(emailId).isPresent();
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
        Users users = iUsersRepository.findByEmailId(emailId)
       .orElseThrow(() -> new UserNotFoundException("User Not Found With This EmailId", "404" + emailId));
        try{
            String otp = generateOtp();
            sendOtpToEmail(emailId, otp);
            users.setOtp(otp);
            users.setOtpGeneratedTime(LocalDateTime.now());
            iUsersRepository.save(users);
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
         Users users = iUsersRepository.findByEmailId(emailId)
        .orElseThrow(() -> new UserNotFoundException("User Not Found With This EmailId", "404" + emailId));

         if(users.getOtp().equals(otp) && Duration.between(users.getOtpGeneratedTime(),
            LocalDateTime.now()).getSeconds() < (10 * 60))
         {
             users.setActive(true);
             iUsersRepository.save(users);
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