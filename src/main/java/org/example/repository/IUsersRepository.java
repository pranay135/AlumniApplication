package org.example.repository;

import org.example.entity.Users;
import org.example.projection.UsersProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<Users, Long> {

   Optional<Users> findByEmailId(String emailId);


   @Query(value = "SELECT id FROM users", nativeQuery = true)
   UsersProjection getUserId();

}
