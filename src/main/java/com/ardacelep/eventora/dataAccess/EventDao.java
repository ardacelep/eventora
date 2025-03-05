package com.ardacelep.eventora.dataAccess;

import com.ardacelep.eventora.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventDao extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Event> findByNameLike(String name);

    Optional<Event> findByNameIgnoreCaseAndVenueIgnoreCaseAndDate(String name, String venue, LocalDateTime date);
}
