package com.example.tabelogkadai.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogkadai.entity.Type;
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.form.PasswordResetForm;
import com.example.tabelogkadai.form.UserEditForm;
import com.example.tabelogkadai.form.UserRegisterForm;
import com.example.tabelogkadai.repository.CardRepository;
import com.example.tabelogkadai.repository.TypeRepository;
import com.example.tabelogkadai.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final TypeRepository typeRepository;
	private final CardRepository cardRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, TypeRepository typeRepository, CardRepository cardRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.typeRepository = typeRepository;
		this.cardRepository = cardRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	//会員登録機能
	@Transactional
	public User create(UserRegisterForm userInputForm) {
		User user = new User();
		Type type = typeRepository.findByTypeNameJp(userInputForm.getTypeNameJp());
		
		user.setUserName(userInputForm.getUserName());
		user.setFurigana(userInputForm.getFurigana());
		user.setPhoneNumber(userInputForm.getPhoneNumber());
		user.setEmail(userInputForm.getEmail());
		user.setPassword(passwordEncoder.encode(userInputForm.getPassword()));
		user.setType(type);
		user.setEnabled(false);
		
		return userRepository.save(user);
	}
	
	//会員情報編集機能
	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getId());
		
		user.setUserName(userEditForm.getUserName());
		user.setFurigana(userEditForm.getFurigana());
		user.setEmail(userEditForm.getEmail());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setEnabled(true);
		
		userRepository.save(user);
	}
	
	//メールアドレスが登録済みかどうか確認する
	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	
	//パスワードとパスワード（確認用）が一致しているか確認する
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}
	
	//メールアドレスが変更されたか確認する
	public boolean isEmailChanged(UserEditForm userEditForm) {
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		return !userEditForm.getEmail().equals(currentUser.getEmail());
	}
	
	//ユーザーを有効にする
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	//パスワードリセット機能
	@Transactional
	public void passwordUpdate(PasswordResetForm passwordResetForm) {
		User user = userRepository.findByEmail(passwordResetForm.getEmail());
		
		user.setPassword(passwordEncoder.encode(passwordResetForm.getPassword()));
		user.setEnabled(true);
		
		userRepository.save(user);
	}
	
	//サブスクアップグレード機能
	@Transactional
	public void typeUpdate(String email) {
		User user = userRepository.findByEmail(email);
		Type type = typeRepository.getReferenceById(2);
		
		user.setType(type);
		
		userRepository.save(user);
	}
	
	//サブスク解約機能
		/*@Transactional
		public void typeDowngrade(String subscriptionId) {
			Card card = cardRepository.findBySubscriptionId(subscriptionId);
			User user = card.getUser();
			Type type = typeRepository.getReferenceById(1);
			
			user.setType(type);
			
			userRepository.save(user);
		}*/

}
