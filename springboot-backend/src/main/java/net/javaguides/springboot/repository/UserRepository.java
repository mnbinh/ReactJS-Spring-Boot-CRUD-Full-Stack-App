package net.javaguides.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
	
	Optional<UserInfo> findByUserName(String username);

	Boolean existsByUserName(String username);

	Boolean existsByEmail(String email);

}
