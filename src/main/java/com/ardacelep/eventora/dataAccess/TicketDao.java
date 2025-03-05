package com.ardacelep.eventora.dataAccess;

import com.ardacelep.eventora.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketDao extends JpaRepository<Ticket, UUID> {

    @Query("SELECT t FROM Ticket t WHERE t.event.id = :eventId AND t.seatNumber = :seatNumber")
    public Optional<Ticket> findTicketByEventIdAndSeatNumber(UUID eventId, String seatNumber);

}
