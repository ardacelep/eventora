package com.ardacelep.eventora.dataAccess;

import com.ardacelep.eventora.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, UUID> {

    List<Reservation> findReservationsByCustomerNameIgnoreCase(String customerName);

}
