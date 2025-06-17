package org.example.mapper;

import org.example.dto.AluminiDetailsDto;
import org.example.dto.UsersDto;
import org.example.entity.AluminiDetails;


public class AluminiDetailsMapper {

  public static AluminiDetails toAluminiDetails(AluminiDetailsDto aluminiDetailsDTO){
      AluminiDetails aluminiDetails = new AluminiDetails();

      aluminiDetails.setId(aluminiDetailsDTO.getId());
      aluminiDetails.setYearOfPassout(aluminiDetailsDTO.getYearOfPassout());
      aluminiDetails.setHighestQualification(aluminiDetailsDTO.getHighestQualification());
      aluminiDetails.setSpecialization(aluminiDetailsDTO.getSpecialization());
      aluminiDetails.setCollegeName(aluminiDetailsDTO.getCollegeName());
      aluminiDetails.setMaritalStatus(aluminiDetailsDTO.getMaritalStatus());
      aluminiDetails.setContactNumber(aluminiDetailsDTO.getContactNumber());
      aluminiDetails.setAddress(aluminiDetailsDTO.getAddress());
      aluminiDetails.setGender(aluminiDetailsDTO.getGender());
      aluminiDetails.setDescription(aluminiDetailsDTO.getDescription());
      aluminiDetails.setProfession(aluminiDetailsDTO.getProfession());
      aluminiDetails.setCity(aluminiDetailsDTO.getCity());
      aluminiDetails.setState(aluminiDetailsDTO.getState());
      aluminiDetails.setCountry(aluminiDetailsDTO.getCountry());
      aluminiDetails.setImageUrl(aluminiDetailsDTO.getImageUrl());
//      aluminiDetails.setUsers();
      return aluminiDetails;
  }

 public static AluminiDetailsDto toAluminiDetailsDto(AluminiDetails aluminiDetails){
      AluminiDetailsDto aluminiDetailsDTO = new AluminiDetailsDto();

      aluminiDetailsDTO.setId(aluminiDetails.getId());
      aluminiDetailsDTO.setYearOfPassout(aluminiDetails.getYearOfPassout());
      aluminiDetailsDTO.setHighestQualification(aluminiDetails.getHighestQualification());
      aluminiDetailsDTO.setSpecialization(aluminiDetails.getSpecialization());
      aluminiDetailsDTO.setCollegeName(aluminiDetails.getCollegeName());
      aluminiDetailsDTO.setMaritalStatus(aluminiDetails.getMaritalStatus());
      aluminiDetailsDTO.setContactNumber(aluminiDetails.getContactNumber());
      aluminiDetailsDTO.setAddress(aluminiDetails.getAddress());
      aluminiDetailsDTO.setGender(aluminiDetails.getGender());
      aluminiDetailsDTO.setDescription(aluminiDetails.getDescription());
      aluminiDetailsDTO.setProfession(aluminiDetails.getProfession());
      aluminiDetailsDTO.setCity(aluminiDetails.getCity());
      aluminiDetailsDTO.setState(aluminiDetails.getState());
      aluminiDetailsDTO.setCountry(aluminiDetails.getCountry());
      aluminiDetailsDTO.setImageUrl(aluminiDetails.getImageUrl());
      return aluminiDetailsDTO;
 }

}
