package com.example.tabelogkadai.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogkadai.entity.Category;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.form.ShopEditForm;
import com.example.tabelogkadai.form.ShopRegisterForm;
import com.example.tabelogkadai.repository.CategoryRepository;
import com.example.tabelogkadai.repository.ShopRepository;
import com.example.tabelogkadai.service.ShopService;

@Controller
public class AdminShopController {
	private final ShopRepository shopRepository;
	private final CategoryRepository categoryRepository;
	private final ShopService shopService;
	
	public AdminShopController(ShopRepository shopRepository, CategoryRepository categoryRepository, ShopService shopService) {
		this.shopRepository = shopRepository;
		this.categoryRepository = categoryRepository;
		this.shopService = shopService;
	}
	
	//店舗管理ページ
	@GetMapping("/login/shops")
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "shopKeyword", required = false) String shopKeyword, @RequestParam(name = "categoryKeyword", required = false) String categoryKeyword, @RequestParam(name = "price", required = false) Integer price, @RequestParam(name = "order", required = false) String order) {
		Page<Shop> shopPage;
		
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
		model.addAttribute("shopKeyword", shopKeyword);
		model.addAttribute("categoryKeyword", categoryKeyword);
		model.addAttribute("order", order);
		model.addAttribute("categoryList", categoryList);
		
		return "adminShop/index";
	}
	
	//店舗登録ページ
	@GetMapping("/login/shops/register")
	public String register(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		
		model.addAttribute("shopRegisterForm", new ShopRegisterForm());
		model.addAttribute("categoryList", categoryList);
		
		return "adminShop/register";
	}
	
	//店舗情報編集ページ
	@GetMapping("login/shops/{id}")
	public String update(@PathVariable(name = "id")Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		ShopEditForm shopEditForm = new ShopEditForm(id, shop.getCategory().getCategoryName(), shop.getShopName(), shop.getFurigana(), shop.getAlphabet(), null, shop.getDescription(), shop.getOpeningHour(), shop.getClosingHour(), shop.getClosedDay(), shop.getMinimumBudget(), shop.getMaximumBudget(), shop.getAddress(), shop.getPhoneNumber(), shop.getSeats());
		List<Category> categoryList = categoryRepository.findAll();
		
		model.addAttribute("shop", shop);
		model.addAttribute("shopEditForm", shopEditForm);
		model.addAttribute("categoryList", categoryList);
		
		return "adminShop/edit";
	}
	
	//店舗登録処理
	@PostMapping("/login/shops/register/create")
	public String create(ShopRegisterForm shopRegisterForm, RedirectAttributes redirectAttributes) {
		shopService.create(shopRegisterForm);
		
		redirectAttributes.addFlashAttribute("createSuccessMessage", "店舗登録が完了しました。");
		
		return "redirect:/login/shops";
	}
	
	//店舗削除処理
	@PostMapping("/login/shops/{id}/delete")
	public String delete(@PathVariable(name = "id")Integer id, RedirectAttributes redirectAttributes) {
		shopRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("deleteSuccessMessage", "店舗情報を削除しました。");
		
		return "redirect:/login/shops";
	}
	
	//店舗情報編集機能
	@PostMapping("/login/shops/{id}/update")
	public String update(@ModelAttribute @Validated ShopEditForm shopEditForm, RedirectAttributes redirectAttributes) {
		shopService.update(shopEditForm);
		
		redirectAttributes.addFlashAttribute("updateSuccessMessage", "店舗情報を変更しました。");
		
		return "redirect:/login/shops";
	}

}
