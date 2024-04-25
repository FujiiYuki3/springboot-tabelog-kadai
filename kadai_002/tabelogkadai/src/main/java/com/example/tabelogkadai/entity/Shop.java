package com.example.tabelogkadai.entity;

import java.sql.Time;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "shops")
@Data
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Column(name = "shop_name")
	private String shopName;
	
	@Column(name = "furigana")
	private String furigana;
	
	@Column(name = "alphabet")
	private String alphabet;
	
	@Column(name = "photo_name")
	private String photoName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "opening_hour")
	private Time openingHour;
	
	@Column(name = "closing_hour")
	private Time closingHour;
	
	@Column(name = "closed_day")
	private String closedDay;
	
	@Column(name = "minimum_budget")
	private Integer minimumBudget;
	
	@Column(name = "maximum_budget")
	private Integer maximumBudget;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "seats")
	private Integer seats;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;

}
