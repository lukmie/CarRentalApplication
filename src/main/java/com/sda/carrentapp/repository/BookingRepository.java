package com.sda.carrentapp.repository;

import com.sda.carrentapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> getBookingById(Long id);
}
