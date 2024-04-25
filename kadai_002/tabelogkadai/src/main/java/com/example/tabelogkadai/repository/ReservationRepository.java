package com.example.tabelogkadai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogkadai.entity.Reservation;
import com.example.tabelogkadai.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	public Page<Reservation> findByUserOrderByBookingDateAscBookingTimeAsc(User user, Pageable pageable);

}
