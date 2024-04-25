package com.example.tabelogkadai.form;

import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRegisterForm {
	@NotNull
	private Integer userId;
	
	@NotNull
	private Integer shopId;
	
	@Transactional
	public void deleteByUserIdAndShopId(Integer userId, Integer shopId) {
		this.userId = userId;
		this.shopId = shopId;
	}

}
