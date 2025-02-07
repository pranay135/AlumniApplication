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


    // ENDPOINT : localhost:8080/api/v1/alumini/add
    @PostMapping(AluminiDetailsConstants.ADD_ALUMINI)
    public ResponseEntity<?> addAluminiDetails(@Valid @RequestBody AluminiDetailsDto aluminiDetailsDTO){
        iAluminiDetailsService.addAluminiDetails(aluminiDetailsDTO);
      return new ResponseEntity<>("Alumini Details Added Successfully", HttpStatus.CREATED);
    }

//    // ENDPOINT : localhost:8080/api/v1/alumini/get/all
//    @GetMapping(AluminiDetailsConstants.GET_ALL_ALUMINI)
//    public ResponseEntity<List<AluminiDetailsDTO>> getAllAluminiDetails(){
//        return ResponseEntity.ok(iAluminiDetailsService.getAllAluminiDetails());
//    }

    // ENDPOINT : localhost:8080/api/v1/alumini/delete/{id}
    @DeleteMapping(AluminiDetailsConstants.DELETE_ALUMINI)
    public ResponseEntity<?> deleteAluminiDetails(@PathVariable Long id){
       iAluminiDetailsService.deleteAluminiDetails(id);
       return new ResponseEntity<>("Alumini Record Deleted", HttpStatus.NO_CONTENT);
    }

    // ENDPOINT : localhost:8080/api/v1/alumini/update/{id}
    @PatchMapping(AluminiDetailsConstants.UPDATE_ALUMINI)
    public ResponseEntity<?> updateAluminiDetails(@PathVariable Long id, @RequestBody Map<String, Object> fields){
        iAluminiDetailsService.updateAluminiDetails(id, fields);
        return new ResponseEntity<>("Record Updated Successfully", HttpStatus.OK);
    }

//    // ENDPOINT : localhost:8080/api/v1/alumini/get/{id}
//    @GetMapping(AluminiDetailsConstants.GET_BY_ID)
//    public ResponseEntity<AluminiDetailsDto> getAluminiDetailsById(@PathVariable Long id){
//        return ResponseEntity.ok(iAluminiDetailsService.getAluminiDetailsById(id));
//    }

    // ENDPOINT : localhost:8080/api/v1/alumini/get/stats
    @GetMapping(AluminiDetailsConstants.GET_STATS)
    public ResponseEntity<?> getSpecializationStats(){
        return ResponseEntity.ok(iAluminiDetailsService.getProfessionStats());
    }

//     ENDPOINT : localhost:8080/api/v1/alumini/search?keyword=IT
    @GetMapping(AluminiDetailsConstants.SEARCH_ALUMINI_DETAILS)
    public ResponseEntity<List<AluminiDetailsDto>> searchAluminiDetails(@RequestParam(value = "search", required = false) String search,
     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize)
    {
        return ResponseEntity.ok(iAluminiDetailsService.searchAluminiDetails(search, pageNumber, pageSize));
    }

}
