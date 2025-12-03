package com.reservas.api.apireservas.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservas.api.apireservas.dto.AvailabilitySlotsDTO;
import com.reservas.api.apireservas.dto.BookingDTO;
import com.reservas.api.apireservas.service.IBookingService;



@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private IBookingService service;
    @GetMapping
    public ResponseEntity<AvailabilitySlotsDTO> getShedule(
        @RequestParam(required = true) Long serviceId,
        @RequestParam(required = true) Long professionalId,
        @RequestParam(required = true) String date
    ) {
        LocalDate transformedDate = LocalDate.parse(date);
        
        return ResponseEntity.ok(service.searchShedules(serviceId, professionalId, transformedDate));
    }
    

    @GetMapping("/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsUser(@PathVariable Long userId) {
        
        return ResponseEntity.ok(service.listBookingDTOs(userId));
    }

    @PostMapping()
    public ResponseEntity<BookingDTO> postBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO dto = service.createBooking(bookingDTO.serviceId(), bookingDTO.professionalId(), bookingDTO.date(), bookingDTO.startTime(), bookingDTO.userId());
        String url = "/booking/" + dto.id();

        return ResponseEntity.created(URI.create(url)).body(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id){
        service.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
    
}
