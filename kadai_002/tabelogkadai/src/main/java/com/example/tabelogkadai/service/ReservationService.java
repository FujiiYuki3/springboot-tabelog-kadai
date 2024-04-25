package com.example.tabelogkadai.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogkadai.entity.Reservation;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.form.ReservationEditForm;
import com.example.tabelogkadai.form.ReservationRegisterForm;
import com.example.tabelogkadai.repository.ReservationRepository;
import com.example.tabelogkadai.repository.ShopRepository;
import com.example.tabelogkadai.repository.UserRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;
	private final ShopRepository shopRepository;
	
	public ReservationService(ReservationRepository reservationRepository, ShopRepository shopRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
		this.shopRepository = shopRepository;
	}
	
	//予約追加機能
	@Transactional
	public void create(ReservationRegisterForm reservationRegisterForm) {
		Reservation reservation = new Reservation();
		User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
		Shop shop = shopRepository.getReferenceById(reservationRegisterForm.getShopId());
		LocalDate bookingDate = reservationRegisterForm.getBookingDate();
		LocalTime bookingTime = reservationRegisterForm.getBookingTime();
		
		reservation.setUser(user);
		reservation.setShop(shop);
		reservation.setBookingDate(bookingDate);
		reservation.setBookingTime(bookingTime);
		reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
		
		reservationRepository.save(reservation);
	}
	
	//予約人数が店の席数を上回っているかどうか判定する
	public boolean isWithinCapacity(Integer numberOfPeople, Integer seats) {
		return numberOfPeople <= seats;
	}
	
	//予約変更機能
	@Transactional
	public void update(ReservationEditForm reservationEditForm) {
		Reservation reservation = reservationRepository.getReferenceById(reservationEditForm.getId());
		
		reservation.setUser(reservationEditForm.getUser());
		reservation.setShop(reservationEditForm.getShop());
		reservation.setBookingDate(reservationEditForm.getBookingDate());
		reservation.setBookingTime(reservationEditForm.getBookingTime());
		reservation.setNumberOfPeople(reservationEditForm.getNumberOfPeople());
		
		reservationRepository.save(reservation);
	}

}
