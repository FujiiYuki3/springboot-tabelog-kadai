package com.example.tabelogkadai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogkadai.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	public User findByUserName(String userName);
	
	public Page<User> findByUserNameLikeOrFuriganaLikeOrderByCreatedAtDesc(String userNameKeyword, String furiganaKeyword, Pageable pageable);
	public Page<User> findByUserNameLikeOrFuriganaLikeOrderByFuriganaAsc(String keyword, String furiganaKeyword, Pageable pageable);
	
	public Page<User> findAllByOrderByCreatedAtDesc(Pageable ageable);
	public Page<User> findAllByOrderByFuriganaAsc(Pageable ageable);

}
