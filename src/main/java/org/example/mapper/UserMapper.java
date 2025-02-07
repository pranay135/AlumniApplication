package org.example.mapper;

import org.example.dto.UserDto;
import org.example.entity.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserMapper {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public static UserDto toUserDto(Users users){
        UserDto userDto = new UserDto();
        userDto.setFirstName(users.getFirstName());
        userDto.setLastName(users.getLastName());
        userDto.setEmailId(users.getEmailId());
//        userDto.setPassword(users.getPassword());
        return userDto;
    }

    public static Users toUserEntity(UserDto userDto){
        Users users = new Users();
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setEmailId(userDto.getEmailId());
//        users.setPassword(encoder.encode(userDto.getPassword()));
        return users;
    }

}
