package org.example.service.impl;

import org.example.entity.UserPrinciple;
import org.example.entity.Users;
import org.example.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
   IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Users users = iUserRepository.findByEmailId(emailId)
       .orElseThrow(() -> new RuntimeException("User not found with this email: " + emailId));
//        if (users == null) {
//            System.out.println("User Not Found");
//            throw new UsernameNotFoundException("user not found");
//        }
        return new UserPrinciple(users);
    }
}
