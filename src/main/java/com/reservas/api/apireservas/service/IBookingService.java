package com.reservas.api.apireservas.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.reservas.api.apireservas.dto.AvailabilitySlotsDTO;
import com.reservas.api.apireservas.dto.BookingDTO;

public interface IBookingService {
    AvailabilitySlotsDTO searchShedules(Long serviceId, Long professionalId, LocalDate date);
    List<BookingDTO> listBookingDTOs(Long userId);
    BookingDTO createBooking(Long serviceId, Long professionalId, LocalDate date, LocalTime startTime, Long userId);
    void cancelBooking(Long id);
}
