package com.example.tabelogkadai.form;

import java.sql.Time;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopRegisterForm {
	@NotNull(message = "カテゴリ名を選択してください。")
	private String categoryName;
	
	@NotBlank(message = "店名を入力してください。")
	private String shopName;
	
	@NotBlank(message = "フリガナを入力してください。")
	private String furigana;
	
	@NotBlank(message = "ローマ字で入力してください。")
	private String alphabet;
	
	private MultipartFile photoName;
	
	@NotBlank(message = "説明を入力してください。")
	private String description;
	
	@NotNull(message = "開店時間を入力してください。")
	private Time openingHour;
	
	@NotNull(message = "閉店時間を入力してください。")
	private Time closingHour;
	
	@NotNull(message = "定休日を入力してください。")
	private String closedDay;
	
	@NotNull(message = "予算額（最低）を入力してください。")
	private Integer minimumBudget;
	
	@NotNull(message = "予算額（最大）を入力してください。")
	private Integer maximumBudget;
	
	@NotBlank(message = "住所を入力してください。")
	private String address;
	
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
	
	@NotNull(message = "席数を入力してください。")
	private Integer seats;

}
