package com.example.tabelogkadai.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		try {
			User user = userRepository.findByEmail(email);
			String userTypeName = user.getType().getTypeNameEn();
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(userTypeName));
			return new UserDetailsImpl(user, authorities);
		}catch(Exception e){
			throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
		}
	}

}
