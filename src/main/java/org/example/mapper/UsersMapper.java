package org.example.mapper;

import org.example.dto.UsersDto;
import org.example.entity.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UsersMapper {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public static UsersDto toUserDto(Users users){
        UsersDto usersDto = new UsersDto();
        usersDto.setFirstName(users.getFirstName());
        usersDto.setLastName(users.getLastName());
        usersDto.setEmailId(users.getEmailId());
//        userDto.setPassword(users.getPassword());
        return usersDto;
    }

    public static Users toUserEntity(UsersDto usersDto){
        Users users = new Users();
        users.setFirstName(usersDto.getFirstName());
        users.setLastName(usersDto.getLastName());
        users.setEmailId(usersDto.getEmailId());
//        users.setPassword(encoder.encode(userDto.getPassword()));
        return users;
    }

}
