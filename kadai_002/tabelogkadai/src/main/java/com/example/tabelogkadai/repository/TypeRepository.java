package com.example.tabelogkadai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogkadai.entity.Type;

public interface TypeRepository extends JpaRepository<Type, Integer> {
	public Type findByTypeNameJp(String typeNameJp);

}
