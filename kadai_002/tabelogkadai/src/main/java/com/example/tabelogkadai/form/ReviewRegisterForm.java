package com.example.tabelogkadai.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRegisterForm {
	@NotBlank(message = "評価を選択してください。")
	private String stars;
	
	@NotBlank(message = "コメントを入力してください。")
	private String reviewComment;
	
	private MultipartFile photoName;

}
