package com.railway.finalProject.repo;

import com.railway.finalProject.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "select * from users where login = ?1", nativeQuery = true)
    Users findByLogin(String username);

    Users findByEmail(String email);

    @Query(value = "select * from users where phone_number = ?1", nativeQuery = true)
    Users findByByPhoneNumber(String phoneNumber);

    Page<Users> findAll(Pageable pageable);
}
