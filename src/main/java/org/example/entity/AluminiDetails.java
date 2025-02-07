package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.utility.Gender;
import org.example.utility.MaritalStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "aluminiDetails")
public class AluminiDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    private String yearOfPassout;
    private String highestQualification;
    private String specialization;
    private String collegeName;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private String contactNumber;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String description;
    private String profession;
    private String city;
    private String state;
    private String country;
    private String imageUrl;
}
