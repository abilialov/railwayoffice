package com.railway.finalProject.repo;

import com.railway.finalProject.models.Role;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select name from roles as r where id = (select role_id from user_roles where user_id = ?1);", nativeQuery = true)
    String findUserRole(int userId);

    Role findByName(String name);

}