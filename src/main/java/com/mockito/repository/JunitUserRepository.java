package com.mockito.repository;

import com.mockito.domain.JunitUser;
import com.mockito.dto.UserDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface JunitUserRepository extends JpaRepository<JunitUser,Long> {



//    @Query(value = "SELECT new com.mockito.dto.UserDto(u.firstName,u.lastName,u.email)" +
//            " from JunitUser u")
@Query(value = "SELECT u.id, u.first_name, u.last_name, u.email, u.password " +
        "FROM junit_user u ", nativeQuery = true)
    List<Object[]> findAllActiveUsers();


//JPQL
    @Query("SELECT u From JunitUser u Where u.firstName=:firstName")
    List<JunitUser>findByFirstName(@Param("firstName") String firstName);

    //JPQL of Index Parameter

    @Query("SELECT u From JunitUser u Where u.firstName= ?1 and u.lastName=?2")
    List<JunitUser>findByFirstNameWithIndexParameter(String firstName,String lastName);

    //Native Query
    @Query(value = "SELECT * FROM junit_user WHERE last_name = :lastName", nativeQuery = true)
    List<JunitUser>findByLastName(@Param("lastName") String lastName);

    //Native Query Index parameter

    @Query(value = "SELECT u From JunitUser u Where u.lastName= ?1 and u.email=?1",nativeQuery = true)
    List<JunitUser>findByLastNameWithIndexParaMeter(String lastName,String email);

    @Query(value = "SELECT u.id as userId, u.first_name as firstName, u.last_name as lastName, r.role_name as roleName " +
            "FROM junit_user u " +
            "JOIN role r ON u.role_id = r.id " +
            "WHERE r.role_name = :roleName",
            nativeQuery = true)
    List<Object[]> findUsersByRoleName(@Param("roleName") String roleName);

}

