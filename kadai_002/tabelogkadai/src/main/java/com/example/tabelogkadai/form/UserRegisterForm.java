package com.example.tabelogkadai.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterForm {
	@NotBlank(message = "名前を入力してください。")
	private String userName;
	
	@NotBlank(message = "フリガナを入力してください。")
	private String furigana;
	
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
	
	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスは正しい形式で入力してください。")
	private String email;
	
	@NotBlank(message = "パスワードを入力してください。")
	@Length(min = 8, message = "パスワードは8文字以上で入力してください。")
	private String password;
	
	@NotBlank(message = "パスワード（確認用）を入力してください。")
	private String passwordConfirmation;
	
	@NotBlank(message = "属性を選択してください。")
	private String typeNameJp;
	
	private String cardNumber;

}
