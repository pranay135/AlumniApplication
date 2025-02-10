package org.example.service.impl;

import org.example.customExceptions.UserNotFoundException;
import org.example.dto.AluminiDetailsDto;
import org.example.entity.AluminiDetails;
import org.example.entity.Users;
import org.example.mapper.AluminiDetailsMapper;
import org.example.mapper.UserMapper;
import org.example.repository.IAluminiRepository;
import org.example.repository.IUserRepository;
import org.example.service.IAluminiDetailsService;
import org.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

import static org.example.mapper.AluminiDetailsMapper.toAluminiDetails;

@Service
public class AluminiDetailsServiceImpl implements IAluminiDetailsService {

    @Autowired
    IAluminiRepository iAluminiRepository;


    @Override
    public void addAluminiDetails(AluminiDetailsDto aluminiDetailsDTO) {
        AluminiDetails aluminiDetails = toAluminiDetails(aluminiDetailsDTO);
        iAluminiRepository.save(aluminiDetails);
    }

    //    @Override
//    public List<AluminiDetailsDTO> getAllAluminiDetails() {
//       List<AluminiDetails> aluminiDetailsList = iAluminiRepository.findAll();
//       List<AluminiDetailsDTO> aluminiDetailsDTOList = convertAluminiDetailsEntityListToAluminiDetailsDtoList(aluminiDetailsList);
//        return aluminiDetailsDTOList;
//    }

    public static List<AluminiDetailsDto> convertAluminiDetailsEntityListToAluminiDetailsDtoList(List<AluminiDetails> aluminiDetailsList){
     List<AluminiDetailsDto> aluminiDetailsDtoList = new ArrayList<>();
     for(AluminiDetails aluminiDetails : aluminiDetailsList){
       aluminiDetailsDtoList.add(AluminiDetailsMapper.toAluminiDetailsDto(aluminiDetails));
     }
       return aluminiDetailsDtoList;
   }

    @Override
    public void deleteAluminiDetails(Long id) {
     Optional<AluminiDetails> optionalAluminiDetails = iAluminiRepository.findById(id);
     if(optionalAluminiDetails.isPresent())
     {
         iAluminiRepository.deleteById(id);
     }
        else {
            throw new UserNotFoundException("User Not Found With Entered ID : ", "404" + id);
     }
    }

    @Override
    public AluminiDetails updateAluminiDetails(Long id, Map<String, Object> fields) {
     Optional<AluminiDetails> existingAluminiDetails = iAluminiRepository.findById(id);

               if(existingAluminiDetails.isPresent()) {
                   fields.forEach((key, value) -> {
                       Field field = ReflectionUtils.findField(AluminiDetails.class, key);
                       field.setAccessible(true);
                       ReflectionUtils.setField(field, existingAluminiDetails.get(), value);
                   });
                   return iAluminiRepository.save(existingAluminiDetails.get());
               }
               else
               {
                   throw new UserNotFoundException("User Not Found With Entered ID : ", "404" + id);
               }

    }

//    @Override
//    public AluminiDetailsDto getAluminiDetailsById(Long id) {
//        AluminiDetails aluminiDetails = iAluminiRepository.findById(id)
//        .orElseThrow(() -> new UserNotFoundException("User Not Found With Entered ID : ", "404" + id))  ;
//
//        AluminiDetailsDto aluminiDetailsDTO = toAluminiDetailsDto(aluminiDetails);
//        return aluminiDetailsDTO;
//    }

    @Override
    public Map<String, Integer> getProfessionStats() {
        Map<String,Integer> lcDataBuffer = new HashMap<>();
        List<AluminiDetails> aluminiDetailsList = iAluminiRepository.findAll();
        if(!aluminiDetailsList.isEmpty())
        {
            for (AluminiDetails target : aluminiDetailsList)
            {
               if(!lcDataBuffer.containsKey(target.getProfession()))
               {
                   lcDataBuffer.put(target.getProfession(),1);
               }
               else{
                   int counter = lcDataBuffer.get(target.getProfession());
                   lcDataBuffer.put(target.getProfession(),++counter);
               }
            }
        }
          return lcDataBuffer;
    }


    @Override
    public List<AluminiDetailsDto> searchAluminiDetails(String search, Integer pageNumber, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<AluminiDetails> aluminiDetailsList =  iAluminiRepository.searchAluminiDetails(search, pageable);
//        List<AluminiDetails> detailsList = aluminiDetailsList.getContent();
//        List<AluminiDetailsDto> aluminiDetailsDtoList = convertAluminiDetailsEntityListToAluminiDetailsDtoList(detailsList);
//     return aluminiDetailsDtoList;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Optional<String> stringOptional = Optional.ofNullable(search);
        if(stringOptional.isPresent()) {
            Page<AluminiDetails> aluminiDetailsList = iAluminiRepository.searchAluminiDetails(search, pageable);
            List<AluminiDetails> detailsList = aluminiDetailsList.getContent();
            List<AluminiDetailsDto> aluminiDetailsDtoList = convertAluminiDetailsEntityListToAluminiDetailsDtoList(detailsList);
            return aluminiDetailsDtoList;
        }
            Page<AluminiDetails> aluminiDetailsPage = iAluminiRepository.getlistOfAluminiDetails(pageable);
            List<AluminiDetails> aluminiDetailsList = aluminiDetailsPage.getContent();
            List<AluminiDetailsDto> aluminiDetailsDtoList = convertAluminiDetailsEntityListToAluminiDetailsDtoList(aluminiDetailsList);
            return aluminiDetailsDtoList;
    }
}
