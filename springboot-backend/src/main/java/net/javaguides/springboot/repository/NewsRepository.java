package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>{
	List<News> findByUser_Id(Long userid);
}
