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
import net.javaguides.springboot.model.News;
import net.javaguides.springboot.repository.NewsRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class NewsController {

	@Autowired
	private NewsRepository newsRepository;
	
	// get all News
	@GetMapping("/news")
	public List<News> getAllNews(){
		return newsRepository.findAll();
	}		
	
	// create News rest api
	@PostMapping("/news")
	public News createNews(@RequestBody News news) {
		return newsRepository.save(news);
	}
	
	// get News by id rest api
	@GetMapping("/news/{id}")
	public ResponseEntity<News> getNewsById(@PathVariable Long id) {
		News news = newsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("News not exist with id :" + id));
		return ResponseEntity.ok(news);
	}
	
	// update News rest api
	
	@PutMapping("/news/{id}")
	public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News newsDetails){
		News news = newsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("News not exist with id :" + id));
		
		news.setTitle(newsDetails.getTitle());
		news.setContent(newsDetails.getContent());
		
		News updatedNews = newsRepository.save(news);
		return ResponseEntity.ok(updatedNews);
	}
	
	// delete News rest api
	@DeleteMapping("/news/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteNews(@PathVariable Long id){
		News news = newsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("News not exist with id :" + id));
		
		newsRepository.delete(news);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
