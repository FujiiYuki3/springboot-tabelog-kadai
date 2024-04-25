package com.example.tabelogkadai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogkadai.entity.Category;
import com.example.tabelogkadai.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
	public Page<Shop> findByShopNameLikeOrAlphabetLikeOrFuriganaLikeOrderByCreatedAtDesc(String shopNameKeyword, String alphabetKeyword, String furiganaKeyword, Pageable pageable);
	public Page<Shop> findByShopNameLikeOrAlphabetLikeOrFuriganaLikeOrderByMinimumBudgetAsc(String shopNameKeyword, String alphabetKeyword, String furiganaKeyword, Pageable pageable);
	public Page<Shop> findByShopNameLikeOrAlphabetLikeOrFuriganaLikeOrderByFuriganaAsc(String shopNameKeyword, String alphabetKeyword, String furiganaKeyword, Pageable pageable);
	
	public Page<Shop> findByCategoryOrderByCreatedAtDesc(Category category, Pageable pageable);
	public Page<Shop> findByCategoryOrderByMinimumBudgetAsc(Category category, Pageable pageable);
	public Page<Shop> findByCategoryOrderByFuriganaAsc(Category category, Pageable pageable);
	
	public Page<Shop> findByMinimumBudgetLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable ageable);
	public Page<Shop> findByMinimumBudgetLessThanEqualOrderByMinimumBudgetAsc(Integer price, Pageable ageable);
	public Page<Shop> findByMinimumBudgetLessThanEqualOrderByFuriganaAsc(Integer price, Pageable ageable);
	
	public Page<Shop> findAllByOrderByCreatedAtDesc(Pageable ageable);
	public Page<Shop> findAllByOrderByMinimumBudgetAsc(Pageable ageable);
	public Page<Shop> findAllByOrderByFuriganaAsc(Pageable ageable);

}
