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
import net.javaguides.springboot.model.Tag;
import net.javaguides.springboot.model.News;
import net.javaguides.springboot.repository.TagRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/")
public class TagController {

	@Autowired
	private TagRepository tagRepository;
	
	// get all News
	@GetMapping("/tags")
	public List<Tag> getAll(){
		return tagRepository.findAll();
	}		
	
	// create News rest api
	@PostMapping("/tags")
	public Tag create(@RequestBody Tag cate) {
		return tagRepository.save(cate);
	}
	
	// get News by id rest api
	@GetMapping("/tags/{id}")
	public ResponseEntity<Tag> getById(@PathVariable Long id) {
		Tag cates = tagRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tag not exist with id :" + id));
		return ResponseEntity.ok(cates);
	}
	
	// update News rest api
	
	@PutMapping("/tags/{id}")
	public ResponseEntity<Tag> update(@PathVariable Long id, @RequestBody News newsDetails){
		Tag cates = tagRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tag not exist with id :" + id));
		
		
		Tag cate = tagRepository.save(cates);
		return ResponseEntity.ok(cate);
	}
	
	// delete News rest api
	@DeleteMapping("/tags/{id}")
	public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id){
		Tag cate = tagRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tag not exist with id :" + id));
		
		tagRepository.delete(cate);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
