package com.example.tabelogkadai.form;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationEditForm {
	@NotNull
	private Integer id;
	
	@NotNull
	private User user;
	
	@NotNull
	private Shop shop;
	
	@NotNull(message = "予約日を入力してください。")
	private LocalDate bookingDate;
	
	@NotNull(message = "予約時間を入力してください。")
	private LocalTime bookingTime;
	
	@NotNull(message = "予約人数を入力してください。")
	@Min(value = 1, message = "予約人数は1人以上に設定してください。")
	private Integer numberOfPeople;

}
