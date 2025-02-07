package org.example.service;


import org.example.dto.AluminiDetailsDto;
import org.example.entity.AluminiDetails;

import java.util.List;
import java.util.Map;

public interface IAluminiDetailsService {

    void addAluminiDetails(AluminiDetailsDto aluminiDetailsDTO);

//    List<AluminiDetailsDTO> getAllAluminiDetails();

    void deleteAluminiDetails(Long id);

    AluminiDetails updateAluminiDetails(Long id, Map<String, Object> fields);

//    AluminiDetailsDto getAluminiDetailsById(Long id);

    Map<String, Integer> getProfessionStats();

    List<AluminiDetailsDto> searchAluminiDetails(String search, Integer pageNumber, Integer pageSize);

}
