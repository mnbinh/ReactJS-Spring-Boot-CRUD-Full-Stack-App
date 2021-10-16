package net.javaguides.springboot.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "news")
public class News {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "title", columnDefinition = "TEXT")
	private String title;
	
	@Column(name = "description")
	private String description;

	@Column(name = "body", columnDefinition = "TEXT")
	private String body;
	
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;
    
    @CreatedDate
    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdDate;
    
    @Column(name = "status")
    private NewsStatus status;
    
	@Column(name = "relate")
	private String relate;
    
    @Column(name = "photos", columnDefinition = "TEXT")
    private String photos;
    
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "news_category", 
				joinColumns = @JoinColumn(name = "news_id"), 
				inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "news_tag", 
				joinColumns = @JoinColumn(name = "news_id"), 
				inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();
    
    public News() {
    	
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public NewsStatus getStatus() {
		return status;
	}

	public void setStatus(NewsStatus status) {
		this.status = status;
	}

	public String getPhoto() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
    
}
