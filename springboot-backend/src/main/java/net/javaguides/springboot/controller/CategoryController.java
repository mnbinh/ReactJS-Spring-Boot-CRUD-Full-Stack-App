package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Category;
import net.javaguides.springboot.model.News;
import net.javaguides.springboot.repository.CategoriesRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/")
public class CategoryController {

	@Autowired
	private CategoriesRepository categoriesRepository;
	
	// get all News
	@GetMapping("/categories")
	public List<Category> getAll(){
		return categoriesRepository.findAll();
	}		
	
	// create News rest api
	@PostMapping("/categories")
	public Category create(@RequestBody Category cate) {
		return categoriesRepository.save(cate);
	}
	
	// get News by id rest api
	@GetMapping("/categories/{id}")
	public ResponseEntity<Category> getById(@PathVariable Long id) {
		Category cates = categoriesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not exist with id :" + id));
		return ResponseEntity.ok(cates);
	}
	
	// update News rest api
	
	@PutMapping("/categories/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody News newsDetails){
		Category cates = categoriesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not exist with id :" + id));
		
		
		Category cate = categoriesRepository.save(cates);
		return ResponseEntity.ok(cate);
	}
	
	// delete News rest api
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id){
		Category cate = categoriesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not exist with id :" + id));
		
		categoriesRepository.delete(cate);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
