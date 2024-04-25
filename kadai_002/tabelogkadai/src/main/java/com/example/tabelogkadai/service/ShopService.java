package com.example.tabelogkadai.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.tabelogkadai.entity.Category;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.form.ShopEditForm;
import com.example.tabelogkadai.form.ShopRegisterForm;
import com.example.tabelogkadai.repository.CategoryRepository;
import com.example.tabelogkadai.repository.ShopRepository;

@Service
public class ShopService {
	private final ShopRepository shopRepository;
	private final CategoryRepository categoryRepository;
	
	public ShopService(ShopRepository shopRepository, CategoryRepository categoryRepository) {
		this.shopRepository = shopRepository;
		this.categoryRepository = categoryRepository;
	}
	
	//店舗登録機能
	@Transactional
	public Shop create(ShopRegisterForm shopRegisterForm) {
		Shop shop = new Shop();
		Category category = categoryRepository.findByCategoryName(shopRegisterForm.getCategoryName());
		MultipartFile photoName = shopRegisterForm.getPhotoName();
		
		if(!photoName.isEmpty()) {
			String imageName = photoName.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(photoName, filePath);
			shop.setPhotoName(hashedImageName);
		}
		
		shop.setCategory(category);
		shop.setShopName(shopRegisterForm.getShopName());
		shop.setFurigana(shopRegisterForm.getFurigana());
		shop.setAlphabet(shopRegisterForm.getAlphabet());
		shop.setDescription(shopRegisterForm.getDescription());
		shop.setOpeningHour(shopRegisterForm.getOpeningHour());
		shop.setClosingHour(shopRegisterForm.getClosingHour());
		shop.setClosedDay(shopRegisterForm.getClosedDay());
		shop.setMinimumBudget(shopRegisterForm.getMinimumBudget());
		shop.setMaximumBudget(shopRegisterForm.getMaximumBudget());
		shop.setAddress(shopRegisterForm.getAddress());
		shop.setPhoneNumber(shopRegisterForm.getPhoneNumber());
		shop.setSeats(shopRegisterForm.getSeats());
		
		return shopRepository.save(shop);
	}
	
	 public String generateNewFileName(String fileName) {
		 String[] fileNames = fileName.split("\\.");                
		 for (int i = 0; i < fileNames.length - 1; i++) {
			 fileNames[i] = UUID.randomUUID().toString();            
		 }
		 String hashedFileName = String.join(".", fileNames);
		 return hashedFileName;
	 }
	 
	 public void copyImageFile(MultipartFile imageFile, Path filePath) {           
		 try {
			 Files.copy(imageFile.getInputStream(), filePath);
		 } catch (IOException e) {
			 e.printStackTrace();
		 }          
	 }
	
	//店舗情報編集機能
	@Transactional
	public Shop update(ShopEditForm shopEditForm) {
		Shop shop = shopRepository.getReferenceById(shopEditForm.getId());
		Category category = categoryRepository.findByCategoryName(shopEditForm.getCategoryName());
		MultipartFile photoName = shopEditForm.getPhotoName();
		
		if(!photoName.isEmpty()) {
			String imageName = photoName.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(photoName, filePath);
			shop.setPhotoName(hashedImageName);
		}
		
		shop.setCategory(category);
		shop.setShopName(shopEditForm.getShopName());
		shop.setFurigana(shopEditForm.getFurigana());
		shop.setAlphabet(shopEditForm.getAlphabet());
		shop.setDescription(shopEditForm.getDescription());
		shop.setOpeningHour(shopEditForm.getOpeningHour());
		shop.setClosingHour(shopEditForm.getClosingHour());
		shop.setClosedDay(shopEditForm.getClosedDay());
		shop.setMinimumBudget(shopEditForm.getMinimumBudget());
		shop.setMaximumBudget(shopEditForm.getMaximumBudget());
		shop.setAddress(shopEditForm.getAddress());
		shop.setPhoneNumber(shopEditForm.getPhoneNumber());
		shop.setSeats(shopEditForm.getSeats());
		
		return shopRepository.save(shop);
	}

}
