package com.example.tabelogkadai.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tabelogkadai.entity.Favorite;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.form.FavoriteRegisterForm;
import com.example.tabelogkadai.repository.FavoriteRepository;
import com.example.tabelogkadai.repository.ShopRepository;
import com.example.tabelogkadai.security.UserDetailsImpl;
import com.example.tabelogkadai.service.FavoriteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class FavoriteController {
	private final FavoriteRepository favoriteRepository;
	private final FavoriteService favoriteService;
	private final ShopRepository shopRepository;
	
	public FavoriteController(FavoriteRepository favoriteRepository, FavoriteService favoriteService, ShopRepository shopRepository) {
		this.favoriteRepository = favoriteRepository;
		this.favoriteService = favoriteService;
		this.shopRepository = shopRepository;
	}
	
	//お気に入り一覧ページ
	@GetMapping("/favorites")
	public String favorites(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable, Model model, HttpSession httpSession) {
		User user = userDetailsImpl.getUser();
		Page<Favorite> favoritesPage = favoriteRepository.findByUser(user, pageable);
		httpSession.setAttribute("favoriteShop", shopRepository.findAll());
		
		model.addAttribute("user", user);
		model.addAttribute("favoritesPage", favoritesPage);
		
		return "paidUser/favorites/index";
	}
	
	//お気に入り登録処理
	@PostMapping("/{id}/create")
	public String create(@ModelAttribute FavoriteRegisterForm favoriteRegisterForm, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id")Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		User user = userDetailsImpl.getUser();
		
		favoriteService.create(favoriteRegisterForm, user, shop);
		
		model.addAttribute("favoriteRegisterForm", favoriteRegisterForm);
		
		return "redirect:/login/{id}";
	}
	
	//お気に入り解除処理
	@PostMapping("/{id}/delete")
	public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id")Integer id, FavoriteRegisterForm favoriteRegisterForm, Model model) {
		Integer userId = userDetailsImpl.getUser().getId();
		favoriteRepository.deleteByShopIdAndUserId(id, userId);
		favoriteRegisterForm.deleteByUserIdAndShopId(userId, id);
		
		model.addAttribute("favoriteRegisterForm", favoriteRegisterForm);
		
		return "redirect:/login/{id}";
	}

}
