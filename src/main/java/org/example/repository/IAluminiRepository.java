package org.example.repository;

import org.example.entity.AluminiDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface  IAluminiRepository extends JpaRepository<AluminiDetails, Long > {

  Optional<AluminiDetails> findById(Long id);

  @Query("SELECT a FROM AluminiDetails a WHERE " +
          "a.specialization LIKE CONCAT('%', :search, '%')"    +
          "Or a.collegeName LIKE CONCAT('%', :search, '%')"    +
          "Or a.contactNumber LIKE CONCAT('%', :search, '%')"  +
          "Or a.address LIKE CONCAT('%', :search, '%')"        +
          "Or a.description LIKE CONCAT('%', :search, '%')"    +
          "Or a.profession LIKE CONCAT('%', :search, '%')")
  Page<AluminiDetails> searchAluminiDetails(String search, Pageable pageable);

  @Query("SELECT a FROM AluminiDetails a")
  Page<AluminiDetails> getlistOfAluminiDetails(Pageable pageable);
}
