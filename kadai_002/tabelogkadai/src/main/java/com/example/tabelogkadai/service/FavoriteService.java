package com.example.tabelogkadai.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogkadai.entity.Favorite;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.form.FavoriteRegisterForm;
import com.example.tabelogkadai.repository.FavoriteRepository;
import com.example.tabelogkadai.repository.ShopRepository;
import com.example.tabelogkadai.repository.UserRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;
	private final UserRepository userRepository;
	private final ShopRepository shopRepository;
	
	public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, ShopRepository shopRepository) {
		this.favoriteRepository = favoriteRepository;
		this.userRepository = userRepository;
		this.shopRepository = shopRepository;
	}
	
	//お気に入り追加機能
	@Transactional
	public void create(FavoriteRegisterForm favoriteRegisterForm, User user, Shop shop) {
		Favorite favorite = new Favorite();
		
		favoriteRegisterForm.setUserId(user.getId());
		favoriteRegisterForm.setShopId(shop.getId());
		
		favorite.setUser(userRepository.getReferenceById(favoriteRegisterForm.getUserId()));
		favorite.setShop(shopRepository.getReferenceById(favoriteRegisterForm.getShopId()));
		
		favoriteRepository.save(favorite);
	}
	
	//ユーザーがすでにお気に入り登録済みかどうか判定する
	@Transactional
	public boolean isFavoritedUserAndFavoritedShop(User user, Shop shop) {
		Favorite isFavorited = favoriteRepository.getByShopIdAndUserId(shop.getId(), user.getId());
		
		if(isFavorited != null) {
			return true;
		}
		
		return false;
	}

}
