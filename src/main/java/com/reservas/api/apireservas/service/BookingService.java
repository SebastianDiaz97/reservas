package com.reservas.api.apireservas.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservas.api.apireservas.dto.AvailabilitySlotsDTO;
import com.reservas.api.apireservas.dto.BookingDTO;
import com.reservas.api.apireservas.exception.ConflictException;
import com.reservas.api.apireservas.exception.NotFoundException;
import com.reservas.api.apireservas.exception.ValidationException;
import com.reservas.api.apireservas.mapper.Mapper;
import com.reservas.api.apireservas.model.Availability;
import com.reservas.api.apireservas.model.Booking;
import com.reservas.api.apireservas.model.Professional;
import com.reservas.api.apireservas.model.Provision;
import com.reservas.api.apireservas.model.User;
import com.reservas.api.apireservas.model.enums.Status;
import com.reservas.api.apireservas.repository.AvailabilityRepository;
import com.reservas.api.apireservas.repository.BookingRepository;
import com.reservas.api.apireservas.repository.ProfessionalProvisionRepository;
import com.reservas.api.apireservas.repository.ProfessionalRepository;
import com.reservas.api.apireservas.repository.ProvisionRepository;
import com.reservas.api.apireservas.repository.UserRepository;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private ProvisionRepository provisionRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ProfessionalProvisionRepository ppRepository;
    @Autowired
    private UserRepository uRepository;
    @Autowired
    private AvailabilityRepository aRepository;

    @Override
    public AvailabilitySlotsDTO searchShedules(Long serviceId, Long professionalId, LocalDate date) {
        if (professionalId == null) throw new ValidationException("professionalId es requerido");
        if (serviceId == null) throw new ValidationException("serviceId es requerido");
        if (date == null) throw new ValidationException("date es requerido");
        
        
        Boolean exists = ppRepository.existsByProfessionalIdAndProvisionId(professionalId, serviceId);
        if(!exists) throw new NotFoundException("Registro no encontrado");

        int durationProvision = provisionRepository.findById(serviceId).get().getDurationMinutes();


        DayOfWeek day = date.getDayOfWeek();
        Availability availabilities = aRepository.findByProfessionalIdAndDayOfWeekAndActiveTrue(professionalId, day);
        ArrayList<LocalTime> arrayShedules = new ArrayList<LocalTime>();

        if (availabilities != null) {
            LocalTime startTimeAv = availabilities.getStartTime();
            LocalTime endTimeAv = availabilities.getEndTime();
            for (LocalTime i = startTimeAv; endTimeAv.isAfter(i);i = i.plusMinutes(durationProvision)) {
                LocalTime end = i.plusMinutes(durationProvision);
                List<Booking> free = bookingRepository.findOverlaps(professionalId, date, i, end);
                if (free.isEmpty()) {
                    arrayShedules.add(i);
                }
            }
        }

        AvailabilitySlotsDTO slotsDTO = new AvailabilitySlotsDTO(date, professionalId, serviceId, arrayShedules);
        
        return slotsDTO;
    }

    @Override
    public List<BookingDTO> listBookingDTOs(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        List<BookingDTO> bookingDTOs = bookings.stream().map(Mapper::toDTO).collect(Collectors.toList());
        System.out.println(bookingDTOs);
        return bookingDTOs;
    }

    @Override
    public BookingDTO createBooking(Long serviceId, Long professionalId, LocalDate date, LocalTime startTime, Long userId) {
        if(serviceId == null) throw new ValidationException("serviceId es requerido");
        if(professionalId == null) throw new ValidationException("professionalId es requerido");
        if(date == null) throw new ValidationException("date es requerido");
        if(startTime == null) throw new ValidationException("startTime es requerido");
        if(userId == null) throw new ValidationException("userId es requerido");
        
        Boolean exists = ppRepository.existsByProfessionalIdAndProvisionId(professionalId, serviceId);
        if(!exists) throw new NotFoundException("Registro no encontrado");
        User user = uRepository.findById(userId).orElse(null);
        if(user == null) throw new NotFoundException("Usuario no encontrado");

        Professional professional = professionalRepository.findById(professionalId).get();
        Provision provision = provisionRepository.findById(serviceId).get();
        Booking booking = new Booking();

        int durationProvision = provision.getDurationMinutes();
        DayOfWeek day = date.getDayOfWeek();

        Availability availability = aRepository.findByProfessionalIdAndDayOfWeekAndActiveTrue(professionalId, day);
        if(availability == null) throw new NotFoundException("Profesional no trabaja ese dia");

        List<Booking> free = bookingRepository.findOverlaps(professionalId, date, startTime, startTime.plusMinutes(durationProvision));
        if (!free.isEmpty()) throw new ConflictException("Espacio horario no disponible");
    
        booking.setDate(date);
        booking.setProfessional(professional);
        booking.setProvision(provision);
        booking.setStatus(Status.ACTIVE);
        booking.setStartTime(startTime);
        booking.setEndTime(startTime.plusMinutes(durationProvision));
        booking.setUser(user);
        booking = bookingRepository.save(booking);
        
        return Mapper.toDTO(booking);
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null) throw new NotFoundException("Reserva no encontrada");

        booking.setStatus(Status.CANCELLED);
        bookingRepository.save(booking);
    }

}
