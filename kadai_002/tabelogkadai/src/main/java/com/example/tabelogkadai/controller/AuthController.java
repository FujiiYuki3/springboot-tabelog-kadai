package com.example.tabelogkadai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.entity.VerificationToken;
import com.example.tabelogkadai.event.ResetEventPublisher;
import com.example.tabelogkadai.event.SignupEventPublisher;
import com.example.tabelogkadai.form.PasswordResetForm;
import com.example.tabelogkadai.form.UserRegisterForm;
import com.example.tabelogkadai.form.VerificationTokenEditForm;
import com.example.tabelogkadai.repository.UserRepository;
import com.example.tabelogkadai.repository.VerificationTokenRepository;
import com.example.tabelogkadai.service.StripeService;
import com.example.tabelogkadai.service.UserService;
import com.example.tabelogkadai.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final UserService userService;
	private final VerificationTokenService verificationTokenService;
	private final StripeService stripeService;
	private final SignupEventPublisher signupEventPublisher;
	private final ResetEventPublisher resetEventPublisher;
	
	public AuthController(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, UserService userService, VerificationTokenService verificationTokenService, StripeService stripeService, SignupEventPublisher signupEventPublisher, ResetEventPublisher resetEventPublisher) {
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.userService = userService;
		this.verificationTokenService = verificationTokenService;
		this.stripeService = stripeService;
		this.signupEventPublisher = signupEventPublisher;
		this.resetEventPublisher = resetEventPublisher;
	}
	
	//トップページ
	@GetMapping("/")
	public String login() {
		return "auth/login";
	}
	
	//会員登録画面
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("userInputForm", new UserRegisterForm());
		return "auth/signup";
	}
	
	@GetMapping("/signup/verify")
	public String verify(@RequestParam(name = "token") String token, Model model, HttpServletRequest httpServletRequest) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if(verificationToken != null) {
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "会員登録が完了しました。";
			String sessionId = stripeService.createStripeSession(httpServletRequest);
			
			model.addAttribute("successMessage", successMessage);
			model.addAttribute("user", user);
			model.addAttribute("sessionId", sessionId);
		}else {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
		}
		
		return "auth/verify";
	}
	
	//トークン再発行画面
	@GetMapping("/reset")
	public String reset(Model model) {
		model.addAttribute("verificationTokenEditForm", new VerificationTokenEditForm());
		
		return "auth/tokenReset";
	}
	
	@GetMapping("/reset/token/verify")
	public String resetVerify(@RequestParam("token")String token, Model model) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		User user = verificationToken.getUser();
		PasswordResetForm passwordResetForm = new PasswordResetForm(user.getId(), user.getEmail(), null);
		
		if(verificationToken != null && user != null) {
			userService.enableUser(user);
		}
		
		model.addAttribute("user", user);
		model.addAttribute("passwordResetForm", passwordResetForm);
		
		return "auth/passwordReset";
	}
	
	//会員登録処理
	@PostMapping("/signup")
	public String signup(@ModelAttribute @Validated UserRegisterForm userRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		if(userService.isEmailRegistered(userRegisterForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
			bindingResult.addError(fieldError);
		}
		
		if(!userService.isSamePassword(userRegisterForm.getPassword(), userRegisterForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
			bindingResult.addError(fieldError);
		}
		
		if(bindingResult.hasErrors()) {
			return "auth/signup";
		}
		
		User createdUser = userService.create(userRegisterForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createdUser, requestUrl);
		redirectAttributes.addFlashAttribute("signupSuccessMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。");
		
		return "redirect:/";
	}
	
	//@カード情報登録処理
	/*@PostMapping("/signup/verify/create")
	public String cardCreate(@ModelAttribute @Validated CardRegisterForm cardRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		User user = userRepository.findByEmail(cardRegisterForm.getEmail());
		
		cardService.create(user, cardRegisterForm);
		redirectAttributes.addFlashAttribute("cardSuccessMessage", "会員登録が完了しました。");
		
		return "redirect:/";
	}*/
	
	//トークン再発行処理
	@PostMapping("/reset/token")
	public String resetToken(@ModelAttribute @Validated VerificationTokenEditForm verificationTokenEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		User updatedUser = userRepository.findByEmail(verificationTokenEditForm.getEmail());
		String requestUrl = new String(httpServletRequest.getRequestURL());
		resetEventPublisher.publishResetEvent(updatedUser, requestUrl);
		redirectAttributes.addFlashAttribute("resetTokenSuccessMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。");
		
		return "redirect:/";
	}
	
	//パスワードリセット処理
	@PostMapping("/reset/token/verify*")
	public String resetPassword(@ModelAttribute @Validated PasswordResetForm passwordResetForm, RedirectAttributes redirectAttributes) {
		userService.passwordUpdate(passwordResetForm);
		redirectAttributes.addFlashAttribute("resetPasswordSuccessMessage", "パスワードを変更しました。");
		
		return "redirect:/";
	}

}
