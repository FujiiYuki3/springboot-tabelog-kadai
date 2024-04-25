package com.example.tabelogkadai.form;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRegisterForm {
	private Integer userId;
	
	private Integer shopId;
	
	private LocalDate bookingDate;
	
	private LocalTime bookingTime;
	
	private Integer numberOfPeople;

}
