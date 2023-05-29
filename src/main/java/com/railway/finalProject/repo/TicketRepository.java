package com.railway.finalProject.repo;

import com.railway.finalProject.models.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "select * from ticket WHERE user_ID = ?1 ORDER BY id DESC", nativeQuery = true)
    Page<Ticket> findAll(Pageable pageable, Long id);

    @Query(value = "select * from ticket WHERE ID = ?1 and user_ID = ?2", nativeQuery = true)
    Optional<Ticket> findUserTicketById(Long id, Long userId);
}
