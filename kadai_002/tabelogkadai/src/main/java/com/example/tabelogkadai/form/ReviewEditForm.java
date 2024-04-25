package com.example.tabelogkadai.form;

import org.springframework.web.multipart.MultipartFile;

import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	@NotNull
	private Integer id;
	
	@NotNull
	private User user;
	
	@NotNull
	private Shop shop;
	
	@NotNull(message = "評価を選択してください。")
	private String stars;
	
	@NotNull(message = "コメントを入力してください。")
	private String reviewComment;
	
	private MultipartFile photoName;

}
