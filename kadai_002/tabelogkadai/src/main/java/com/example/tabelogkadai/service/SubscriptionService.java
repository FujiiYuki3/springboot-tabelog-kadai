package com.example.tabelogkadai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.tabelogkadai.entity.Type;
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.repository.CardRepository;
import com.example.tabelogkadai.repository.TypeRepository;
import com.example.tabelogkadai.repository.UserRepository;

@Service
public class SubscriptionService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private CardRepository cardRepository;
	
	public void upgradeUserToPaid(String userName) {
		User user = userRepository.findByUserName(userName);
		if(cardRepository.findByUser(user) != null) {
			Type type = typeRepository.getReferenceById(2);
			user.setType(type);
			userRepository.save(user);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), AuthorityUtils.createAuthorityList("ROLE_PAIDUSER"));
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}else if(cardRepository.findByUser(user) == null) {
			Type type = typeRepository.getReferenceById(1);
			user.setType(type);
			userRepository.save(user);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), AuthorityUtils.createAuthorityList("ROLE_FREEUSER"));
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}
	}

}
