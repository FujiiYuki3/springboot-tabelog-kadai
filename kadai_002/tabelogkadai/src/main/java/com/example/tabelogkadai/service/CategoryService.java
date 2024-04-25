package com.example.tabelogkadai.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogkadai.entity.Category;
import com.example.tabelogkadai.form.CategoryEditForm;
import com.example.tabelogkadai.form.CategoryRegisterForm;
import com.example.tabelogkadai.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	//カテゴリ登録機能
	@Transactional
	public Category create(CategoryRegisterForm categoryRegisterForm) {
		Category category = new Category();
		
		category.setCategoryName(categoryRegisterForm.getCategoryName());
		
		return categoryRepository.save(category);
	}
	
	//カテゴリ編集機能
	@Transactional
	public Category update(CategoryEditForm categoryEditForm) {
		Category category = categoryRepository.getReferenceById(categoryEditForm.getId());
		
		category.setCategoryName(categoryEditForm.getCategoryName());
		
		return categoryRepository.save(category);
	}

}
