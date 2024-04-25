package com.example.tabelogkadai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogkadai.entity.Card;
import com.example.tabelogkadai.entity.User;

public interface CardRepository extends JpaRepository<Card, Integer> {
	public Card findByUser(User user);
	public Card findBySubscriptionId(String subscriptionId);
	
	@Transactional
	public Card deleteBySubscriptionId(String subscriptionId);

}
