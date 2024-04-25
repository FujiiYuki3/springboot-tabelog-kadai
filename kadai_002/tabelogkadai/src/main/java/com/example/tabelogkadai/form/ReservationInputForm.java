package com.example.tabelogkadai.form;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {
	@NotNull(message = "予約日時を入力してください。")
	private String bookingDateTime;
	
	@NotNull(message = "予約人数を入力してください。")
	@Min(value = 1, message = "予約人数は1人以上に設定してください。")
	private Integer numberOfPeople;
	
	public LocalDate getBookingDate() {
		String[] bookingDateTime = getBookingDateTime().split(" ");
		return LocalDate.parse(bookingDateTime[0]);
	}
	
	public LocalTime getBookingTime() {
		String[] bookingDateTime = getBookingDateTime().split(" ");
		return LocalTime.parse(bookingDateTime[1]);
	}

}
