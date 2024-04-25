package com.example.tabelogkadai.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tabelogkadai.entity.Category;
import com.example.tabelogkadai.entity.Review;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.form.FavoriteRegisterForm;
import com.example.tabelogkadai.form.ReservationInputForm;
import com.example.tabelogkadai.repository.CategoryRepository;
import com.example.tabelogkadai.repository.ReviewRepository;
import com.example.tabelogkadai.repository.ShopRepository;
import com.example.tabelogkadai.repository.UserRepository;
import com.example.tabelogkadai.security.UserDetailsImpl;
import com.example.tabelogkadai.service.FavoriteService;
import com.example.tabelogkadai.service.ReviewService;

@Controller
@RequestMapping("/login")
public class HomeController {
	private final ShopRepository shopRepository;
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final FavoriteService favoriteService;
	private final ReviewService reviewService;
	
	public HomeController(ShopRepository shopRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository, UserRepository userRepository, FavoriteService favoriteService, ReviewService reviewService) {
		this.shopRepository = shopRepository;
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.favoriteService = favoriteService;
		this.reviewService = reviewService;
	}
	
	//ログイン後トップページ（全員共通）
	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "shopKeyword", required = false) String shopKeyword, @RequestParam(name = "categoryKeyword", required = false) String categoryKeyword, @RequestParam(name = "price", required = false) Integer price, @RequestParam(name = "order", required = false) String order, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Page<Shop> shopPage;
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		//空文字チェックいれるか？switch分にするか？
		if(shopKeyword != null && !shopKeyword.isEmpty()) {
			if(order != null && order.equals("minimumBudgetAsc")) {
				shopPage = shopRepository.findByShopNameLikeOrAlphabetLikeOrFuriganaLikeOrderByMinimumBudgetAsc("%" + shopKeyword + "%", "%" + shopKeyword + "%", "%" + shopKeyword + "%", pageable);
			}else if(order != null && order.equals("furiganaAsc")) {
				shopPage = shopRepository.findByShopNameLikeOrAlphabetLikeOrFuriganaLikeOrderByFuriganaAsc("%" + shopKeyword + "%", "%" + shopKeyword + "%", "%" + shopKeyword + "%", pageable);
			}else{
				shopPage = shopRepository.findByShopNameLikeOrAlphabetLikeOrFuriganaLikeOrderByCreatedAtDesc("%" + shopKeyword + "%", "%" + shopKeyword + "%", "%" + shopKeyword + "%", pageable);
			}
		}else if(price != null) {
			if(order != null && order.equals("createdAtDesc")) {
				shopPage = shopRepository.findByMinimumBudgetLessThanEqualOrderByCreatedAtDesc(price, pageable);
			}else if(order != null && order.equals("minimumBudgetAsc")) {
				shopPage = shopRepository.findByMinimumBudgetLessThanEqualOrderByMinimumBudgetAsc(price, pageable);
			}else{
				shopPage = shopRepository.findByMinimumBudgetLessThanEqualOrderByFuriganaAsc(price, pageable);
			}
		}else if(categoryKeyword != null && !categoryKeyword.isEmpty()) {
			if(order != null && order.equals("createdAtDesc")) {
				shopPage = shopRepository.findByCategoryOrderByCreatedAtDesc(categoryRepository.findByCategoryNameLike("%" + categoryKeyword + "%"), pageable);
			}else if(order != null && order.equals("minimumBudgetAsc")) {
				shopPage = shopRepository.findByCategoryOrderByMinimumBudgetAsc(categoryRepository.findByCategoryNameLike("%" + categoryKeyword + "%"), pageable);
			}else{
				shopPage = shopRepository.findByCategoryOrderByFuriganaAsc(categoryRepository.findByCategoryNameLike("%" + categoryKeyword + "%"), pageable);
			}
		}else {
			if(order != null && order.equals("furiganaAsc")) {
				shopPage = shopRepository.findAllByOrderByFuriganaAsc(pageable);
			}else if(order != null && order.equals("minimumBudgetAsc")) {
				shopPage = shopRepository.findAllByOrderByMinimumBudgetAsc(pageable);
			}else{
				shopPage = shopRepository.findAllByOrderByCreatedAtDesc(pageable);
			}
		}
		
		List<Category> categoryList = categoryRepository.findAll();
		
		model.addAttribute("shopPage", shopPage);
		model.addAttribute("user", user);
		model.addAttribute("shopKeyword", shopKeyword);
		model.addAttribute("categoryKeyword", categoryKeyword);
		model.addAttribute("order", order);
		model.addAttribute("categoryList", categoryList);
		
		return "index";
	}
	
	//店舗詳細
	@GetMapping("/{id}")
	public String show(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id")Integer id, FavoriteRegisterForm favoriteRegisterForm, @PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		Page<Review> newReview = reviewRepository.findByShopOrderByCreatedAtDesc(shop, pageable);
		
		if(userDetailsImpl != null) {
			User user = userDetailsImpl.getUser();
			
			favoriteRegisterForm.setUserId(user.getId());
			favoriteRegisterForm.setShopId(shop.getId());
			
			boolean isFavorited = favoriteService.isFavoritedUserAndFavoritedShop(user, shop);
			boolean isCreatedReview = reviewService.isCreatedReviewUserAndCreatedReviewShop(user, shop);
			
			model.addAttribute("user", user);
			model.addAttribute("favoriteRegisterForm", favoriteRegisterForm);
			model.addAttribute("success", isFavorited);
			model.addAttribute("created", isCreatedReview);
		}
		
		model.addAttribute("shop", shop);
		model.addAttribute("reservationInputForm", new ReservationInputForm());
		model.addAttribute("newReview", newReview);
		
		return "show";
	}

}
