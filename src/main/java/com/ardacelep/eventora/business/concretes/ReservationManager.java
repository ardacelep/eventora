package com.ardacelep.eventora.business.concretes;

import com.ardacelep.eventora.business.abstracts.ReservationService;
import com.ardacelep.eventora.dataAccess.ReservationDao;
import com.ardacelep.eventora.dataAccess.TicketDao;
import com.ardacelep.eventora.entities.Reservation;
import com.ardacelep.eventora.entities.Ticket;
import com.ardacelep.eventora.entities.dto.ReservationDto;
import com.ardacelep.eventora.entities.dto.ReservationDtoIU;
import com.ardacelep.eventora.enums.ErrorMessageType;
import com.ardacelep.eventora.enums.ReservationStatus;
import com.ardacelep.eventora.enums.TicketStatus;
import com.ardacelep.eventora.exception.RuntimeBaseException;
import com.ardacelep.eventora.helpers.ReservationManagerHelpers;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationManager implements ReservationService {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    ReservationManagerHelpers resManHelp;

    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationDto makeReservation(ReservationDtoIU reservationDtoIU) {

        Optional<Ticket> optional = ticketDao.findById(reservationDtoIU.getTicketId());

        if (optional.isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("given ticket does not exist, searched in tickets for id: {0}",reservationDtoIU.getTicketId()), HttpStatus.NOT_FOUND);

        Ticket reservationTicket = optional.get();

        // enum'lar için == kullanılabilir çünkü referanslarının hepsi tek bir yerde saklanır.
        // (==) operatörü bellek referanslarını karşılaştırır.
        // enum olmasa reservationTicket.getStatus() != null &&
        // reservationTicket.getStatus().equals(TicketStatus.RESERVED) yapardık, null pointer hatası
        // almamak için.
        if (reservationTicket.getStatus() == TicketStatus.RESERVED) throw new RuntimeBaseException(ErrorMessageType.ALREADY_RESERVED, MessageFormat.format("given ticket is already reserved, searched in tickets for id: {0}",reservationDtoIU.getTicketId()), HttpStatus.CONFLICT);

        reservationTicket.setReservation(null);

        Reservation reservation = new Reservation();

        reservationTicket.setStatus(TicketStatus.RESERVED);

        BeanUtils.copyProperties(reservationDtoIU,reservation);

        reservation.setTicket(reservationTicket);

        reservation.setStatus(ReservationStatus.ACTIVE);

        reservationTicket.setReservation(reservation);

        entityManager.flush(); // flush() , transactional ile takip edilen aksiyonların veritabanına
        // aktarılmasını tetikler. normalde metot bitiminde tetiklenir. burada manuel save yapmak yerine
        // flush ile tetikleyerek save işlemini gerçekleştirdik ve kaydedilen reservation'ın id alanı
        // veritabanından gelen id'yle otomatik olarak dolduruldu.
        // not: metot bitmeden hata fırlatılırsa @Transactional flush ile tetiklenen işlemleri de geri alır.
        // normalde transactional RuntimeException'ları ve alt sınıflarını yakalar. spring'in transactional'ı
        // rollbackFor ile içine verilen sınıfları ve tüm alt sınıflarını yakalar.
        // Exception.class vererek bütün hataların yakalanmasını sağladık.

        return resManHelp.convertReservationtoDto(reservation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationDto cancelReservation(UUID reservationId) {

        Optional<Reservation> optional = reservationDao.findById(reservationId);

        if (optional.isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("given reservation does not exist, searched in reservations for id: {0}",reservationId), HttpStatus.NOT_FOUND);

        Reservation dbReservation = optional.get();

        if (dbReservation.getStatus() == ReservationStatus.CANCELLED) throw new RuntimeBaseException(ErrorMessageType.ALREADY_CANCELLED, MessageFormat.format("given reservation is already cancelled, searched in reservations for id: {0}",reservationId), HttpStatus.CONFLICT);

        dbReservation.setStatus(ReservationStatus.CANCELLED);

        dbReservation.getTicket().setStatus(TicketStatus.AVAILABLE);

        entityManager.flush();

        return resManHelp.convertReservationtoDto(dbReservation);
    }

    @Override
    @Transactional
    public List<ReservationDto> findReservationsByCustomerNameIgnoreCase(String customerName) {

        List<ReservationDto> reservationDtoList = new ArrayList<>();

        if (reservationDao.findReservationsByCustomerNameIgnoreCase(customerName).isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("there is no reservations made in the name: {0}",customerName), HttpStatus.NOT_FOUND);

        for (Reservation reservation : reservationDao.findReservationsByCustomerNameIgnoreCase(customerName)) {
            reservationDtoList.add(resManHelp.convertReservationtoDto(reservation));

        }

        return reservationDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReservationDto activateReservation(UUID reservationId) {

        Optional<Reservation> optional = reservationDao.findById(reservationId);

        if (optional.isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("given reservation does not exist, searched in reservations for id: {0}",reservationId), HttpStatus.NOT_FOUND);

        Reservation dbReservation = optional.get();

        if (dbReservation.getStatus() == ReservationStatus.ACTIVE) throw new RuntimeBaseException(ErrorMessageType.ALREADY_ACTIVE, MessageFormat.format("given reservation is already active, searched in reservations for id: {0}",reservationId), HttpStatus.CONFLICT);

        dbReservation.setStatus(ReservationStatus.ACTIVE);

        dbReservation.getTicket().setStatus(TicketStatus.RESERVED);

        entityManager.flush();

        return resManHelp.convertReservationtoDto(dbReservation);
    }

    @Override
    @Transactional
    public ReservationDto findReservationById(UUID id) {

        Optional<Reservation> optional = reservationDao.findById(id);

        if (optional.isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("given reservation does not exist, searched in reservations for id: {0}",id), HttpStatus.NOT_FOUND);

        return resManHelp.convertReservationtoDto(optional.get());
    }

    @Override
    @Transactional
    public List<ReservationDto> findAllReservations() {

        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for (Reservation reservation : reservationDao.findAll()) {
            reservationDtoList.add(resManHelp.convertReservationtoDto(reservation));

        }

        return reservationDtoList;
    }


}
