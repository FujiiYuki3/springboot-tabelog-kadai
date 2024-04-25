package com.example.tabelogkadai.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogkadai.entity.Review;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.form.ReviewEditForm;
import com.example.tabelogkadai.form.ReviewRegisterForm;
import com.example.tabelogkadai.repository.ReviewRepository;
import com.example.tabelogkadai.repository.ShopRepository;
import com.example.tabelogkadai.security.UserDetailsImpl;
import com.example.tabelogkadai.service.ReviewService;

@Controller
@RequestMapping("/login")
public class ReviewController {
	private final ReviewService reviewService;
	private final ReviewRepository reviewRepository;
	private final ShopRepository shopRepository;
	
	public ReviewController(ReviewService reviewService, ShopRepository shopRepository, ReviewRepository reviewRepository) {
		this.reviewService = reviewService;
		this.shopRepository = shopRepository;
		this.reviewRepository = reviewRepository;
	}
	
	//レビュー一覧ページ
	@GetMapping("/reviews")
	public String reviews(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable, Model model) {
		User user = userDetailsImpl.getUser();
		
		Page<Review> newReview = reviewRepository.findByUserOrderByUpdatedAtDesc(user, pageable);
		
		model.addAttribute("newReview", newReview);
		
		return "paidUser/reviews/index";
	}
	
	//レビュー投稿ページ
	@GetMapping("/{id}/review")
	public String review(@PathVariable(name = "id") Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		
		model.addAttribute("shop", shop);
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		
		return "paidUser/reviews/register";
	}
	
	//レビュー編集ページ
	@GetMapping("/reviews/{id}")
	public String reviewShow(@PathVariable(name = "id")Integer id, Model model) {
		Review review = reviewRepository.getReferenceById(id);
		ReviewEditForm reviewEditForm = new ReviewEditForm(id, review.getUser(), review.getShop(), review.getStars(), review.getReviewComment(), null);
		
		model.addAttribute("review", review);
		model.addAttribute("reviewEditForm", reviewEditForm);
		
		return "paidUser/reviews/edit";
	}
	
	//レビュー投稿処理
	@PostMapping("/{id}/review/create")
	public String review(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id") Integer id, ReviewRegisterForm reviewRegisterForm, RedirectAttributes redirectAttributes) {
		User user = userDetailsImpl.getUser();
		Shop shop = shopRepository.getReferenceById(id);
		
		reviewService.create(user, shop, reviewRegisterForm);
		
		redirectAttributes.addFlashAttribute("createSuccessMessage", "レビューを投稿しました。");
		
		return "redirect:/login/reviews";
	}
	
	//レビュー変更処理
	@PostMapping("/reviews/{id}/update")
	public String update(@ModelAttribute @Validated ReviewEditForm reviewEditForm, RedirectAttributes redirectAttributes) {
		reviewService.update(reviewEditForm);
		
		redirectAttributes.addFlashAttribute("updateSuccessMessage", "レビューを更新しました。");
		
		return "redirect:/login/reviews";
	}
	
	//レビュー削除処理
	@PostMapping("/reviews/{id}/delete")
	public String delete(@PathVariable(name = "id")Integer id, RedirectAttributes redirectAttributes) {
		reviewRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("deleteSuccessMessage", "レビューを削除しました。");
		
		return "redirect:/login/reviews";
	}

}
