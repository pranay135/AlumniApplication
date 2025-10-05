package org.example.dto;

import lombok.*;
import org.example.utility.Gender;
import org.example.utility.MaritalStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AluminiDetailsDto {

    private long id;
    private UsersDto users;
    private String yearOfPassout;
    private String highestQualification;
    private String specialization;
    private String collegeName;
    private MaritalStatus maritalStatus;
    private String contactNumber;
    private String address;
    private Gender gender;
    private String description;
    private String profession;
    private String city;
    private String state;
    private String country;
    private String imageUrl;
}
