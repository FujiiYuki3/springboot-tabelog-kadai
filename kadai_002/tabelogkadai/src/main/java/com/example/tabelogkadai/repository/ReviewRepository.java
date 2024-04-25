package com.example.tabelogkadai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogkadai.entity.Review;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	public Page<Review> findByShopOrderByCreatedAtDesc(Shop shop, Pageable pageable);
	public Page<Review> findByOrderByStarsAsc(Pageable pageable);
	public Page<Review> findByUserOrderByUpdatedAtDesc(User user, Pageable pageable);
	
	public Review getByUserIdAndShopId(Integer userId, Integer shopId);

}
