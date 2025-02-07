package org.example.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.example.utility.Gender;
import org.example.utility.MaritalStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AluminiDetailsDto {

    private long id;
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
