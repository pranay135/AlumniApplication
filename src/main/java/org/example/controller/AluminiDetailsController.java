package org.example.controller;

import jakarta.validation.Valid;
import org.example.constants.AluminiDetailsConstants;
import org.example.dto.AluminiDetailsDto;
import org.example.service.IAluminiDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AluminiDetailsConstants.BASE_ENDPOINT)
public class AluminiDetailsController {

    @Autowired
    IAluminiDetailsService iAluminiDetailsService;


    @PostMapping(AluminiDetailsConstants.ADD_ALUMINI)
    public ResponseEntity<?> addAluminiDetails(@Valid @RequestBody AluminiDetailsDto aluminiDetailsDTO, @PathVariable Long userId){
        iAluminiDetailsService.addAluminiDetails(aluminiDetailsDTO, userId);
      return new ResponseEntity<>("Alumini Details Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping(AluminiDetailsConstants.GET_ALL_ALUMINI)
    public ResponseEntity<List<AluminiDetailsDto>> getAllAluminiDetails(){
        return ResponseEntity.ok(iAluminiDetailsService.getAllAluminiDetails());
    }

    @DeleteMapping(AluminiDetailsConstants.DELETE_ALUMINI)
    public ResponseEntity<?> deleteAluminiDetails(@PathVariable Long id){
       iAluminiDetailsService.deleteAluminiDetails(id);
       return new ResponseEntity<>("Alumini Record Deleted", HttpStatus.NO_CONTENT);
    }

    @PatchMapping(AluminiDetailsConstants.UPDATE_ALUMINI)
    public ResponseEntity<?> updateAluminiDetails(@PathVariable Long id, @RequestBody Map<String, Object> fields){
        iAluminiDetailsService.updateAluminiDetails(id, fields);
        return new ResponseEntity<>("Record Updated Successfully", HttpStatus.OK);
    }

    @GetMapping(AluminiDetailsConstants.GET_BY_ID)
    public ResponseEntity<AluminiDetailsDto> getAluminiDetailsById(@PathVariable Long id){
        return ResponseEntity.ok(iAluminiDetailsService.getAluminiDetailsById(id));
    }

    @GetMapping(AluminiDetailsConstants.GET_STATS)
    public ResponseEntity<?> getSpecializationStats(){
        return ResponseEntity.ok(iAluminiDetailsService.getProfessionStats());
    }

    @GetMapping(AluminiDetailsConstants.SEARCH_ALUMINI_DETAILS)
    public ResponseEntity<List<AluminiDetailsDto>> searchAluminiDetails(@RequestParam(value = "search", required = false) String search,
     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize)
    {
        return ResponseEntity.ok(iAluminiDetailsService.searchAluminiDetails(search, pageNumber, pageSize));
    }

}
