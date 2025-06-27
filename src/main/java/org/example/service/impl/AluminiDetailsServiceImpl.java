package org.example.service.impl;

import jakarta.persistence.EntityManager;
import org.example.customExceptions.UserNotFoundException;
import org.example.dto.AluminiDetailsDto;
import org.example.dto.UsersDto;
import org.example.entity.AluminiDetails;
import org.example.entity.Users;
import org.example.repository.IAluminiRepository;
import org.example.repository.IUsersRepository;
import org.example.service.IAluminiDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AluminiDetailsServiceImpl implements IAluminiDetailsService {

    @Autowired
    IAluminiRepository iAluminiRepository;

    @Autowired
    IUsersRepository iUsersRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public AluminiDetailsDto addAluminiDetails(AluminiDetailsDto aluminiDetailsDTO, Long userId) {
        Optional<Users> optionalUsers = iUsersRepository.findById(userId);
        if(optionalUsers.isPresent()){
            Users users = optionalUsers.get();
            UsersDto usersDto = modelMapper.map(users, UsersDto.class);
            aluminiDetailsDTO.setUsers(usersDto);
            AluminiDetails aluminiDetails = this.modelMapper.map(aluminiDetailsDTO, AluminiDetails.class);
            iAluminiRepository.save(aluminiDetails);
            AluminiDetailsDto aluminiDetailsDto = this.modelMapper.map(aluminiDetails, AluminiDetailsDto.class);
            return aluminiDetailsDto;

        }
        else {
            throw new UserNotFoundException("User Not Found With Entered ID : ", "404" + userId);
        }
    }


        @Override
    public List<AluminiDetailsDto> getAllAluminiDetails() {
        List<AluminiDetails> aluminiDetailsList = iAluminiRepository.findAll();
        List<AluminiDetailsDto> aluminiDetailsDtoList =
        aluminiDetailsList.stream().map(aluminiDetails -> modelMapper.map(aluminiDetails,
        AluminiDetailsDto.class)).collect(Collectors.toList());
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

    @Override
    public AluminiDetailsDto getAluminiDetailsById(Long id) {
        AluminiDetails aluminiDetails = iAluminiRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User Not Found With Entered ID : ", "404" + id))  ;

        AluminiDetailsDto aluminiDetailsDTO = this.modelMapper.map(aluminiDetails, AluminiDetailsDto.class);
        return aluminiDetailsDTO;
    }

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
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Optional<String> stringOptional = Optional.ofNullable(search);
        if(stringOptional.isPresent()) {
            Page<AluminiDetails> aluminiDetailsList = iAluminiRepository.searchAluminiDetails(search, pageable);
            List<AluminiDetails> detailsList = aluminiDetailsList.getContent();
            List<AluminiDetailsDto> aluminiDetailsDtoList = detailsList.stream().map(
            aluminiDetails -> modelMapper.map(aluminiDetails, AluminiDetailsDto.class))
            .collect(Collectors.toList());
             return aluminiDetailsDtoList;

        }
            Page<AluminiDetails> aluminiDetailsPage = iAluminiRepository.getlistOfAluminiDetails(pageable);
            List<AluminiDetails> aluminiDetailsList = aluminiDetailsPage.getContent();
            List<AluminiDetailsDto> aluminiDetailsDtoList = aluminiDetailsList.stream().map(
            aluminiDetails -> modelMapper.map(aluminiDetails, AluminiDetailsDto.class))
            .collect(Collectors.toList());
            return aluminiDetailsDtoList;

    }
}
