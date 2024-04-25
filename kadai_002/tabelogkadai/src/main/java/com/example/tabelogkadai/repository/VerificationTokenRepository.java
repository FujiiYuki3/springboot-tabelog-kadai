package com.example.tabelogkadai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
	public VerificationToken findByToken(String token);
	public VerificationToken findByUser(User user);

}
