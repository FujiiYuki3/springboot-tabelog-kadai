package com.example.tabelogkadai.controller;

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
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.form.CategoryEditForm;
import com.example.tabelogkadai.form.CategoryRegisterForm;
import com.example.tabelogkadai.repository.CategoryRepository;
import com.example.tabelogkadai.repository.UserRepository;
import com.example.tabelogkadai.service.CategoryService;

@Controller
public class AdminAppsController {
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final CategoryService categoryService;
	
	public AdminAppsController(UserRepository userRepository, CategoryRepository categoryRepository, CategoryService categoryService) {
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.categoryService = categoryService;
	}
	
	//会員管理ページ
	@GetMapping("/login/users")
	public String users(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "userKeyword", required = false) String userKeyword, @RequestParam(name = "order", required = false) String order) {
		Page<User> userPage;
		
		if(userKeyword != null && !userKeyword.isEmpty()) {
			if(order != null && order.equals("furiganaAsc")) {
				userPage = userRepository.findByUserNameLikeOrFuriganaLikeOrderByFuriganaAsc("%" + userKeyword + "%", "%" + userKeyword + "%", pageable);
			}else{
				userPage = userRepository.findByUserNameLikeOrFuriganaLikeOrderByCreatedAtDesc("%" + userKeyword + "%", "%" + userKeyword + "%", pageable);
			}
		}else {
			if(order != null && order.equals("furiganaAsc")) {
				userPage = userRepository.findAllByOrderByFuriganaAsc(pageable);
			}else {
				userPage = userRepository.findAllByOrderByCreatedAtDesc(pageable);
			}
		}
		
		model.addAttribute("userPage", userPage);
		model.addAttribute("userKeyword", userKeyword);
		model.addAttribute("order", order);
		
		return "adminApps/users/index";
	}
	
	//カテゴリ管理ページ
	@GetMapping("/login/categories")
	public String categories(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "categoryKeyword", required = false) String categoryKeyword) {
		Page<Category> categoryPage;
		
		if(categoryKeyword != null && !categoryKeyword.isEmpty()) {
			categoryPage = categoryRepository.findByCategoryNameLikeOrderByCategoryNameAsc(categoryKeyword, pageable);
		}else {
			categoryPage = categoryRepository.findAllByOrderByCategoryNameAsc(pageable);
		}
		
		model.addAttribute("categoryPage", categoryPage);
		model.addAttribute("categoryKeyword", categoryKeyword);
		
		return "adminApps/categories/index";
	}
	
	//カテゴリ登録ページ
	@GetMapping("/login/categories/register")
	public String register(Model model) {
		model.addAttribute("categoryRegisterForm", new CategoryRegisterForm());
		
		return "adminApps/categories/register";
	}
	
	//カテゴリ編集ページ
	@GetMapping("/login/categories/{id}")
	public String update(@PathVariable(name = "id")Integer id, Model model) {
		Category category = categoryRepository.getReferenceById(id);
		CategoryEditForm categoryEditForm = new CategoryEditForm(id, category.getCategoryName());
		
		model.addAttribute("category", category);
		model.addAttribute("categoryEditForm", categoryEditForm);
		
		return "adminApps/categories/edit";
	}
	
	//カテゴリ登録処理
	@PostMapping("/login/categories/register/create")
	public String create(CategoryRegisterForm categoryRegisterForm, RedirectAttributes redirectAttributes) {
		categoryService.create(categoryRegisterForm);
		
		redirectAttributes.addFlashAttribute("createSuccessMessage", "カテゴリ登録が完了しました。");
		
		return "redirect:/login/categories";
	}
	
	//カテゴリ削除処理
	@PostMapping("/login/categories/{id}/delete")
	public String delete(@PathVariable(name = "id")Integer id, RedirectAttributes redirectAttributes) {
		categoryRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("deleteSuccessMessage", "カテゴリを削除しました。");
		
		return "redirect:/login/categories";
	}
	
	//カテゴリ編集処理
	@PostMapping("/login/categories/{id}/update")
	public String update(@ModelAttribute @Validated CategoryEditForm categoryEditForm, RedirectAttributes redirectAttributes) {
		categoryService.update(categoryEditForm);
		
		redirectAttributes.addFlashAttribute("updateSuccessMessage", "カテゴリを編集しました。");
		
		return "redirect:/login/categories";
	}

}
