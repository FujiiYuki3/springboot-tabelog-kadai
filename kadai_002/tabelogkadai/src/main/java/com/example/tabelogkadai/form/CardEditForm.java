package com.example.tabelogkadai.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardEditForm {
	@NotBlank(message = "メールアドレスを記入してください。")
	private String email;
	
	@NotBlank
	private String customerId;

}
