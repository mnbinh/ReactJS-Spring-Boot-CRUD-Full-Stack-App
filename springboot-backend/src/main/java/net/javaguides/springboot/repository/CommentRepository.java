package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long>{

}
