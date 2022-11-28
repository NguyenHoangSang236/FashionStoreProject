package com.example.demo.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.respository.CatalogRemoveRepository;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.service.CatalogService;

@Service
public class CatalogServiceImpl implements CatalogService{
	@Autowired
	CatalogRepository catalogRepo;
	
	@Autowired
	CatalogRemoveRepository catalogRemoveRepo;
	
	
	@Override
	public void deleteCatalog(String name) {
		int catalogId = catalogRepo.getCatalogIdByName(name);
		
		catalogRemoveRepo.deleteCatalogWithProductsById(catalogId);
		catalogRemoveRepo.deleteCatalogByName(name);
	}
}
