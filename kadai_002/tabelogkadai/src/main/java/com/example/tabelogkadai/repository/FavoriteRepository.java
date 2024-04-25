package com.example.tabelogkadai.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogkadai.entity.Favorite;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	public List<Favorite> findByShop(Shop shop);
	public Page<Favorite> findByUser(User user, Pageable pageable);
	
	@Transactional
	public void deleteByShopIdAndUserId(Integer shopId, Integer userId);
	
	public Favorite getByShopIdAndUserId(Integer shopId, Integer userId);

}
