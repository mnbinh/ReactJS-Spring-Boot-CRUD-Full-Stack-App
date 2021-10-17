package net.javaguides.springboot.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Category;
import net.javaguides.springboot.model.News;
import net.javaguides.springboot.model.NewsStatus;
import net.javaguides.springboot.model.Tag;
import net.javaguides.springboot.payload.RequestNew;
import net.javaguides.springboot.repository.CategoriesRepository;
import net.javaguides.springboot.repository.NewsRepository;
import net.javaguides.springboot.repository.TagRepository;
import net.javaguides.springboot.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/")
public class NewsController {

	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoriesRepository cateRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	// get all News
	@GetMapping("/news")
	public List<News> getAllNews(){
		return newsRepository.findAll();
	}		
	
	// create News rest api
	@PostMapping("/news")
	public News createNews( @RequestParam(value = "photo", required=false,  defaultValue = "") String photo, 
            @RequestParam(value = "body", required=false, defaultValue = "") String body,
            @RequestParam(value = "title", required=false,  defaultValue = "") String title,
            @RequestParam(value = "user", required=false,  defaultValue = "") String user, 
            @RequestParam(value = "categories", required=false,  defaultValue = "") String categories,
            @RequestParam(value = "tags", required=false,  defaultValue = "") String tags) {
		
		News news = new News();
		if(!StringUtils.isEmpty(title)) {
			news.setTitle(title);
		}
		if(!StringUtils.isEmpty(body)) {
			news.setBody(body);
		}
		if(!StringUtils.isEmpty(photo)) {
			news.setPhotos(photo);
		}
		if(!StringUtils.isEmpty(user)) {
			news.setUser(userRepository.findById(Long.parseLong(user)).get());
		}
		if(!StringUtils.isEmpty(categories)) {
			Set<Category> cates = new HashSet<Category>();
			String[] cids = categories.split(",");
			for (String id : cids) {
				cates.add(cateRepository.findById(Long.parseLong(id)).get());
			}
			news.setCategories(cates);
		}
		
		if(!StringUtils.isEmpty(tags)) {
			Set<Tag> tagsA = new HashSet<Tag>();
			String[] tids = tags.split(",");
			for (String id : tids) {
				tagsA.add(tagRepository.findById(Long.parseLong(id)).get());
			}
			news.setTags(tagsA);
		}
		news.setCreatedDate(new Date());
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
	public ResponseEntity<News> updateNews(@PathVariable Long id, 
			@RequestBody RequestNew req){
		News news = newsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("News not exist with id :" + id));
		news.setTitle(req.getTitle());
		news.setBody(req.getBody());
		news.setPhotos(req.getPhoto());
		String categories = req.getCategories();
		if(!StringUtils.isEmpty(categories)) {
			Set<Category> cates = new HashSet<Category>();
			String[] cids = categories.split(",");
			for (String cid : cids) {
				cates.add(cateRepository.findById(Long.parseLong(cid)).get());
			}
			news.setCategories(cates);
		}
		String tags = req.getTags();
		if(!StringUtils.isEmpty(tags)) {
			Set<Tag> tagsA = new HashSet<Tag>();
			String[] tids = tags.split(",");
			for (String tid : tids) {
				tagsA.add(tagRepository.findById(Long.parseLong(tid)).get());
			}
			news.setTags(tagsA);
		}
		
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
