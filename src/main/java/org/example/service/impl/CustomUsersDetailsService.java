package org.example.service.impl;

import org.example.entity.UserPrinciple;
import org.example.entity.Users;
import org.example.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUsersDetailsService implements UserDetailsService {

   @Autowired
   IUsersRepository iUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Users users = iUsersRepository.findByEmailId(emailId)
       .orElseThrow(() -> new RuntimeException("User not found with this email: " + emailId));
//        if (users == null) {
//            System.out.println("User Not Found");
//            throw new UsernameNotFoundException("user not found");
//        }
        return new UserPrinciple(users);
    }
}
