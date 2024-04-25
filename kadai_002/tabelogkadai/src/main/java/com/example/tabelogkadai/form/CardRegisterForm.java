package com.example.tabelogkadai.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CardRegisterForm {
	@NotBlank(message = "メールアドレスを記入してください。")
	private String email;
	
	/*@NotBlank
	private String customerId;*/

}
